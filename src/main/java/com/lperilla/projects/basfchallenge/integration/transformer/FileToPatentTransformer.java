package com.lperilla.projects.basfchallenge.integration.transformer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringUtils;
import org.springframework.integration.file.transformer.AbstractFilePayloadTransformer;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.lperilla.projects.basfchallenge.entity.Patent;
import com.lperilla.projects.basfchallenge.exception.BasfException;
import com.lperilla.projects.basfchallenge.service.Utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class FileToPatentTransformer extends AbstractFilePayloadTransformer<Patent> {

	private static final String TITLE_XPATH = "//invention-title/text()";

	private static final String ABSTRACT_XPATH = "//abstract[1]";

	private static final String DOCUMENT_ID_XPATH = "//publication-reference/document-id";

	@Override
	protected Patent transformFile(File file) throws IOException {
		Patent patent = null;
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document xmlDocument = builder.parse(new FileInputStream(file));
			XPath xPath = XPathFactory.newInstance().newXPath();
			var title = xPath.compile(TITLE_XPATH).evaluate(xmlDocument);
			var abstractText = xPath.compile(ABSTRACT_XPATH).evaluate(xmlDocument);
			var nodeList = (NodeList) xPath.compile(DOCUMENT_ID_XPATH).evaluate(xmlDocument, XPathConstants.NODESET);
			var element = (Element) nodeList.item(0);
			var country = Utils.getElementsByTagName(element, "country");
			var docNumber = Utils.getElementsByTagName(element, "doc-number");
			var kind = Utils.getElementsByTagName(element, "kind");
			var date = Utils.getElementsByTagName(element, "date");
			log.info("File {} processed successfully", file.getName());
			patent = Patent.builder() //
					.documentId(StringUtils.join(country, docNumber, kind))//
					.title(title) //
					.abstractText(abstractText.trim()) //
					.country(country) //
					.docNumber(docNumber)//
					.kind(kind)//
					.date(date)//
					.ner(new ArrayList<>())//
					.build();
		} catch (XPathExpressionException | ParserConfigurationException | SAXException | IOException ex) {
			throw new BasfException("Error in the transformation of the file to xml", ex);
		} finally {
			Files.deleteIfExists(file.toPath());
		}
		return patent;
	}

}
