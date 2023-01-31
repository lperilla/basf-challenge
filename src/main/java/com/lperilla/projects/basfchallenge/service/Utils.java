package com.lperilla.projects.basfchallenge.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.lang3.time.DateUtils;
import org.w3c.dom.Element;

import com.lperilla.projects.basfchallenge.exception.BasfException;

import jakarta.servlet.http.Part;
import lombok.experimental.UtilityClass;

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

	public static Date getDate(final Element element, final String tagName) {
		String date = getElementsByTagName(element, tagName);
		if (date != null) {
			try {
				return DateUtils.parseDateStrictly(date, "yyyyMMdd");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void moveXmlFile(final File directory, final Part file){
		try {
			File output = new File(directory, file.getSubmittedFileName());
			Files.copy(file.getInputStream(), output.toPath());
		} catch (IOException e) {
			throw new BasfException(String.format("Error moving %s file to directory %s", file.getSubmittedFileName(), directory), e);
		}
	}

	public static void unZip(final File directory, final Part file){
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
		}
		catch (IOException e) {
			throw new BasfException(String.format("Error unzipping %s file in directory %s", file.getSubmittedFileName(), directory), e);
		}
	}

}