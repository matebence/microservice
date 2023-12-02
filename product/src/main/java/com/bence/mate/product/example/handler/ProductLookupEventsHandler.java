package com.bence.mate.product.example.handler;

import com.bence.mate.product.example.repository.ProductLookupRepository;
import com.bence.mate.product.example.event.ProductCreatedEvent;
import com.bence.mate.product.example.model.ProductLookupEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.config.ProcessingGroup;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
public class ProductLookupEventsHandler {

    @Autowired
    private ProductLookupRepository productLookupRepository;

    @EventHandler
    public void on(ProductCreatedEvent event) {
        ProductLookupEntity productLookupEntity = new ProductLookupEntity(event.getProductId(), event.getTitle());

        productLookupRepository.save(productLookupEntity);
    }
}