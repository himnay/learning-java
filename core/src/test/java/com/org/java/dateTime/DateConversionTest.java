package com.org.java.dateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DateConversionTest {

    @Test
    @DisplayName("Converting a java.util.Date to LocalDate preserves year, month, and day")
    void dateToLocalDate_preservesYearMonthDay() {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();
        assertEquals(today.getYear(),       localDate.getYear());
        assertEquals(today.getMonthValue(), localDate.getMonthValue());
        assertEquals(today.getDayOfMonth(), localDate.getDayOfMonth());
    }

    @Test
    @DisplayName("Converting a LocalDate to Date and back yields the original date")
    void localDateToDate_roundTrips() {
        LocalDate original = LocalDate.of(2020, 6, 15);
        Date date = Date.from(original.atTime(LocalTime.NOON).atZone(ZoneId.systemDefault()).toInstant());
        LocalDate converted = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        assertEquals(original, converted);
    }

    @Test
    @DisplayName("Converting a LocalDate to java.sql.Date preserves the date and its string representation")
    void sqlDateToLocalDate_preservesDate() {
        LocalDate localDate = LocalDate.of(2020, 6, 15);
        java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
        assertEquals(localDate, sqlDate.toLocalDate());
        assertEquals("2020-06-15", sqlDate.toString());
    }

    @Test
    @DisplayName("Converting a LocalDate to java.sql.Date and back yields the original date")
    void localDateToSqlDate_roundTrips() {
        LocalDate original = LocalDate.of(2021, 12, 31);
        java.sql.Date sqlDate = java.sql.Date.valueOf(original);
        assertEquals(original, sqlDate.toLocalDate());
    }
}
