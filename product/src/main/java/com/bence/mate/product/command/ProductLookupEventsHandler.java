package com.bence.mate.product.command;

import com.bence.mate.product.core.repository.ProductLookupRepository;
import com.bence.mate.product.core.events.ProductCreatedEvent;
import com.bence.mate.product.core.data.ProductLookupEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.axonframework.eventhandling.ResetHandler;
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

    @ResetHandler
    public void reset() {
        productLookupRepository.deleteAll();
    }
}