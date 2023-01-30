package com.lperilla.projects.basfchallenge.mutation;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.lperilla.projects.basfchallenge.service.PatentService;

import graphql.kickstart.tools.GraphQLMutationResolver;
import jakarta.servlet.http.Part;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UploadFileMutation implements GraphQLMutationResolver {

	private PatentService patentService;

	public boolean upload(Part part){
		Assert.notNull(part, "The file must not be null");
		patentService.processFile(part);
		return true;
	}

	public boolean dropCollections() {
		return patentService.dropCollections();
	}
}
