package com.bence.mate.product.query;

import com.bence.mate.product.core.repository.ProductsRepository;
import com.bence.mate.product.core.events.ProductCreatedEvent;
import com.bence.mate.product.core.data.ProductEntity;
import org.springframework.beans.BeanUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import org.axonframework.config.ProcessingGroup;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ProcessingGroup("product-group")
public class ProductEventsHandler {

    @Autowired
    private ProductsRepository productsRepository;

    @EventHandler
    public void on(ProductCreatedEvent event) {
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(event, productEntity);

        try {
            productsRepository.save(productEntity);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
