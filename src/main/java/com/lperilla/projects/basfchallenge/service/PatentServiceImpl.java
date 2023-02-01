package com.lperilla.projects.basfchallenge.service;

import com.lperilla.projects.basfchallenge.config.BasfChallengeProperties;
import com.lperilla.projects.basfchallenge.entity.Error;
import com.lperilla.projects.basfchallenge.entity.Patent;
import com.lperilla.projects.basfchallenge.exception.BasfException;
import jakarta.servlet.http.Part;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PatentServiceImpl implements PatentService {

    private final MongoTemplate mongoTemplate;

    private final BasfChallengeProperties basfChallengeProperties;

    @Override
    public boolean processFile(final Part file) {
        log.info("Processing File {} : {}", file.getSubmittedFileName(), file.getContentType());
        if (Utils.isXmlFile(file.getContentType())) {
            log.info("Moving {} file in directory {}", file.getSubmittedFileName(), basfChallengeProperties.getDirectory());
            Utils.moveXmlFile(basfChallengeProperties.getDirectory(), file);
        } else if (Utils.isZipFile(file.getContentType())) {
            log.info("Unzipping {} file in directory {}", file.getSubmittedFileName(), basfChallengeProperties.getDirectory());
            Utils.unZip(basfChallengeProperties.getDirectory(), file);
        } else {
            throw new BasfException(String.format("Invalid %s file type. Only XML and ZIP files are allowed.", file.getContentType()));
        }
        return true;
    }

    @Override
    public boolean dropCollections() {
        mongoTemplate.dropCollection(Patent.class);
        mongoTemplate.dropCollection(Error.class);
        return true;
    }

    public List<Error> findAllError() {
        return mongoTemplate.findAll(Error.class);
    }

}
