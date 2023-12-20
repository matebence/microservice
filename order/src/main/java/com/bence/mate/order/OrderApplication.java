package com.bence.mate.order;

import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.axonframework.config.ConfigurationScopeAwareProvider;
import org.axonframework.deadline.SimpleDeadlineManager;
import org.axonframework.deadline.DeadlineManager;
import org.springframework.boot.SpringApplication;
import org.axonframework.config.Configuration;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

	// This Deadline manager does not persist data. Deadline will disappear if JVM turns off.
	@Bean
	public DeadlineManager deadlineManager(Configuration configuration, SpringTransactionManager transactionManager) {
		return SimpleDeadlineManager.builder()
				.scopeAwareProvider(new ConfigurationScopeAwareProvider(configuration))
				.transactionManager(transactionManager)
				.build();
	}
}
