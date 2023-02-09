package com.lperilla.projects.basfchallenge.process;

import com.lperilla.projects.basfchallenge.exception.BasfException;
import org.springframework.stereotype.Component;

@Component
public class PartProcessFactory {

    private static final String APPLICATION_ZIP_VALUE = "application/zip";

    private static final String APPLICATION_XML_VALUE = "application/xml";

    public PartProcess getFileProcess(final String contentType) {
        if (APPLICATION_XML_VALUE.equals(contentType)) {
            return new XmlPartProcess();
        } else if (APPLICATION_ZIP_VALUE.equals(contentType)) {
            return new ZipPartProcess();
        }
        throw new BasfException(String.format("Invalid %s file type. Only XML and ZIP files are allowed.", contentType));
    }

}
