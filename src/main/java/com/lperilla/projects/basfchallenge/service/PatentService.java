package com.lperilla.projects.basfchallenge.service;

import jakarta.servlet.http.Part;

public interface PatentService {

	void processFile(final Part part);

	boolean dropCollections();

}