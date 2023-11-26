package com.bence.mate.product.example.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import lombok.RequiredArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@RequiredArgsConstructor
public class CreateProductCommand {

    // This field will associate the command and the aggregate:
    // Aggregate is our domain object, it handles and validates the commands and if a event has to be raised
    @Getter
    @TargetAggregateIdentifier
    private final String productId;

    @Getter
    private final String title;

    @Getter
    private final BigDecimal price;

    @Getter
    private final Integer quantity;
}
