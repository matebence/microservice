package com.bence.mate.product;

import com.bence.mate.product.command.interceptor.CreateProductCommandInterceptor;
import com.bence.mate.product.core.error.ProductsServiceEventsErrorHandler;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.context.ApplicationContext;
import org.axonframework.commandhandling.CommandBus;
import org.springframework.boot.SpringApplication;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;

@EnableDiscoveryClient
@SpringBootApplication
public class ProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}

	// Register interceptor
	@Autowired
	public void registerCreateProductCommandInterceptor(ApplicationContext context, CommandBus commandBus) {
		commandBus.registerDispatchInterceptor(context.getBean(CreateProductCommandInterceptor.class));
	}


	// Register error handler
	@Autowired
	public void configure(EventProcessingConfigurer config) {
		config.registerListenerInvocationErrorHandler("product-group", conf -> new ProductsServiceEventsErrorHandler() );

		// Another option:
		// config.registerListenerInvocationErrorHandler("product-group", conf -> PropagatingErrorHandler.instance() );
	}
}
