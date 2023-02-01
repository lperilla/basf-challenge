package com.lperilla.projects.basfchallenge.service;

import com.lperilla.projects.basfchallenge.exception.BasfException;
import jakarta.servlet.http.Part;
import lombok.experimental.UtilityClass;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@UtilityClass
public class Utils {

    private static final String APPLICATION_ZIP_VALUE = "application/zip";

    private static final String APPLICATION_XML_VALUE = "application/xml";

    private static final String TEXT_XML_VALUE = "text/xml";

    public static boolean isXmlFile(final String contentType) {
        return (APPLICATION_XML_VALUE.equals(contentType) || TEXT_XML_VALUE.equals(contentType));
    }

    public static boolean isZipFile(final String contentType) {
        return (APPLICATION_ZIP_VALUE.equals(contentType));
    }

    public static String getElementsByTagName(final Element element, final String tagName) {
        if (element != null && element.getElementsByTagName(tagName).getLength() > 0) {
            return element.getElementsByTagName(tagName).item(0).getTextContent();
        }
        throw new BasfException(String.format("The tag %s there isn't in the document xml", tagName));
    }

    public static int getFieldFromDate(Date date, int field) {
        if (date != null) {
            var calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(field);
        }
        return -1;
    }

    public static Date dateParser(String date) {
        try {
            return new SimpleDateFormat("yyyyMMdd").parse(date);
        } catch (ParseException e) {
            throw new BasfException(String.format("Error parsing date %s", date), e);
        }
    }

    public static long moveXmlFile(final File directory, final Part file) {
        try {
            File output = new File(directory, file.getSubmittedFileName());
            return Files.copy(file.getInputStream(), output.toPath());
        } catch (IOException e) {
            throw new BasfException(String.format("Error moving %s file to directory %s", file.getSubmittedFileName(), directory), e);
        }
    }

    public static boolean unZip(final File directory, final Part file) {
        try (ZipInputStream zis = new ZipInputStream(file.getInputStream())) {
            ZipEntry zipEntry = zis.getNextEntry();
            byte[] buffer = new byte[1024];
            while (zipEntry != null) {
                File destFile = new File(directory, zipEntry.getName());
                try (FileOutputStream fos = new FileOutputStream(destFile)) {
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                }
                zis.closeEntry();
                zipEntry = zis.getNextEntry();
            }
            return true;
        } catch (IOException e) {
            throw new BasfException(String.format("Error unzipping %s file in directory %s", file.getSubmittedFileName(), directory), e);
        }
    }

}