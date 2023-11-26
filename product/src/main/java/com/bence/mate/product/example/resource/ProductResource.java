package com.bence.mate.product.example.resource;

import com.bence.mate.product.example.command.CreateProductCommand;
import com.bence.mate.product.example.model.CreateProductRestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.core.env.Environment;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductResource {

    @Autowired
    private Environment env;

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
            productId = ex.getMessage();
        }
        return productId;
    }

    @GetMapping
    public String getProduct() {
        // Api cloud gate way uses the ribbon load balancer
        // with the random port we can simulate it
        // mvn clean install
        // java -jar .\product-0.0.1-SNAPSHOT.jar
        return "HTTP GET handled " + env.getProperty("local.server.port");
    }

    @PutMapping
    public String updateProduct() {
        return "HTTP PUT handled";
    }

    @DeleteMapping
    public String deleteProduct() {
        return "HTTP DELETE handled";
    }
}
