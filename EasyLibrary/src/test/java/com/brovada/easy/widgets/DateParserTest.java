package com.brovada.easy.widgets;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.ByteArrayInputStream;

public class DateParserTest  extends TestCase {

    @Test
    public void test_dateParser() throws ParseException {
        DateParser parser = new DateParser(new ByteArrayInputStream("".getBytes()));
        ParsedDate date = parser.parseDate("30 weeks");
        System.out.println(date);
        date = parser.parseDate("2001-10-08");
        System.out.println(date);
        date = parser.parseDate("feb 11 '19...feb 17");
        System.out.println(date);
        date = parser.parseDate("feb 1 2014 ... feb 17");
        System.out.println(date);
        date = parser.parseDate("next year");
        System.out.println(date);
        date = parser.parseDate("july 4 to next year");
        System.out.println(date);
        date = parser.parseDate("2001\\10\\08");
        System.out.println(date);
        date = parser.parseDate("last week for 2 week");
        System.out.println(date);
        date = parser.parseDate("this week for 1 month");
        System.out.println(date);
        date = parser.parseDate("next week for a year");
        System.out.println(date);
        date = parser.parseDate("yesterday for 6 YRS");
        System.out.println(date);
        date = parser.parseDate("today 1 YrS");
        System.out.println(date);
        date = parser.parseDate("yesterday tomorrow");
        System.out.println(date);
        date = parser.parseDate("july 17 for one quarter");
        System.out.println(date);
        date = parser.parseDate("july 14...last month");
        System.out.println(date);
        date = parser.parseDate("july 4 to next year");
        System.out.println(date);
        date = parser.parseDate("april 7- next wk");
        System.out.println(date);
        date = parser.parseDate("FEB 30 to FEB 14,1999");
        System.out.println(date);
        date = parser.parseDate("today");
        System.out.println(date);
        date = parser.parseDate("tomorrow");
        System.out.println(date);
        date = parser.parseDate("next week");
        System.out.println(date);
        date = parser.parseDate("feb 11 14..next week");
        System.out.println(date);
        date = parser.parseDate("feb 11 14..last week");
        System.out.println(date);
        date = parser.parseDate("feb 11 14 for 2 years");
        System.out.println(date);
        date = parser.parseDate("jan 1 99");
        System.out.println(date);
        date = parser.parseDate("feb 21 '99");
        System.out.println(date);
        date = parser.parseDate("feb 21 '99 to feb 22, 2055");
        System.out.println(date);
        date = parser.parseDate("feb 11 14...feb 17");
        System.out.println(date);
        date = parser.parseDate("01/11/99");
        System.out.println(date);
        date = parser.parseDate("15/04/2011");
        System.out.println(date);
        date = parser.parseDate("15\\04\\2011");
        System.out.println(date);
        date = parser.parseDate("15-04-2011");
        System.out.println(date);
        date = parser.parseDate("feb 11 14..feb 17");
        System.out.println(date);
        date = parser.parseDate("7 days");
        System.out.println(date);
        date = parser.parseDate("1 weeks");
        System.out.println(date);
        date = parser.parseDate("today for 43 months");
        System.out.println(date);
        date = parser.parseDate("1 WK");
        System.out.println(date);
    }

}
