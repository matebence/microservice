package com.bence.mate.product.core.error;

import lombok.RequiredArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@RequiredArgsConstructor
public class ErrorMessage {

    @Getter
    private final Date timestamp;

    @Getter
    private final String message;
}
