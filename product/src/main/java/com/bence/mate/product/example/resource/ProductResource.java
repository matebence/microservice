package com.bence.mate.product.example.resource;

import com.bence.mate.product.example.command.CreateProductCommand;
import com.bence.mate.product.example.model.CreateProductRestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import com.bence.mate.product.example.model.FindProductsQuery;
import com.bence.mate.product.example.model.ProductRestModel;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.core.env.Environment;

import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductResource {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private QueryGateway queryGateway;

    @Autowired
    private Environment env;

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
    public List<ProductRestModel> getProducts() {
        // Api cloud gate way uses the ribbon load balancer
        // with the random port we can simulate it
        // mvn clean install
        // java -jar .\product-0.0.1-SNAPSHOT.jar
        log.info("HTTP GET handled {}", env.getProperty("local.server.port"));

        FindProductsQuery findProductsQuery = new FindProductsQuery();

        return queryGateway.query(findProductsQuery, ResponseTypes.multipleInstancesOf(ProductRestModel.class)).join();
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
