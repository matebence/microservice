package com.bence.mate.core.events;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.Getter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductReservedEvent {

    @Getter
    @Setter
    private String productId;

    @Getter
    @Setter
    private int quantity;

    @Getter
    @Setter
    private String orderId;

    @Getter
    @Setter
    private String userId;
}
