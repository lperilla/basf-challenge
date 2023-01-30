package com.lperilla.projects.basfchallenge.integration.handler;

import org.springframework.integration.core.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import com.lperilla.projects.basfchallenge.entity.Patent;
import com.lperilla.projects.basfchallenge.repository.PatentRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
	
@Slf4j
@Component
@AllArgsConstructor
public class NERPersistenceHandling implements GenericHandler<Patent> {

    private PatentRepository patentRepository;

    public Object handle(Patent payload, MessageHeaders headers) {
        try{
            log.info("Persisting NER Object");
            patentRepository.save(payload);
            return null;
        }
        finally{
            log.info("NER Object saved successfully");
        }
    }

}
