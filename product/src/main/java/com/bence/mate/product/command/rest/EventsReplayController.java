package com.bence.mate.product.command.rest;

import org.axonframework.eventhandling.TrackingEventProcessor;
import org.axonframework.config.EventProcessingConfiguration;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@RestController
@RequestMapping("/management")
public class EventsReplayController {

    @Autowired
    private EventProcessingConfiguration eventProcessingConfiguration;

    @PostMapping("/eventProcessor/{processorName}/reset")
    public ResponseEntity<String> replayEvents(@PathVariable String processorName) {
        Optional<TrackingEventProcessor>trackingEventProcessor = eventProcessingConfiguration.eventProcessor(processorName, TrackingEventProcessor.class);

        // It will first delete everything from the read database then insert everything with help of the event store
        if(trackingEventProcessor.isPresent()) {
            TrackingEventProcessor eventProcessor = trackingEventProcessor.get();
            eventProcessor.shutDown();
            eventProcessor.resetTokens();
            eventProcessor.start();

            return ResponseEntity.ok().body(String.format("The eventprocessor with name [%s] has been reset", processorName));
        }
        else {
            return ResponseEntity.badRequest()
                    .body(String.format(
                            "The eventprocessor with name [%s] is not a tracking event processor. "
                                    + "Only Tracking event processor is supported.", processorName));
        }
    }
}
