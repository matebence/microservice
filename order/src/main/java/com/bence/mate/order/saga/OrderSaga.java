package com.bence.mate.order.saga;

import org.springframework.beans.factory.annotation.Autowired;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.axonframework.modelling.saga.EndSaga;
import lombok.extern.slf4j.Slf4j;

import com.bence.mate.core.events.ProductReservationCancelledEvent;
import com.bence.mate.core.events.FetchUserPaymentDetailsQuery;
import com.bence.mate.order.core.events.OrderRejectedEvent;
import com.bence.mate.core.events.PaymentProcessedEvent;
import com.bence.mate.core.events.ProductReservedEvent;
import com.bence.mate.order.command.RejectOrderCommand;
import com.bence.mate.core.events.OrderApprovedEvent;

import com.bence.mate.core.commands.CancelProductReservationCommand;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import com.bence.mate.order.command.ApproveOrderCommand;
import org.axonframework.queryhandling.QueryGateway;
import com.bence.mate.core.model.User;

import org.axonframework.commandhandling.gateway.CommandGateway;
import com.bence.mate.order.core.events.OrderCreatedEvent;
import com.bence.mate.core.commands.ReserveProductCommand;
import com.bence.mate.core.commands.ProcessPaymentCommand;

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
            cancelProductReservation(productReservedEvent, ex.getMessage());
            return;
        }

        if(userPaymentDetails == null) {
            //Start compensating transaction
            cancelProductReservation(productReservedEvent, "Could not fetch user payment details");
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
            cancelProductReservation(productReservedEvent, ex.getMessage());
            return;
        }

        if(result == null) {
            log.info("The ProcessPaymentCommand resulted in NULL. Initiating a compensating transaction");
            // Start compensating transaction
            cancelProductReservation(productReservedEvent, "Could not process user payment");
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentProcessedEvent paymentProcessedEvent) {
        // Send an ApproveOrderCommand
        ApproveOrderCommand approvedOrderCommand = new ApproveOrderCommand(paymentProcessedEvent.getOrderId());

        commandGateway.send(approvedOrderCommand);
    }

    // End Saga success case
    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderApprovedEvent orderApprovedEvent) {
        log.info("Order is approved. Order Saga is complete for orderId: " + orderApprovedEvent.getOrderId() );

        // This can be also used instead of @EndSaga:
        //	SagaLifecycle.end();
    }

    private void cancelProductReservation(ProductReservedEvent productReservedEvent, String reason) {
        CancelProductReservationCommand publishProductReservationCommand =
                CancelProductReservationCommand.builder()
                        .orderId(productReservedEvent.getOrderId())
                        .productId(productReservedEvent.getProductId())
                        .quantity(productReservedEvent.getQuantity())
                        .userId(productReservedEvent.getUserId())
                        .reason(reason)
                        .build();

        commandGateway.send(publishProductReservationCommand);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservationCancelledEvent productReservationCancelledEvent) {
        // Create and send a RejectOrderCommand
        RejectOrderCommand rejectOrderCommand = new RejectOrderCommand(productReservationCancelledEvent.getOrderId(), productReservationCancelledEvent.getReason() );

        commandGateway.send(rejectOrderCommand);
    }

    // End Saga fail case
    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderRejectedEvent orderRejectedEvent) {
        log.info("Successfully rejected order with id: " + orderRejectedEvent.getOrderId());
    }
}