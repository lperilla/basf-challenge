package com.lperilla.projects.basfchallenge.query;

import com.lperilla.projects.basfchallenge.entity.Error;
import com.lperilla.projects.basfchallenge.entity.Patent;
import com.lperilla.projects.basfchallenge.service.ErrorService;
import com.lperilla.projects.basfchallenge.service.PatentService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@AllArgsConstructor
public class UploadFileQuery implements GraphQLQueryResolver {

    private PatentService patentService;

    private ErrorService errorService;

    public String sayHello(String name) {
        return "Hello " + name;
    }

    public List<Error> findAllError() {
        return errorService.findAllByOrderByTimestampDesc();
    }

    public List<Patent> findAllPatent() {
        return patentService.findAll();
    }
}
