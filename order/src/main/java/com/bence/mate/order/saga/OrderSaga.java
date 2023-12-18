package com.bence.mate.order.saga;

import org.springframework.beans.factory.annotation.Autowired;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.axonframework.modelling.saga.EndSaga;
import lombok.extern.slf4j.Slf4j;

import com.bence.mate.core.events.FetchUserPaymentDetailsQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import com.bence.mate.order.command.ApproveOrderCommand;
import com.bence.mate.core.events.OrderApprovedEvent;
import org.axonframework.queryhandling.QueryGateway;
import com.bence.mate.core.model.User;

import org.axonframework.commandhandling.gateway.CommandGateway;
import com.bence.mate.order.core.events.OrderCreatedEvent;
import com.bence.mate.core.commands.ReserveProductCommand;
import com.bence.mate.core.commands.ProcessPaymentCommand;
import com.bence.mate.core.events.PaymentProcessedEvent;
import com.bence.mate.core.events.ProductReservedEvent;

import java.util.concurrent.TimeUnit;
import java.util.UUID;

@Saga
@Slf4j
public class OrderSaga {

    @Autowired
    private transient QueryGateway queryGateway;

    @Autowired
    //we use transient because we don't want to serialize CommandGateway
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent orderCreatedEvent) {
        ReserveProductCommand reserveProductCommand = ReserveProductCommand.builder()
                .orderId(orderCreatedEvent.getOrderId())
                .productId(orderCreatedEvent.getProductId())
                .quantity(orderCreatedEvent.getQuantity())
                .userId(orderCreatedEvent.getUserId())
                .build();

        log.info("OrderCreatedEvent handled for orderId: " + reserveProductCommand.getOrderId() + " and productId: " + reserveProductCommand.getProductId());

        commandGateway.send(reserveProductCommand, (commandMessage, commandResultMessage) -> {
            // There is a exception
            if (commandResultMessage.isExceptional()) {
            }
        });
    }

    @SagaEventHandler(associationProperty="orderId")
    public void handle(ProductReservedEvent productReservedEvent) {
        // Process user payment
        log.info("productReservedEvent is called for productId: " + productReservedEvent.getProductId() + " and orderId: " + productReservedEvent.getOrderId());

        // --------------------- Fetching user payment details ---------------------
        User userPaymentDetails = null;
        FetchUserPaymentDetailsQuery fetchUserPaymentDetailsQuery = new FetchUserPaymentDetailsQuery(productReservedEvent.getUserId());

        try {
            userPaymentDetails = queryGateway.query(fetchUserPaymentDetailsQuery, ResponseTypes.instanceOf(User.class)).join();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            //Start compensating transaction
            return;
        }

        if(userPaymentDetails == null) {
            //Start compensating transaction
            return;
        }
        log.info("{}", userPaymentDetails);

        // --------------------- Process payment details ---------------------
        ProcessPaymentCommand processPaymentCommand = ProcessPaymentCommand.builder()
                .orderId(productReservedEvent.getOrderId())
                .paymentDetails(userPaymentDetails.getPaymentDetails())
                .paymentId(UUID.randomUUID().toString())
                .build();

        String result = null;
        try {
            result = commandGateway.sendAndWait(processPaymentCommand, 10, TimeUnit.SECONDS);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            // Start compensating transaction
            return;
        }

        if(result == null) {
            log.info("The ProcessPaymentCommand resulted in NULL. Initiating a compensating transaction");
            // Start compensating transaction
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentProcessedEvent paymentProcessedEvent) {
        // Send an ApproveOrderCommand
        ApproveOrderCommand approvedOrderCommand = new ApproveOrderCommand(paymentProcessedEvent.getOrderId());

        commandGateway.send(approvedOrderCommand);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderApprovedEvent orderApprovedEvent) {
        log.info("Order is approved. Order Saga is complete for orderId: " + orderApprovedEvent.getOrderId() );

        // This can be also used instead of @EndSaga:
        //	SagaLifecycle.end();
    }
}
