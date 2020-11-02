package com.org.java8.dateTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;

public class ModifyLocalDateTimeExamples {
    public static void main(String[] args) {
        // 1.
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        // 2.
        System.out.println(localDateTime.of(2020,01,21,22,23,24,2345676));

        // 3. get
        System.out.println(localDateTime.of(LocalDate.now(), LocalTime.now()));
        System.out.println(localDateTime.getHour());
        System.out.println(localDateTime.getMinute());
        System.out.println(localDateTime.getDayOfMonth());
        System.out.println(localDateTime.get(ChronoField.DAY_OF_MONTH));

        // 4. plus
        System.out.println(localDateTime.plusHours(1));
        System.out.println(localDateTime.plusMinutes(2));
        System.out.println(localDateTime.plusWeeks(3));
    }
}
