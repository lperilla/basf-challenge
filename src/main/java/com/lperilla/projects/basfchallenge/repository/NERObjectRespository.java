package com.lperilla.projects.basfchallenge.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lperilla.projects.basfchallenge.entity.NERObject;

public interface NERObjectRespository extends MongoRepository<NERObject, UUID>{
    
}
