package com.bence.mate.order.query;

import com.bence.mate.order.core.model.events.OrderCreatedEvent;
import com.bence.mate.order.core.data.OrdersRepository;
import com.bence.mate.order.core.data.OrderEntity;
import org.springframework.beans.BeanUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.config.ProcessingGroup;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("order-group")
public class OrderEventsHandler {

    @Autowired
    private OrdersRepository ordersRepository;

    @EventHandler
    public void on(OrderCreatedEvent event) throws Exception {
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(event, orderEntity);

        this.ordersRepository.save(orderEntity);
    }
}
