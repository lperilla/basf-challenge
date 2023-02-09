package com.lperilla.projects.basfchallenge.integration.transformer;

import com.lperilla.projects.basfchallenge.entity.Patent;
import com.lperilla.projects.basfchallenge.util.BasfUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.transformer.MessageTransformationException;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;

@Slf4j
@Component
@AllArgsConstructor
public class FileToPatentTransformer implements GenericTransformer<Message<File>, Message<Patent>> {

    private static final String TITLE_XPATH = "//invention-title/text()";

    private static final String ABSTRACT_XPATH = "//abstract[1]";

    private static final String DOCUMENT_ID_XPATH = "//publication-reference/document-id";

    @Override
    public Message<Patent> transform(Message<File> message) {
        var file = message.getPayload();
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDocument = builder.parse(new FileInputStream(file));
            XPath xPath = XPathFactory.newInstance().newXPath();
            var title = xPath.compile(TITLE_XPATH).evaluate(xmlDocument);
            var abstractText = xPath.compile(ABSTRACT_XPATH).evaluate(xmlDocument);
            var nodeList = (NodeList) xPath.compile(DOCUMENT_ID_XPATH).evaluate(xmlDocument, XPathConstants.NODESET);
            var element = (Element) nodeList.item(0);
            var country = BasfUtils.getElementsByTagName(element, "country");
            var docNumber = BasfUtils.getElementsByTagName(element, "doc-number");
            var kind = BasfUtils.getElementsByTagName(element, "kind");
            var date = DateUtils.parseDateStrictly(BasfUtils.getElementsByTagName(element, "date"), "yyyyMMdd");
            log.info("File {} processed successfully", file.getName());
            return MessageBuilder.createMessage(Patent.builder() //
                    .documentId(StringUtils.join(country, docNumber, kind))//
                    .title(title) //
                    .abstractText(abstractText.trim()) //
                    .country(country) //
                    .docNumber(docNumber) //
                    .kind(kind) //
                    .date(date) //
                    .year(BasfUtils.getFieldFromDate(date, Calendar.YEAR)).ner(new ArrayList<>()) //
                    .build(), message.getHeaders());
        } catch (Exception ex) {
            ErrorMessage error = new ErrorMessage(ex, message.getHeaders());
            throw new MessageTransformationException(error, String.format("Error in the transformation of xml in file %s", file), ex.getCause());
        } finally {
            try {
                Files.deleteIfExists(file.toPath());
            } catch (IOException ex) {
                ErrorMessage error = new ErrorMessage(ex, message.getHeaders());
                throw new MessageTransformationException(error, String.format("Error deleting file %s", file), ex.getCause());
            }
        }
    }
}
