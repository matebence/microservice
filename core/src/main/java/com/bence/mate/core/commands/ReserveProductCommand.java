package com.bence.mate.core.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.RequiredArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@RequiredArgsConstructor
public class ReserveProductCommand {

    @Getter
    @TargetAggregateIdentifier
    private final String productId;

    @Getter
    private final int quantity;

    @Getter
    private final String orderId;

    @Getter
    private final String userId;
}
