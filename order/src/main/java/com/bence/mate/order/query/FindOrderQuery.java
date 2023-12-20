package com.bence.mate.order.query;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class FindOrderQuery {

    @Getter
    @Setter
    private String orderId;
}
