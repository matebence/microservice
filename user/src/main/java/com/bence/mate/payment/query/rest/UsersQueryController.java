package com.bence.mate.payment.query.rest;

import com.bence.mate.core.events.FetchUserPaymentDetailsQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import com.bence.mate.core.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/users")
public class UsersQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/{userId}/payment-details")
    public User getUserPaymentDetails(@PathVariable String userId) {
        FetchUserPaymentDetailsQuery query = new FetchUserPaymentDetailsQuery(userId);

        return queryGateway.query(query, ResponseTypes.instanceOf(User.class)).join();
    }
}
