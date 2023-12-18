package com.bence.mate.payment.command;

import org.axonframework.modelling.command.AggregateLifecycle;
import com.bence.mate.core.commands.ProcessPaymentCommand;
import com.bence.mate.core.events.PaymentProcessedEvent;

import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.NoArgsConstructor;

@Aggregate
@NoArgsConstructor
public class PaymentAggregate {

    @AggregateIdentifier
    private String paymentId;

    private String orderId;

    @CommandHandler
    public PaymentAggregate(ProcessPaymentCommand processPaymentCommand){
        if(processPaymentCommand.getPaymentDetails() == null) {
            throw new IllegalArgumentException("Missing payment details");
        }

        if(processPaymentCommand.getOrderId() == null) {
            throw new IllegalArgumentException("Missing orderId");
        }

        if(processPaymentCommand.getPaymentId() == null) {
            throw new IllegalArgumentException("Missing paymentId");
        }

        AggregateLifecycle.apply(new PaymentProcessedEvent(processPaymentCommand.getOrderId(), processPaymentCommand.getPaymentId()));
    }

    @EventSourcingHandler
    protected void on(PaymentProcessedEvent paymentProcessedEvent){
        this.paymentId = paymentProcessedEvent.getPaymentId();
        this.orderId = paymentProcessedEvent.getOrderId();
    }
}
