package com.org.java8.dateTime;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class ModifyLocalDateExamples {
    public static void main(String[] args) {
        // 1. increment without changing the actual date
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate.plusDays(10));
        System.out.println(localDate.plusMonths(10));
        System.out.println(localDate.plusWeeks(10));
        System.out.println(localDate.plusYears(10));

        // 2. decrement
        System.out.println(localDate.minusDays(10));
        System.out.println(localDate.minusMonths(10));
        System.out.println(localDate.minusWeeks(10));
        System.out.println(localDate.minusYears(10));

        System.out.println(localDate.withYear(2030));

        System.out.println(localDate.with(ChronoField.YEAR, 2030));

        System.out.println(localDate.with(TemporalAdjusters.firstDayOfMonth()));

        System.out.println(localDate.minusDays(300));

        System.out.println(localDate.minus(1, ChronoUnit.YEARS));

        // 3. leap year
        System.out.println(localDate.isLeapYear());
        System.out.println(LocalDate.ofYearDay(2020, 1).isLeapYear());

        // 4. equal, isAfter & isBefore
        System.out.println(localDate.equals(localDate.minusDays(1)));
        System.out.println(localDate.isAfter(localDate.minusDays(1)));
        System.out.println(localDate.isBefore(localDate.minusDays(1)));

        // 5. supported
        System.out.println(localDate.isSupported(ChronoUnit.MINUTES));

    }
}
