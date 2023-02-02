package com.lperilla.projects.basfchallenge.service;

import com.lperilla.projects.basfchallenge.config.BasfChallengeProperties;
import com.lperilla.projects.basfchallenge.entity.Error;
import com.lperilla.projects.basfchallenge.entity.NERObject;
import com.lperilla.projects.basfchallenge.entity.Patent;
import com.lperilla.projects.basfchallenge.exception.BasfException;
import com.lperilla.projects.basfchallenge.repository.PatentRepository;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import jakarta.servlet.http.Part;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PatentServiceImpl implements PatentService {

    private final PatentRepository patentRepository;

    private final MongoTemplate mongoTemplate;

    private final StanfordCoreNLP stanfordCoreNLP;

    private final BasfChallengeProperties basfChallengeProperties;

    @Override
    public boolean processFile(final Part file) {
        log.info("Processing File {} : {}", file.getSubmittedFileName(), file.getContentType());
        if (Utils.isXmlFile(file.getContentType())) {
            log.info("Moving {} file in directory {}", file.getSubmittedFileName(), basfChallengeProperties.getDirectory());
            Utils.moveXmlFile(basfChallengeProperties.getDirectory(), file);
        } else if (Utils.isZipFile(file.getContentType())) {
            log.info("Unzipping {} file in directory {}", file.getSubmittedFileName(), basfChallengeProperties.getDirectory());
            Utils.unZip(basfChallengeProperties.getDirectory(), file);
        } else {
            throw new BasfException(String.format("Invalid %s file type. Only XML and ZIP files are allowed.", file.getContentType()));
        }
        return true;
    }

    @Override
    public boolean dropCollections() {
        mongoTemplate.dropCollection(Patent.class);
        mongoTemplate.dropCollection(Error.class);
        return true;
    }

    public List<Error> findAllError() {
        var query = new Query().with(Sort.by(Sort.Direction.DESC, "timestamp"));
        return mongoTemplate.find(query, Error.class);
    }

    @Override
    public List<NERObject> processAbstract(final String abstractText) {
        var doc = new Annotation(abstractText);
        stanfordCoreNLP.annotate(doc);
        List<NERObject> nerObjects = new ArrayList<>();
        for (CoreMap sentence : doc.get(CoreAnnotations.SentencesAnnotation.class)) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                var pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                if (basfChallengeProperties.getPartOfSpeech().contains(pos)) {
                    nerObjects.add(NERObject.builder()//
                            .pos(pos)//
                            .word(token.get(CoreAnnotations.TextAnnotation.class))//
                            .beginPosition(token.beginPosition())//
                            .endPosition(token.endPosition())//
                            .build());
                }
            }
        }
        return nerObjects;
    }

    @Override
    public Patent save(Patent patent) {
        return this.patentRepository.save(patent);
    }

}
