package com.bence.mate.product.resource;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.core.env.Environment;

@RestController
@RequestMapping("/products")
public class ProductResource {

    @Autowired
    private Environment env;

    @PostMapping
    public String createProduct() {
        return "HTTP POST handled";
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