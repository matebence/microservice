package com.bence.mate.core.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.Getter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    private String userId;

    @Getter
    @Setter
    private PaymentDetails paymentDetails;
}
