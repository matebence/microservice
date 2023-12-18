package com.bence.mate.order.command.rest;

import org.axonframework.commandhandling.gateway.CommandGateway;
import com.bence.mate.order.command.CreateOrderCommand;
import com.bence.mate.core.model.OrderStatus;

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
    private CommandGateway commandGateway;

    @PostMapping
    public String createOrder(@Valid @RequestBody OrderCreateRest order) {
        String userId = "27b95829-4f3f-4ddf-8983-151ba010e35b";
        String requestId = UUID.randomUUID().toString();

        CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
                .addressId(order.getAddressId())
                .productId(order.getProductId())
                .userId(userId)
                .quantity(order.getQuantity())
                .orderId(requestId)
                .orderStatus(OrderStatus.CREATED)
                .build();

        String orderId;

        try {
            orderId = commandGateway.sendAndWait(createOrderCommand);
        } catch (Exception ex) {
            orderId = ex.getMessage();
        }
        return orderId;
    }
}
