package com.bence.mate.product.command.rest;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
// Validating Request Body. Bean Validation
public class CreateProductRestModel {

    @Getter
    @Setter
    @NotBlank(message = "Product title is a required field")
    private String title;

    @Getter
    @Setter
    @Min(value = 1, message = "Price cannot be lower than 1.")
    private BigDecimal price;

    @Getter
    @Setter
    @Min(value = 1, message = "Quantity cannot be lower than 1.")
    @Max(value=10, message = "Quantity cannot be larger than 10.")
    private Integer quantity;
}
