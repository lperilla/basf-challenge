package com.lperilla.projects.basfchallenge.mutation;

import com.lperilla.projects.basfchallenge.config.BasfChallengeProperties;
import com.lperilla.projects.basfchallenge.service.DatabaseService;
import com.lperilla.projects.basfchallenge.process.PartProcessFactory;
import graphql.kickstart.tools.GraphQLMutationResolver;
import jakarta.servlet.http.Part;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

@Slf4j
@Controller
@AllArgsConstructor
public class UploadFileMutation implements GraphQLMutationResolver {

    private DatabaseService databaseService;

    private PartProcessFactory fileProcessFactory;

    private final BasfChallengeProperties basfChallengeProperties;

    public boolean upload(Part file) {
        Assert.notNull(file, "The file must not be null");
        log.info("Processing File {} : {}", file.getSubmittedFileName(), file.getContentType());
        fileProcessFactory.getFileProcess(file.getContentType()).process(basfChallengeProperties.getDirectory(), file);
        return true;
    }

    public boolean dropCollections() {
        databaseService.deleteAll();
        return true;
    }
}
