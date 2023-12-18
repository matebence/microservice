package com.bence.mate.core.events;

import com.bence.mate.core.model.OrderStatus;

import lombok.RequiredArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class OrderApprovedEvent {

    @Getter
    private final String orderId;

    @Getter
    private OrderStatus orderStatus = OrderStatus.APPROVED;
}
