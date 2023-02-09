package com.lperilla.projects.basfchallenge.service;

import com.lperilla.projects.basfchallenge.config.BasfChallengeProperties;
import com.lperilla.projects.basfchallenge.entity.NERObject;
import com.lperilla.projects.basfchallenge.entity.Patent;
import com.lperilla.projects.basfchallenge.repository.PatentRepository;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PatentServiceImpl implements PatentService {

    private final PatentRepository patentRepository;

    private final StanfordCoreNLP stanfordCoreNLP;

    private final BasfChallengeProperties basfChallengeProperties;

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

    @Override
    public List<Patent> findAll() {
        return this.patentRepository.findAll();
    }

}
