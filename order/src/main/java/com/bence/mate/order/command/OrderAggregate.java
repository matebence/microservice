package com.bence.mate.order.command;

import org.axonframework.modelling.command.AggregateLifecycle;
import com.bence.mate.order.core.events.OrderCreatedEvent;
import com.bence.mate.core.events.OrderApprovedEvent;
import com.bence.mate.core.model.OrderStatus;
import org.springframework.beans.BeanUtils;

import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.spring.stereotype.Aggregate;
import lombok.NoArgsConstructor;

@Aggregate
@NoArgsConstructor
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;

    private String productId;

    private String userId;

    private int quantity;

    private String addressId;

    private OrderStatus orderStatus;

    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand) {
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        BeanUtils.copyProperties(createOrderCommand, orderCreatedEvent);

        AggregateLifecycle.apply(orderCreatedEvent);
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent orderCreatedEvent) {
        this.orderId = orderCreatedEvent.getOrderId();
        this.productId = orderCreatedEvent.getProductId();
        this.userId = orderCreatedEvent.getUserId();
        this.addressId = orderCreatedEvent.getAddressId();
        this.quantity = orderCreatedEvent.getQuantity();
        this.orderStatus = orderCreatedEvent.getOrderStatus();
    }

    @CommandHandler
    public void handle(ApproveOrderCommand approveOrderCommand) {
        //Create and publish the OrderApprovedEvent
        OrderApprovedEvent orderApprovedEvent = new OrderApprovedEvent(approveOrderCommand.getOrderId());

        AggregateLifecycle.apply(orderApprovedEvent);
    }

    @EventSourcingHandler
    protected void on(OrderApprovedEvent orderApprovedEvent) {
        this.orderStatus = orderApprovedEvent.getOrderStatus();
    }
}
