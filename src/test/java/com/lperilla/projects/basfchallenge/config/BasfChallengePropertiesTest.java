package com.lperilla.projects.basfchallenge.config;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class BasfChallengePropertiesTest {

    @Test
    void testDirectory() {
        var properties = new BasfChallengeProperties();
        assertNull(properties.getDirectory());
        properties.setDirectory(new File("/tmp"));
        assertNotNull(properties.getDirectory());
        assertEquals("tmp", properties.getDirectory().getName());
    }

    @Test
    void testToString() {
        var properties = new BasfChallengeProperties();
        properties.setDirectory(new File("/tmp"));
        assertEquals("BasfChallengeProperties(directory=/tmp, partOfSpeech=[NN, NNS, NNP, NNPS])", properties.toString());
    }

    @Test
    void basfChallengePropertiesTest_withOutArgs() {
        var properties = new BasfChallengeProperties();
        assertNotNull(properties);
        assertNull(properties.getDirectory());
    }
}