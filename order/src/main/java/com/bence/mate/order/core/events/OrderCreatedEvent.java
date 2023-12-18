package com.bence.mate.order.core.events;

import com.bence.mate.core.model.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.Getter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {

    @Getter
    @Setter
    private String orderId;

    @Getter
    @Setter
    private String productId;

    @Getter
    @Setter
    private String userId;

    @Getter
    @Setter
    private int quantity;

    @Getter
    @Setter
    private String addressId;

    @Getter
    @Setter
    private OrderStatus orderStatus;
}
