package com.bence.mate.product.core.error;

import org.axonframework.commandhandling.CommandExecutionException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ProductsServiceErrorHandler {

    // This is inside the RestController
    @ExceptionHandler(value= {IllegalStateException.class})
    public ResponseEntity<Object>handleIllegalStateException(IllegalStateException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // This is inside the RestController - Generic one
    @ExceptionHandler(value= {Exception.class})
    public ResponseEntity<Object>handleOtherExceptions(Exception ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // This is inside the ProductAggregate
    @ExceptionHandler(value= {CommandExecutionException.class})
    public ResponseEntity<Object>handleCommandExecutionException(CommandExecutionException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());

        // ProductsServiceEventsErrorHandler will be handled here
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
