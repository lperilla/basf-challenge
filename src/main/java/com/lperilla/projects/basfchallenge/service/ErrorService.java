package com.lperilla.projects.basfchallenge.service;

import com.lperilla.projects.basfchallenge.entity.Error;

import java.util.List;

public interface ErrorService {

    List<Error> findAllByOrderByTimestampDesc();

    Error save(Error error);
}
