package com.lperilla.projects.basfchallenge.entity;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PatentTest {

    @Test
    void testBuilder() {
        Patent.PatentBuilder builder = Patent.builder() //
                .country("US") //
                .abstractText("AbstractText")//
                .date("20010101") //
                .docNumber("DocNumber1") //
                .documentId("documentId1") //
                .kind("A") //
                .title("title") //
                .ner(Collections.emptyList());
        assertNotNull(builder);
        assertEquals("Patent(documentId=documentId1, title=title, abstractText=AbstractText, date=20010101, country=US, docNumber=DocNumber1, kind=A, ner=[])",
                builder.build().toString());
    }

    @Test
    void testDocumentId() {
        Patent patent = new Patent();
        patent.setDocumentId("DOC1");
        assertEquals("DOC1", patent.getDocumentId());
    }

    @Test
    void testTitle() {
        Patent patent = new Patent();
        patent.setTitle("Title1");
        assertEquals("Title1", patent.getTitle());
    }

    @Test
    void testAbstractText() {
        Patent patent = new Patent();
        patent.setAbstractText("AbstractText");
        assertEquals("AbstractText", patent.getAbstractText());
    }

    @Test
    void testDate() {
        Patent patent = new Patent();
        patent.setDate("20010101");
        assertEquals("20010101", patent.getDate());
    }

    @Test
    void testCountry() {
        Patent patent = new Patent();
        patent.setCountry("US");
        assertEquals("US", patent.getCountry());
    }

    @Test
    void testDocNumber() {
        Patent patent = new Patent();
        patent.setDocNumber("Patent1");
        assertEquals("Patent1", patent.getDocNumber());
    }

    @Test
    void testKind() {
        Patent patent = new Patent();
        patent.setKind("A");
        assertEquals("A", patent.getKind());
    }

    @Test
    void testNer() {
        Patent patent = new Patent();
        patent.setNer(Collections.emptyList());
        assertNotNull(patent.getNer());
    }

    @Test
    void testToString() {
        Patent patent = new Patent("Doc1", "Title1", "AbstractText", "200101201", "US", "1235", "A",
                Collections.emptyList());
        assertNotNull(patent);
        assertNotNull(patent.getNer());
        assertEquals(
                "Patent(documentId=Doc1, title=Title1, abstractText=AbstractText, date=200101201, country=US, docNumber=1235, kind=A, ner=[])",
                patent.toString());
    }

    @Test
    void testPatent() {
        Patent patent = new Patent("Doc1", "Title1", "AbstractText", "200101201", "US", "1235", "A",
                Collections.emptyList());
        assertNotNull(patent);
        Patent patent2 = new Patent();
        assertNotNull(patent2);
    }

}
