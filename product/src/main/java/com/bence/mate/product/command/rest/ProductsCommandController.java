package com.bence.mate.product.command.rest;

import org.axonframework.commandhandling.gateway.CommandGateway;
import com.bence.mate.product.command.CreateProductCommand;

import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductsCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public String createProduct(@RequestBody CreateProductRestModel createProductRestModel) {
        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .price(createProductRestModel.getPrice())
                .quantity(createProductRestModel.getQuantity())
                .title(createProductRestModel.getTitle())
                .productId(UUID.randomUUID().toString())
                .build();

        String productId;
        try {
            // commandGateway.send(createProductCommand); async
            // this will block the main thread
            productId = commandGateway.sendAndWait(createProductCommand);
        } catch (Exception ex) {
            // Its is possible to handle errors like this, but its recommend to have a central place
            // Via Spring @ControllerAdvice = this try catch has to be commented out
            productId = ex.getMessage();
        }
        return productId;
    }
}
