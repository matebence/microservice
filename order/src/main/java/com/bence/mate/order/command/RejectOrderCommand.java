package com.bence.mate.order.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.RequiredArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@RequiredArgsConstructor
public class RejectOrderCommand {

    @Getter
    @TargetAggregateIdentifier
    private final String orderId;

    @Getter
    private final String reason;
}
