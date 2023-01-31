package com.lperilla.projects.basfchallenge.service;

import com.lperilla.projects.basfchallenge.config.BasfChallengeProperties;
import com.lperilla.projects.basfchallenge.entity.Patent;
import com.lperilla.projects.basfchallenge.exception.BasfException;
import jakarta.servlet.http.Part;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@MockitoSettings
class PatentServiceImplTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private BasfChallengeProperties basfChallengeProperties;
    @TempDir
    File directory;

    @InjectMocks
    private PatentServiceImpl patentService;

    @Test
    void testProcessFile_xmlFile() throws FileNotFoundException, IOException {
        Part part = mock(Part.class);
        when(part.getContentType()).thenReturn("application/xml");
        when(part.getSubmittedFileName()).thenReturn("US06060048A.xml");
        when(basfChallengeProperties.getDirectory()).thenReturn(directory);
        try (MockedStatic<Utils> utilsMockedStatic = mockStatic(Utils.class)) {
            utilsMockedStatic.when(() -> Utils.isXmlFile(anyString())).thenReturn(true);
            utilsMockedStatic.when(() -> Utils.moveXmlFile(any(File.class), any(Part.class))).thenReturn(100L);
            assertDoesNotThrow(() -> patentService.processFile(part));
        }
    }

    @Test
    void testProcessFile_zipFile() throws FileNotFoundException, IOException {
        Part part = mock(Part.class);
        when(part.getContentType()).thenReturn("application/zip");
        when(part.getSubmittedFileName()).thenReturn("test.zip");
        when(basfChallengeProperties.getDirectory()).thenReturn(directory);
        try (MockedStatic<Utils> utilsMockedStatic = mockStatic(Utils.class)) {
            utilsMockedStatic.when(() -> Utils.isZipFile(anyString())).thenReturn(true);
            utilsMockedStatic.when(() -> Utils.unZip(any(File.class), any(Part.class))).thenReturn(true);
            assertDoesNotThrow(() -> patentService.processFile(part));
        }
    }

    @Test
    void testProcessFile_isNotXmlOrZip() throws FileNotFoundException, IOException {
        Part part = mock(Part.class);
        when(part.getContentType()).thenReturn("application/txt");
        when(part.getSubmittedFileName()).thenReturn("test.txt");
        assertThrows(BasfException.class, () -> patentService.processFile(part));
    }

    @Test
    void testDropCollections() {
        doNothing().when(mongoTemplate).dropCollection(Patent.class);
        assertTrue(patentService.dropCollections());
    }

    @Test
    void testPatentServiceImpl() {
        assertNotNull(patentService);
    }

}
