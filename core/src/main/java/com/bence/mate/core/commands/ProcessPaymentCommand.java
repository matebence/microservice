package com.bence.mate.core.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.RequiredArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import com.bence.mate.core.model.PaymentDetails;

@Builder
@RequiredArgsConstructor
public class ProcessPaymentCommand {

    @Getter
    @TargetAggregateIdentifier
    private final String paymentId;

    @Getter
    private final String orderId;

    @Getter
    private final PaymentDetails paymentDetails;
}
