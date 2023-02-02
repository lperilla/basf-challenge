package com.lperilla.projects.basfchallenge.service;

import com.lperilla.projects.basfchallenge.entity.Error;
import com.lperilla.projects.basfchallenge.entity.NERObject;
import com.lperilla.projects.basfchallenge.entity.Patent;
import jakarta.servlet.http.Part;

import java.util.List;

public interface PatentService {

    boolean processFile(final Part part);

    boolean dropCollections();

    List<Error> findAllError();

    List<NERObject> processAbstract(String abstractText);

    Patent save(Patent patent);
}