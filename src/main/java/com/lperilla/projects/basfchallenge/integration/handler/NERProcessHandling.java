package com.lperilla.projects.basfchallenge.integration.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	private static final List<String> PART_OF_SPEECH = Arrays.asList("NN", "NNS", "NNP", "NNPS");

	@Override
	public Object handle(Patent payload, MessageHeaders headers) {
		Annotation doc = new Annotation(payload.getAbstractText());
		stanfordCoreNLP.annotate(doc);
		List<NERObject> nerObjects = new ArrayList<>();
		List<CoreMap> sentences = doc.get(SentencesAnnotation.class);
		for (CoreMap sentence : sentences) {
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				var pos = token.get(PartOfSpeechAnnotation.class);
				if (PART_OF_SPEECH.contains(pos)) {
					nerObjects.add(NERObject.builder()//
							.pos(pos)//
							.word(token.get(TextAnnotation.class))//
							.beginPosition(token.beginPosition())//
							.endPosition(token.endPosition())//
							.build());
				}
			}
		}
		payload.setNer(nerObjects);
		return MessageBuilder.withPayload(payload);
	}
}