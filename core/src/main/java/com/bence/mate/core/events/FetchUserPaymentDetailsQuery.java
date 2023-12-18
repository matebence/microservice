package com.bence.mate.core.events;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.Getter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FetchUserPaymentDetailsQuery {

    @Getter
    @Setter
    private String userId;
}
