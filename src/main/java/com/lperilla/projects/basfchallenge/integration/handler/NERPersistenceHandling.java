package com.lperilla.projects.basfchallenge.integration.handler;

import java.util.List;

import org.springframework.integration.core.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import com.lperilla.projects.basfchallenge.entity.NERObject;
import com.lperilla.projects.basfchallenge.repository.NERObjectRespository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
	
@Slf4j
@Component
@AllArgsConstructor
public class NERPersistenceHandling implements GenericHandler<List<NERObject>> {

    private NERObjectRespository nerObjectRespository;

    public Object handle(List<NERObject> payload, MessageHeaders headers) {
        try{
            log.info("Persisting NER Object");
            nerObjectRespository.saveAll(payload);
            return null;
        }
        finally{
            log.info("NER Object saved successfully");
        }
    }

}
