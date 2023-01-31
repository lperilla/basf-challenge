package com.lperilla.projects.basfchallenge.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class NERObjectTest {

    @Test
    void testBuilder() {
        NERObject.NERObjectBuilder nerObject = NERObject.builder().word("Hello").pos("NN").beginPosition(1).endPosition(4);
        assertNotNull(nerObject);
        assertEquals("NERObject.NERObjectBuilder(word=Hello, pos=NN, beginPosition=1, endPosition=4)", nerObject.toString());
    }

    @Test
    void testWord() {
        NERObject nerObject = new NERObject();
        nerObject.setWord("Hello");
        assertEquals("Hello", nerObject.getWord());
    }

    @Test
    void testPos() {
        NERObject nerObject = new NERObject();
        nerObject.setPos("NN");
        assertEquals("NN", nerObject.getPos());
    }

    @Test
    void testBeginPosition() {
        NERObject nerObject = new NERObject();
        nerObject.setBeginPosition(1);
        assertEquals(1, nerObject.getBeginPosition());
    }

    @Test
    void testEndPosition() {
        NERObject nerObject = new NERObject();
        nerObject.setEndPosition(2);
        assertEquals(2, nerObject.getEndPosition());
    }

    @Test
    void testToString() {
        NERObject nerObject = NERObject.builder().word("Hello").pos("NN").beginPosition(1).endPosition(4).build();
        assertEquals("NERObject(word=Hello, pos=NN, beginPosition=1, endPosition=4)", nerObject.toString());
    }

    @Test
    void testNERObject_ConstructorWithArgs() {
        NERObject nerObject = new NERObject("hello", "NN", 1, 4);
        assertNotNull(nerObject);
    }

    @Test
    void testNERObject() {
        NERObject nerObject = new NERObject();
        assertNotNull(nerObject);
    }

}
