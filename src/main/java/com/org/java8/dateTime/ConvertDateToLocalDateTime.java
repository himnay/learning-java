package com.org.java8.dateTime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class ConvertDateToLocalDateTime {
    public static void main(String[] args) {
        // 1. convert date to localDate
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        System.out.println(date);
        System.out.println(localDate);

        // 2. localDate to Date
        date = new Date().from(localDate.atTime(LocalTime.now()).atZone(ZoneId.systemDefault()).toInstant());
        System.out.println(date);

        // 3. sql date to localDate
        java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
        System.out.println(sqlDate);

        localDate = sqlDate.toLocalDate();
        System.out.println(localDate);
    }
}
