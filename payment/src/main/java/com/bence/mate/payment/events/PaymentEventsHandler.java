package com.bence.mate.payment.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import com.bence.mate.core.events.PaymentProcessedEvent;
import com.bence.mate.payment.data.PaymentRepository;
import com.bence.mate.payment.data.PaymentEntity;
import org.springframework.beans.BeanUtils;

@Slf4j
@Component
public class PaymentEventsHandler {

    @Autowired
    private PaymentRepository paymentRepository;

    @EventHandler
    public void on(PaymentProcessedEvent event) {
        log.info("PaymentProcessedEvent is called for orderId: " + event.getOrderId());

        PaymentEntity paymentEntity = new PaymentEntity();
        BeanUtils.copyProperties(event, paymentEntity);

        paymentRepository.save(paymentEntity);
    }
}
