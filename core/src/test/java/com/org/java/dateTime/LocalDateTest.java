package com.org.java.dateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.*;

class LocalDateTest {

    @Test
    @DisplayName("LocalDate.now() returns a date with valid day-of-month and day-of-year fields")
    void now_hasValidFields() {
        LocalDate today = LocalDate.now();
        assertTrue(today.getDayOfMonth() >= 1 && today.getDayOfMonth() <= 31);
        assertNotNull(today.getMonth());
        assertTrue(today.getDayOfYear() >= 1 && today.getDayOfYear() <= 366);
    }

    @Test
    @DisplayName("LocalDate.of() creates a date with the specified year, month, and day")
    void of_createsSpecificDate() {
        LocalDate date = LocalDate.of(2020, 11, 2);
        assertEquals(2020, date.getYear());
        assertEquals(11, date.getMonthValue());
        assertEquals(2, date.getDayOfMonth());
    }

    @Test
    @DisplayName("LocalDate.ofYearDay() creates a date from a year and day-of-year offset")
    void ofYearDay_createsDateFromDayOfYear() {
        LocalDate date = LocalDate.ofYearDay(2020, 300);
        assertEquals(2020, date.getYear());
        assertEquals(300, date.getDayOfYear());
    }

    @Test
    @DisplayName("plusDays() returns a new date advanced by the given number of days without mutating the original")
    void plusDays_returnsNewDateWithoutMutating() {
        LocalDate today = LocalDate.now();
        LocalDate future = today.plusDays(10);
        assertEquals(10, ChronoUnit.DAYS.between(today, future));
        assertNotSame(today, future);
    }

    @Test
    @DisplayName("plusMonths() advances the date by the given number of months")
    void plusMonths_advancesByMonths() {
        LocalDate date = LocalDate.of(2020, 1, 15);
        assertEquals(LocalDate.of(2020, 3, 15), date.plusMonths(2));
    }

    @Test
    @DisplayName("plusWeeks() advances the date by the given number of weeks")
    void plusWeeks_advancesByWeeks() {
        LocalDate date = LocalDate.of(2020, 1, 1);
        assertEquals(LocalDate.of(2020, 1, 15), date.plusWeeks(2));
    }

    @Test
    @DisplayName("minusDays() moves the date backward by the given number of days")
    void minusDays_goesBackInTime() {
        LocalDate today = LocalDate.now();
        LocalDate past = today.minusDays(10);
        assertEquals(10, ChronoUnit.DAYS.between(past, today));
    }

    @Test
    @DisplayName("withYear() changes only the year while leaving month and day unchanged")
    void withYear_changesYearOnly() {
        LocalDate date = LocalDate.of(2020, 6, 15);
        assertEquals(2030, date.withYear(2030).getYear());
        assertEquals(6, date.withYear(2030).getMonthValue());
    }

    @Test
    @DisplayName("with(ChronoField.YEAR, ...) updates the year field on the date")
    void withChronoField_changesYear() {
        LocalDate date = LocalDate.of(2020, 6, 15);
        LocalDate updated = date.with(ChronoField.YEAR, 2030);
        assertEquals(2030, updated.getYear());
    }

    @Test
    @DisplayName("TemporalAdjusters.firstDayOfMonth() adjusts the date to the first day of its month")
    void firstDayOfMonth_adjuster() {
        LocalDate date = LocalDate.of(2020, 6, 15);
        LocalDate first = date.with(TemporalAdjusters.firstDayOfMonth());
        assertEquals(1, first.getDayOfMonth());
        assertEquals(6, first.getMonthValue());
    }

    @Test
    @DisplayName("isLeapYear() correctly identifies leap years, including century exceptions")
    void isLeapYear_returnsCorrectResult() {
        assertTrue(LocalDate.ofYearDay(2020, 1).isLeapYear());   // 2020 is leap
        assertFalse(LocalDate.ofYearDay(2019, 1).isLeapYear());  // 2019 is not
        assertFalse(LocalDate.ofYearDay(1900, 1).isLeapYear());  // divisible by 100, not 400
        assertTrue(LocalDate.ofYearDay(2000, 1).isLeapYear());   // divisible by 400
    }

    @Test
    @DisplayName("isAfter()/isBefore()/equals() correctly compare two LocalDate instances")
    void comparison_isAfterIsBefore() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        assertTrue(today.isAfter(yesterday));
        assertFalse(today.isBefore(yesterday));
        assertFalse(today.equals(yesterday));
    }

    @Test
    @DisplayName("isSupported() reports that DAYS is supported but MINUTES is not for LocalDate")
    void isSupported_minutesNotSupported() {
        assertFalse(LocalDate.now().isSupported(ChronoUnit.MINUTES));
        assertTrue(LocalDate.now().isSupported(ChronoUnit.DAYS));
    }

    @Test
    @DisplayName("get(ChronoField.DAY_OF_MONTH) returns the day-of-month component of the date")
    void chronoField_get() {
        LocalDate date = LocalDate.of(2020, 6, 15);
        assertEquals(15, date.get(ChronoField.DAY_OF_MONTH));
    }
}
