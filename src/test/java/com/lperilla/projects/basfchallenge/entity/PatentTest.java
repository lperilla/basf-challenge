package com.lperilla.projects.basfchallenge.entity;

import com.lperilla.projects.basfchallenge.service.Utils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PatentTest {

    @Test
    void testBuilder() throws ParseException {
        Patent.PatentBuilder builder = Patent.builder() //
                .country("US") //
                .abstractText("AbstractText")//
                .date(DateUtils.parseDateStrictly("20010101", "yyyyMMdd")) //
                .year(2001)
                .docNumber("DocNumber1") //
                .documentId("documentId1") //
                .kind("A") //
                .title("title") //
                .ner(Collections.emptyList());
        assertNotNull(builder);
        assertEquals("Patent(documentId=documentId1, title=title, abstractText=AbstractText, date=Mon Jan 01 00:00:00 CET 2001, year=2001, country=US, docNumber=DocNumber1, kind=A, ner=[])",
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
    void testDate() throws ParseException {
        Patent patent = new Patent();
        patent.setDate(Utils.dateParser("20010101"));
        assertEquals(DateUtils.parseDateStrictly("20010101", "yyyyMMdd"), patent.getDate());
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
    void testToString() throws ParseException {
        Patent patent = new Patent("Doc1", "Title1", "AbstractText", DateUtils.parseDateStrictly("20230101", "yyyyMMdd"), 2023, "US", "1235", "A",
                Collections.emptyList());
        assertNotNull(patent);
        assertNotNull(patent.getNer());
        assertEquals(
                "Patent(documentId=Doc1, title=Title1, abstractText=AbstractText, date=Sun Jan 01 00:00:00 CET 2023, year=2023, country=US, docNumber=1235, kind=A, ner=[])",
                patent.toString());
    }

    @Test
    void testPatent() throws ParseException {
        Patent patent = new Patent("Doc1", "Title1", "AbstractText", DateUtils.parseDateStrictly("20230101", "yyyyMMdd"), 2023, "US", "1235", "A",
                Collections.emptyList());
        assertNotNull(patent);
        Patent patent2 = new Patent();
        assertNotNull(patent2);
    }

}
