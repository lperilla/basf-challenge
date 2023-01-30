package com.lperilla.projects.basfchallenge.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lperilla.projects.basfchallenge.entity.Patent;

public interface PatentRepository extends MongoRepository<Patent, String> {
    
}
