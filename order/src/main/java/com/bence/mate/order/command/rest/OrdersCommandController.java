package com.bence.mate.order.command.rest;

import org.axonframework.commandhandling.gateway.CommandGateway;
import com.bence.mate.order.command.CreateOrderCommand;
import com.bence.mate.order.core.model.OrderSummary;
import com.bence.mate.order.query.FindOrderQuery;
import com.bence.mate.core.model.OrderStatus;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.axonframework.queryhandling.QueryGateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrdersCommandController {

    @Autowired
    private QueryGateway queryGateway;

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public OrderSummary createOrder(@Valid @RequestBody OrderCreateRest order) {

        String userId = "27b95829-4f3f-4ddf-8983-151ba010e35b";
        String orderId = UUID.randomUUID().toString();

        CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
                .addressId(order.getAddressId())
                .productId(order.getProductId())
                .userId(userId)
                .quantity(order.getQuantity())
                .orderId(orderId)
                .orderStatus(OrderStatus.CREATED)
                .build();

        SubscriptionQueryResult<OrderSummary, OrderSummary> queryResult =
                queryGateway.subscriptionQuery(new FindOrderQuery(orderId),
                        ResponseTypes.instanceOf(OrderSummary.class) ,
                        ResponseTypes.instanceOf(OrderSummary.class));

        try {
            commandGateway.sendAndWait(createOrderCommand);

            // We are waiting here for the first result from the subscription
            return queryResult.updates().blockFirst();
        }
        finally {
            queryResult.close();
        }
    }
}
