package demo;

import junit.framework.TestCase;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.Period;

public class JodaTest extends TestCase {

    public void test_joda() {
        LocalDate localDate = new LocalDate(2014, 12, 31);
        localDate.plusDays(4).minusMonths(7).plus(Period.days(23));
        LocalDate anotherDate = new LocalDate();
        int result = localDate.compareTo(anotherDate);
        Months months = Months.monthsBetween(localDate, anotherDate);
        LocalDate firstOfMonth = new LocalDate().withDayOfMonth(1);
        new DateTime().compareTo(new LocalDate().toDateTimeAtCurrentTime());
    }

}
