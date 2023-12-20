package com.bence.mate.order.core.model;

import com.bence.mate.core.model.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.Getter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSummary {

    @Getter
    @Setter
    private String orderId;

    @Getter
    @Setter
    private OrderStatus orderStatus;

    @Getter
    @Setter
    private String message;
}
