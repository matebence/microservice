package com.bence.mate.order.command.rest;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Builder
@NoArgsConstructor
@AllArgsConstructor
// Validating Request Body. Bean Validation
public class OrderCreateRest {

    @Getter
    @Setter
    @NotBlank(message = "Order productId is a required field")
    private String productId;

    @Getter
    @Setter
    @Min(value = 1, message = "Quantity cannot be lower than 1")
    @Max(value = 5, message = "Quantity cannot be larger than 5")
    private int quantity;

    @Getter
    @Setter
    @NotBlank(message = "Order addressId is a required field")
    private String addressId;
}
