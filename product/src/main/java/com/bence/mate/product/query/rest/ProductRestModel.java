package com.bence.mate.product.query.rest;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRestModel {

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
