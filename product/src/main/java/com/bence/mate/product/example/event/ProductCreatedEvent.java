package com.bence.mate.product.example.event;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreatedEvent {

    @Getter
    @Setter
    private String productId;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private BigDecimal price;

    @Getter
    @Setter
    private Integer quantity;
}
