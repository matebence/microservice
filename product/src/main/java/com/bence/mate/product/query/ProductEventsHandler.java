package com.bence.mate.product.query;

import com.bence.mate.product.core.repository.ProductsRepository;
import com.bence.mate.product.core.events.ProductCreatedEvent;
import com.bence.mate.product.core.data.ProductEntity;
import org.springframework.beans.BeanUtils;

import org.axonframework.messaging.interceptors.ExceptionHandler;
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

    //General Exception handling
    @ExceptionHandler(resultType=Exception.class)
    public void handle(Exception exception) throws Exception {
        // propagation up, because if we catch there is no rollback
        throw exception;
    }

    //Exception handling
    @ExceptionHandler(resultType=IllegalArgumentException.class)
    public void handle(IllegalArgumentException exception) {
        // propagation up, because if we catch there is no rollback
        throw exception;
    }

    @EventHandler
    public void on(ProductCreatedEvent event) throws Exception {
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(event, productEntity);

        // Here can be a exception to be trowed so we can handle it like this:
        // or via @ExceptionHandler - in this we have to try-catch comment out
        try {
            productsRepository.save(productEntity);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        if (true) {
            throw new Exception("Error happened");
        }
    }
}