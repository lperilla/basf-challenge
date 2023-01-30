package com.lperilla.projects.basfchallenge.integration.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.integration.core.GenericHandler;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import com.lperilla.projects.basfchallenge.entity.NERObject;
import com.lperilla.projects.basfchallenge.entity.Patent;

import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class NERProcessHandling implements GenericHandler<Patent> {

    private StanfordCoreNLP stanfordCoreNLP;

    @Override
    public Object handle(Patent payload, MessageHeaders headers) {
        Annotation doc = new Annotation(payload.getAbstractText());
        stanfordCoreNLP.annotate(doc);

        List<CoreMap> sentences = doc.get(SentencesAnnotation.class);
        List<NERObject> nerObjects = new ArrayList<>();
        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
                var word = token.get(TextAnnotation.class);
                var pos = token.get(PartOfSpeechAnnotation.class);
                var beginPosition = token.beginPosition();
                var endPostion = token.endPosition();
                nerObjects.add(NERObject.builder().id(UUID.randomUUID()).word(word).pos(pos).beginPosition(beginPosition).endPosition(endPostion).build());
            }
        }
        return MessageBuilder.withPayload(nerObjects);
    }
}