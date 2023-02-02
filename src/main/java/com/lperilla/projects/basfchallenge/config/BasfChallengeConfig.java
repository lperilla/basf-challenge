package com.lperilla.projects.basfchallenge.config;

import com.lperilla.projects.basfchallenge.entity.Patent;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.PropertiesUtils;
import graphql.kickstart.servlet.apollo.ApolloScalars;
import graphql.schema.GraphQLScalarType;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.GenericHandler;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import java.io.File;

@Configuration
@EnableConfigurationProperties(BasfChallengeProperties.class)
public class BasfChallengeConfig {
    @Bean
    public GraphQLScalarType uploadScalarType() {
        return ApolloScalars.Upload;
    }

    @Bean
    @InboundChannelAdapter(value = "fileInputChannel", poller = @Poller(fixedDelay = "1000"))
    public MessageSource<File> fileReadingMessageSource(BasfChallengeProperties basfChallengeProperties) {
        CompositeFileListFilter<File> filters = new CompositeFileListFilter<>();
        filters.addFilter(new SimplePatternFileListFilter("*.xml"));

        FileReadingMessageSource source = new FileReadingMessageSource();
        source.setAutoCreateDirectory(true);
        source.setDirectory(basfChallengeProperties.getDirectory());
        source.setFilter(filters);
        return source;
    }

    @Bean
    public MessageChannel errorChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow processFileFlow(GenericTransformer<Message<File>, Message<Patent>> fileToPatentTransformer, //
                                           GenericHandler<Patent> patentHandling) {
        return f -> f.channel("fileInputChannel") //
                .transform(fileToPatentTransformer) //
                .handle(patentHandling);
    }

    @Bean
    public StanfordCoreNLP stanfordCoreNLP() {
        return new StanfordCoreNLP(PropertiesUtils.asProperties("annotators", "tokenize,pos"));
    }
}