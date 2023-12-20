package com.bence.mate.order.query;

import com.bence.mate.order.core.data.OrdersRepository;
import com.bence.mate.order.core.model.OrderSummary;
import com.bence.mate.order.core.data.OrderEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class OrderQueriesHandler {

    @Autowired
    private OrdersRepository ordersRepository;

    @QueryHandler
    public OrderSummary findOrder(FindOrderQuery findOrderQuery) {
        OrderEntity orderEntity = ordersRepository.findByOrderId(findOrderQuery.getOrderId());
        return new OrderSummary(orderEntity.getOrderId(), orderEntity.getOrderStatus(), "");
    }
}
