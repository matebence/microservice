package com.bence.mate.product.command.interceptor;

import com.bence.mate.product.core.repository.ProductLookupRepository;
import com.bence.mate.product.command.CreateProductCommand;
import com.bence.mate.product.core.data.ProductLookupEntity;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.axonframework.commandhandling.CommandMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.util.function.BiFunction;
import java.util.List;

@Slf4j
@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    @Autowired
    private ProductLookupRepository productLookupRepository;

    @Override
    // This will be validated before @CommandHandler in the Aggregate class
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {
            log.info("Intercepted command: " + command.getPayloadType());

            if(CreateProductCommand.class.equals(command.getPayloadType())) {
                CreateProductCommand createProductCommand = (CreateProductCommand)command.getPayload();

                // Check if the product already exists:
                ProductLookupEntity productLookupEntity = productLookupRepository.findByProductIdOrTitle(
                        createProductCommand.getProductId(), createProductCommand.getTitle());
                if(productLookupEntity != null) {
                    throw new IllegalStateException(
                            String.format("Product with productId %s or title %s already exists",
                                    createProductCommand.getProductId(), createProductCommand.getTitle())
                    );
                }

            }

            return command;
        };
    }
}
