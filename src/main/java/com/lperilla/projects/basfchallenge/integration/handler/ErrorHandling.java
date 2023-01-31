package com.lperilla.projects.basfchallenge.integration.handler;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ErrorHandling{

	@ServiceActivator(inputChannel = "errorChannel")
	public void handleError(Throwable e) {
		log.error(">>>>>>>>>>> error: {}", e);
	}
}