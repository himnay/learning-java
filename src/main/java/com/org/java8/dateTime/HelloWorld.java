package com.org.java8.dateTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;

public class HelloWorld {
    public static void main(String[] args) {
        // 1. localDate
        LocalDate localDate = LocalDate.now();

        System.out.println(LocalDate.now());
        System.out.println(localDate.getDayOfMonth());
        System.out.println(localDate.getMonth());
        System.out.println(localDate.getDayOfMonth());
        System.out.println(localDate.getDayOfYear());

        // 2. localTime
        System.out.println(LocalTime.now());

        // 3. localDateTime
        System.out.println(LocalDateTime.now());

        // 1.1
        System.out.println(LocalDate.of(2020, 11, 02));

        // 1.2
        System.out.println(LocalDate.ofYearDay(2020, 300));

        // Temporal Fields
        System.out.println(localDate.get(ChronoField.DAY_OF_MONTH));
    }
}
