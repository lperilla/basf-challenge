package com.lperilla.projects.basfchallenge.util;

import com.lperilla.projects.basfchallenge.exception.BasfException;
import lombok.experimental.UtilityClass;
import org.w3c.dom.Element;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@UtilityClass
public class BasfUtils {

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

}