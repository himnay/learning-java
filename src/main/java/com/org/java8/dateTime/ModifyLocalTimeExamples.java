package com.org.java8.dateTime;

import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public class ModifyLocalTimeExamples {
    public static void main(String[] args) {
        // 1.
        LocalTime localTime = LocalTime.now();
        System.out.println(localTime);

        // 2.
        System.out.println(LocalTime.of(23, 59));
        System.out.println(LocalTime.of(23, 59, 10));
        System.out.println(LocalTime.of(23, 59, 10, 980980980));

        // 3.
        System.out.println(localTime.getHour());
        System.out.println(localTime.get(ChronoField.CLOCK_HOUR_OF_DAY));

        System.out.println(localTime.getMinute());

        System.out.println(localTime.get(ChronoField.SECOND_OF_DAY));

        // 4.
        System.out.println(localTime.minusMinutes(30));

        // 5.
        System.out.println(localTime.minus(2, ChronoUnit.HOURS));
        System.out.println(localTime.with(LocalTime.MIDNIGHT));
        System.out.println(localTime.with(ChronoField.HOUR_OF_DAY, 22));
        System.out.println(localTime.plusMinutes(10));
        System.out.println(localTime.withHour(10));
    }
}
