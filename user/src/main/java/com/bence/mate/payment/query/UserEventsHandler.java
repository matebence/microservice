package com.bence.mate.payment.query;

import com.bence.mate.core.events.FetchUserPaymentDetailsQuery;
import com.bence.mate.core.model.PaymentDetails;
import com.bence.mate.core.model.User;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class UserEventsHandler {

    @QueryHandler
    public User findUserPaymentDetails(FetchUserPaymentDetailsQuery query) {
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .cardNumber("123Card")
                .cvv("123")
                .name("Bence M")
                .validUntilMonth(12)
                .validUntilYear(2030)
                .build();

        return User.builder()
                .firstName("Bence")
                .lastName("M")
                .userId(query.getUserId())
                .paymentDetails(paymentDetails)
                .build();
    }
}
