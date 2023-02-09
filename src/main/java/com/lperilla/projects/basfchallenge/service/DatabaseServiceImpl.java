package com.lperilla.projects.basfchallenge.service;

import com.lperilla.projects.basfchallenge.entity.Error;
import com.lperilla.projects.basfchallenge.entity.Patent;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DatabaseServiceImpl implements DatabaseService {

    private MongoTemplate mongoTemplate;

    @Override
    public void deleteAll() {
        mongoTemplate.dropCollection(Patent.class);
        mongoTemplate.dropCollection(Error.class);
    }
}
