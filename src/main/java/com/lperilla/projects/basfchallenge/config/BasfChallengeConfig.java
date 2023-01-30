package com.lperilla.projects.basfchallenge.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;

import com.lperilla.projects.basfchallenge.integration.handler.NERPersistenceHandling;
import com.lperilla.projects.basfchallenge.integration.handler.NERProcessHandling;
import com.lperilla.projects.basfchallenge.integration.handler.PatentPersistenceHandling;
import com.lperilla.projects.basfchallenge.integration.transformer.FileToPatentTransformer;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.PropertiesUtils;
import graphql.kickstart.servlet.apollo.ApolloScalars;
import graphql.schema.GraphQLScalarType;

@Configuration
public class BasfChallengeConfig {

	@Value("${basf.directory}")
	private File directory;

	@Bean
	public GraphQLScalarType uploadScalarType() {
		return ApolloScalars.Upload;
	}

	@Bean
	@InboundChannelAdapter(value = "fileInputChannel", poller = @Poller(fixedDelay = "1000"))
	public MessageSource<File> fileReadingMessageSource() {
		CompositeFileListFilter<File> filters = new CompositeFileListFilter<>();
		filters.addFilter(new SimplePatternFileListFilter("*.xml"));

		FileReadingMessageSource source = new FileReadingMessageSource();
		source.setAutoCreateDirectory(true);
		source.setDirectory(directory);
		source.setFilter(filters);
		return source;
	}

	@Bean
	@Autowired
	public IntegrationFlow processFileFlow(FileToPatentTransformer fileToPatentTransformer,
			PatentPersistenceHandling patentPersistenceHandling, //
			NERProcessHandling nerProcessHandling, //
			NERPersistenceHandling nerPersistenceHandling) {
		fileToPatentTransformer.setDeleteFiles(true);
		return f -> f.channel("fileInputChannel") //
				.transform(fileToPatentTransformer) //
				.handle(patentPersistenceHandling) //
				.handle(nerProcessHandling) //
				.handle(nerPersistenceHandling);
	}

	@Bean
	public StanfordCoreNLP stanfordCoreNLP() {
		return new StanfordCoreNLP(PropertiesUtils.asProperties("annotators",
				"tokenize,ssplit,pos,lemma,depparse,natlog,openie", "coref.algorithm", "neural"));
	}
}