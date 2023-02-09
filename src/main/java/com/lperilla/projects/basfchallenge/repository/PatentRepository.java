package com.lperilla.projects.basfchallenge.repository;

import com.lperilla.projects.basfchallenge.entity.Patent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatentRepository extends MongoRepository<Patent, String> {

}