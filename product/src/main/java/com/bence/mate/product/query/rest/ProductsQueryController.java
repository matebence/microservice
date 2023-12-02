package com.bence.mate.product.query.rest;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import com.bence.mate.product.query.FindProductsQuery;
import org.axonframework.queryhandling.QueryGateway;

import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductsQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @Autowired
    private Environment env;

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
}
