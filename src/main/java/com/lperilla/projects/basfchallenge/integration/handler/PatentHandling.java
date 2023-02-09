package com.lperilla.projects.basfchallenge.integration.handler;

import com.lperilla.projects.basfchallenge.entity.Patent;
import com.lperilla.projects.basfchallenge.service.PatentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.core.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class PatentHandling implements GenericHandler<Patent> {

    private PatentService patentService;

    public Object handle(Patent payload, MessageHeaders headers) {
        try {
            payload.setNer(patentService.processAbstract(payload.getAbstractText()));
            log.info("Persisting Patent {}", payload.getDocumentId());
            patentService.save(payload);
            return null;
        } finally {
            log.info("Patent {} saved successfully", payload.getDocumentId());
        }
    }

}
