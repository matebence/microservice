package com.bence.mate.core.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.Getter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetails {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String cardNumber;

    @Getter
    @Setter
    private int validUntilMonth;

    @Getter
    @Setter
    private int validUntilYear;

    @Getter
    @Setter
    private String cvv;
}
