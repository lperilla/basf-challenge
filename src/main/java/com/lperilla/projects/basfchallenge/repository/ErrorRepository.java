package com.lperilla.projects.basfchallenge.repository;

import com.lperilla.projects.basfchallenge.entity.Error;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface ErrorRepository extends MongoRepository<Error, UUID> {
    List<Error> findAllByOrderByTimestampDesc();

}
