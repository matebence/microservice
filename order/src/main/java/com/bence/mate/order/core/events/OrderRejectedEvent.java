package com.bence.mate.order.core.events;

import com.bence.mate.core.model.OrderStatus;

import lombok.RequiredArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@AllArgsConstructor
@RequiredArgsConstructor
public class OrderRejectedEvent {

    @Getter
    private final String orderId;

    @Getter
    @Setter
    private String reason;

    @Getter
    private final OrderStatus orderStatus = OrderStatus.REJECTED;
}
