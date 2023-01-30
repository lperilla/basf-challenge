package com.lperilla.projects.basfchallenge.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.lperilla.projects.basfchallenge.entity.NERObject;
import com.lperilla.projects.basfchallenge.entity.Patent;
import com.lperilla.projects.basfchallenge.exception.BasfException;

import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatentServiceImpl implements PatentService {

	private final MongoTemplate mongoTemplate;

	@Value("${basf.directory}")
	private File directory;

	@Override
	public void processFile(final Part file) {
		log.info("{} : {}", file.getSubmittedFileName(), file.getContentType());
		if (Utils.isXmlFile(file.getContentType())) {
			try {
				log.info("Moving {} file in directory {}", file.getSubmittedFileName(), directory);
				Utils.moveXmlFile(directory, file);
			} catch (IOException e) {
				throw new BasfException(String.format("Error moving %s file to directory %s", file.getSubmittedFileName(), directory), e);
			}
		} else if (Utils.isZipFile(file.getContentType())) {
			try {
				log.info("Unzipping {} file in directory {}", file.getSubmittedFileName(), directory);
				Utils.unZip(directory, file);
			} catch (IOException e) {
				throw new BasfException(String.format("Error unzipping %s file in directory %s", file.getSubmittedFileName(), directory), e);
			}
		} else {
			throw new BasfException(String.format("Invalid %s file type. Only XML and ZIP files are allowed.", file.getContentType()));
		}
	}

	@Override
	public boolean dropCollections() {
		mongoTemplate.dropCollection(Patent.class);
		mongoTemplate.dropCollection(NERObject.class);
		return true;
	}

}
