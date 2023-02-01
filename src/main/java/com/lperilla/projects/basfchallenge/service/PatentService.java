package com.lperilla.projects.basfchallenge.service;

import com.lperilla.projects.basfchallenge.entity.Error;
import jakarta.servlet.http.Part;

import java.util.List;

public interface PatentService {

    boolean processFile(final Part part);

    boolean dropCollections();

    List<Error> findAllError();

}