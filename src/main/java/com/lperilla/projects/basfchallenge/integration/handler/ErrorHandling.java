package com.lperilla.projects.basfchallenge.integration.handler;

import com.lperilla.projects.basfchallenge.entity.Error;
import com.lperilla.projects.basfchallenge.service.ErrorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

@Slf4j
@Component
@AllArgsConstructor
public class ErrorHandling {

    private ErrorService errorService;

    @ServiceActivator(inputChannel = "errorChannel")
    public void handleError(Message<?> message) {
        MessagingException messagingException = (MessagingException) message.getPayload();

        errorService.save(Error.builder().id(message.getHeaders().getId()) //
                .fileName(messagingException.getFailedMessage().getHeaders().get("file_name", String.class)).timestamp(LocalDateTime.ofInstant( //
                        Instant.ofEpochMilli(message.getHeaders().getTimestamp()), //
                        TimeZone.getTimeZone(ZoneId.systemDefault()).toZoneId())) //
                .failedMessage(messagingException.getMessage()) //
                .cause(messagingException.getFailedMessage().getPayload().toString()).build());
        log.error("Error: {} {}", message.getPayload(), message.getHeaders());
    }
}