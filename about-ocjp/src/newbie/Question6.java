package newbie;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Question6 {

    public static void main(String[] args) {
        String date = LocalDate
                        .parse("2014-05-04")
                        .format(DateTimeFormatter.ISO_DATE_TIME);

        System.out.println(date);
        // What is the result?
        // A. May 04, 2014T00:00:00.000
        // B. 2014-05-04T00:00: 00. 000
        // C. 5/4/14T00:00:00.000
        // D. An exception is thrown at runtime.


        // Answer: D
        // Explanation:
        // java.time.temporal.UnsupportedTemporalTypeException: Unsupported field: HourOfDay
        // ISO_LOCAL_DATE_TIME 形如 '2011-12-03T10:15:30'
        // ISO_DATE_TIME 形如 '2011-12-03T10:15:30',
        //   '2011-12-03T10:15:30+01:00' or '2011-12-03T10:15:30+01:00[Europe/Paris]'
    }
}
