package com.lperilla.projects.basfchallenge.query;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Controller;

@Controller
public class UploadFileQuery implements GraphQLQueryResolver {

    public String sayHello(String name) {
        return "Hello " + name;
    }
}
