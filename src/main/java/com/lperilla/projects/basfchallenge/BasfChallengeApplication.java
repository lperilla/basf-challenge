package com.lperilla.projects.basfchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication
@IntegrationComponentScan
@EnableMongoRepositories
public class BasfChallengeApplication {
    public static void main(String[] args) {
        SpringApplication.run(BasfChallengeApplication.class, args);
    }

}