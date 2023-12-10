package com.bence.mate.product.core.error;

import org.axonframework.eventhandling.ListenerInvocationErrorHandler;
import org.axonframework.eventhandling.EventMessageHandler;
import org.axonframework.eventhandling.EventMessage;

public class ProductsServiceEventsErrorHandler implements ListenerInvocationErrorHandler {

    // We can catch in a central place like this
    @Override
    public void onError(Exception exception, EventMessage<?> event, EventMessageHandler eventHandler) throws Exception {
        // propagation up, because if we catch there is no rollback
        throw exception;
    }
}
