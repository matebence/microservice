package com.bence.mate.order.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.bence.mate.core.model.OrderStatus;

import lombok.RequiredArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@RequiredArgsConstructor
public class CreateOrderCommand {

    // This field will associate the command and the aggregate:
    // Aggregate is our domain object, it handles and validates the commands and if a event has to be raised
    @Getter
    @TargetAggregateIdentifier
    public final String orderId;

    @Getter
    private final String userId;

    @Getter
    private final String productId;

    @Getter
    private final int quantity;

    @Getter
    private final String addressId;

    @Getter
    private final OrderStatus orderStatus;
}
