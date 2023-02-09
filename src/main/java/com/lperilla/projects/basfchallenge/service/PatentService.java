package com.lperilla.projects.basfchallenge.service;

import com.lperilla.projects.basfchallenge.entity.NERObject;
import com.lperilla.projects.basfchallenge.entity.Patent;

import java.util.List;

public interface PatentService {

    List<NERObject> processAbstract(String abstractText);

    Patent save(Patent patent);

    List<Patent> findAll();
}