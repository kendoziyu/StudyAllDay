package newbie;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Question4 {

    public static void main(String[] args) {
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = LocalDate.of(2014, 6, 20);
        LocalDate date3 = LocalDate.parse("2014-06-20", DateTimeFormatter.ISO_DATE);
        System.out.println("date1 = " + date1);
        System.out.println("date2 = " + date2);
        System.out.println("date3 = " + date3);
        // Assume that the system date is June 20, 2014. What is the result?
        // A:
        // date1 = 2014-06-20
        // date2 = 2014-06-20
        // date3 = 2014-06-20
        // B:
        // date1 = 20/06/2014
        // date2 = 2014-06-20
        // date3 = June 20, 2014
        // C: Compilation fails
        // D: A DateTimeParseException is throw at runtime

        // 关于DateTimeFormatter
        // ISO_LOCAL_DATE 和 ISO_DATE 一样 2014-06-20
    }

}
