package com.lperilla.projects.basfchallenge.integration.handler;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ErrorHandling {

    @ServiceActivator(inputChannel = "errorChannel")
    public void handleError(Message<MessageHandlingException> message) {
        log.info("Error: {} ", message);
    }
}