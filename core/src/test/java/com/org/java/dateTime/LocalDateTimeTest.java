package com.org.java.dateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;

import static org.junit.jupiter.api.Assertions.*;

class LocalDateTimeTest {

    @Test
    @DisplayName("LocalDateTime.now() returns a value with valid hour and day-of-month fields")
    void now_hasValidDateAndTimeFields() {
        LocalDateTime ldt = LocalDateTime.now();
        assertTrue(ldt.getHour() >= 0 && ldt.getHour() <= 23);
        assertTrue(ldt.getDayOfMonth() >= 1 && ldt.getDayOfMonth() <= 31);
    }

    @Test
    @DisplayName("LocalDateTime.of() with explicit year/month/day/hour/minute/second/nano sets every field")
    void of_fromYearMonthDayHourMinuteSecondNano() {
        LocalDateTime ldt = LocalDateTime.of(2020, 1, 21, 22, 23, 24, 2_345_676);
        assertEquals(2020, ldt.getYear());
        assertEquals(1,    ldt.getMonthValue());
        assertEquals(21,   ldt.getDayOfMonth());
        assertEquals(22,   ldt.getHour());
        assertEquals(23,   ldt.getMinute());
        assertEquals(24,   ldt.getSecond());
    }

    @Test
    @DisplayName("LocalDateTime.of() built from a LocalDate and LocalTime combines both correctly")
    void of_fromLocalDateAndLocalTime() {
        LocalDate date = LocalDate.of(2020, 6, 15);
        LocalTime time = LocalTime.of(10, 30);
        LocalDateTime ldt = LocalDateTime.of(date, time);
        assertEquals(2020, ldt.getYear());
        assertEquals(10,   ldt.getHour());
    }

    @Test
    @DisplayName("get(ChronoField.DAY_OF_MONTH) returns the day-of-month component of a LocalDateTime")
    void getField_viaChronoField() {
        LocalDateTime ldt = LocalDateTime.of(2020, 6, 15, 10, 30, 0);
        assertEquals(15, ldt.get(ChronoField.DAY_OF_MONTH));
    }

    @Test
    @DisplayName("plusHours() advances the hour component by the given amount")
    void plusHours_addsHours() {
        LocalDateTime ldt = LocalDateTime.of(2020, 6, 15, 10, 0);
        assertEquals(11, ldt.plusHours(1).getHour());
    }

    @Test
    @DisplayName("plusMinutes() rolls over into the next hour when the minute total exceeds 59")
    void plusMinutes_addsMinutes() {
        LocalDateTime ldt = LocalDateTime.of(2020, 6, 15, 10, 58);
        LocalDateTime result = ldt.plusMinutes(2);
        assertEquals(11, result.getHour());
        assertEquals(0,  result.getMinute());
    }

    @Test
    @DisplayName("plusWeeks() advances the date by the given number of weeks while keeping the time unchanged")
    void plusWeeks_addsWeeks() {
        LocalDateTime ldt = LocalDateTime.of(2020, 1, 1, 0, 0);
        assertEquals(LocalDateTime.of(2020, 1, 22, 0, 0), ldt.plusWeeks(3));
    }
}
