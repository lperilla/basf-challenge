package com.lperilla.projects.basfchallenge.query;

import org.springframework.stereotype.Component;

import graphql.kickstart.tools.GraphQLQueryResolver;

@Component
public class UploadFileQuery implements GraphQLQueryResolver {

	public String sayHello(String name){
		return "Hello "+name;
	}
}
