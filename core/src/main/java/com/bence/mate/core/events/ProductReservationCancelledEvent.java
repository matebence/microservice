package com.bence.mate.core.events;

import lombok.RequiredArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductReservationCancelledEvent {

    @Getter
    private String productId;

    @Getter
    private int quantity;

    @Getter
    private String orderId;

    @Getter
    private String userId;

    @Getter
    private String reason;
}
