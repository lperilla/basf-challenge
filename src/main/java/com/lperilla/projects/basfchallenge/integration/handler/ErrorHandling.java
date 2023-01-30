package com.lperilla.projects.basfchallenge.integration.handler;

import org.springframework.core.AttributeAccessor;
import org.springframework.integration.support.ErrorMessageStrategy;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ErrorHandling implements ErrorMessageStrategy {

	@Override
	//@ServiceActivator(inputChannel = "errorChannel")
	public ErrorMessage buildErrorMessage(Throwable throwable, AttributeAccessor attributes) {
		log.error("An error occurred in the pipeline: " + throwable.getMessage(), throwable);
		return new ErrorMessage(throwable);
		//throw new BasfException("An error occurred in the pipeline");
	}
}