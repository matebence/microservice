package com.bence.mate.product.command;

import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.spring.stereotype.Aggregate;
import lombok.NoArgsConstructor;

import com.bence.mate.product.core.events.ProductCreatedEvent;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Aggregate
@NoArgsConstructor
public class ProductAggregate {

    // This field will associate the command and the aggregate
    @AggregateIdentifier
    private String productId;

    private String title;

    private BigDecimal price;

    private Integer quantity;

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand) {
        //validate createProduct Command
        // Validation in the @CommandHandler method
        if(createProductCommand.getPrice().compareTo(BigDecimal.ZERO)<=0) {
            throw new IllegalArgumentException("Price cannot be less or equal to zero.");
        }

        if(createProductCommand.getTitle()==null || createProductCommand.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();

        // Generally this is solved by using a builder method if command class doesn't have too many fields
        BeanUtils.copyProperties(createProductCommand, productCreatedEvent);

		/*
		Aggregate mostly publish events by applying them
		This will dispatch the aggregate to all event handlers inside this aggregate,
		so that its state could be updated.
		*/
        AggregateLifecycle.apply(productCreatedEvent);
    }

    //Initialize the current state of the aggregate based on the latest information
    @EventSourcingHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        this.productId = productCreatedEvent.getProductId();
        this.price = productCreatedEvent.getPrice();
        this.title = productCreatedEvent.getTitle();
        this.quantity = productCreatedEvent.getQuantity();
    }
}
