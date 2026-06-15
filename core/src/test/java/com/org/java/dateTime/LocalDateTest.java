package com.org.java.dateTime;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.*;

class LocalDateTest {

    @Test
    void now_hasValidFields() {
        LocalDate today = LocalDate.now();
        assertTrue(today.getDayOfMonth() >= 1 && today.getDayOfMonth() <= 31);
        assertNotNull(today.getMonth());
        assertTrue(today.getDayOfYear() >= 1 && today.getDayOfYear() <= 366);
    }

    @Test
    void of_createsSpecificDate() {
        LocalDate date = LocalDate.of(2020, 11, 2);
        assertEquals(2020, date.getYear());
        assertEquals(11, date.getMonthValue());
        assertEquals(2, date.getDayOfMonth());
    }

    @Test
    void ofYearDay_createsDateFromDayOfYear() {
        LocalDate date = LocalDate.ofYearDay(2020, 300);
        assertEquals(2020, date.getYear());
        assertEquals(300, date.getDayOfYear());
    }

    @Test
    void plusDays_returnsNewDateWithoutMutating() {
        LocalDate today = LocalDate.now();
        LocalDate future = today.plusDays(10);
        assertEquals(10, ChronoUnit.DAYS.between(today, future));
        assertNotSame(today, future);
    }

    @Test
    void plusMonths_advancesByMonths() {
        LocalDate date = LocalDate.of(2020, 1, 15);
        assertEquals(LocalDate.of(2020, 3, 15), date.plusMonths(2));
    }

    @Test
    void plusWeeks_advancesByWeeks() {
        LocalDate date = LocalDate.of(2020, 1, 1);
        assertEquals(LocalDate.of(2020, 1, 15), date.plusWeeks(2));
    }

    @Test
    void minusDays_goesBackInTime() {
        LocalDate today = LocalDate.now();
        LocalDate past = today.minusDays(10);
        assertEquals(10, ChronoUnit.DAYS.between(past, today));
    }

    @Test
    void withYear_changesYearOnly() {
        LocalDate date = LocalDate.of(2020, 6, 15);
        assertEquals(2030, date.withYear(2030).getYear());
        assertEquals(6, date.withYear(2030).getMonthValue());
    }

    @Test
    void withChronoField_changesYear() {
        LocalDate date = LocalDate.of(2020, 6, 15);
        LocalDate updated = date.with(ChronoField.YEAR, 2030);
        assertEquals(2030, updated.getYear());
    }

    @Test
    void firstDayOfMonth_adjuster() {
        LocalDate date = LocalDate.of(2020, 6, 15);
        LocalDate first = date.with(TemporalAdjusters.firstDayOfMonth());
        assertEquals(1, first.getDayOfMonth());
        assertEquals(6, first.getMonthValue());
    }

    @Test
    void isLeapYear_returnsCorrectResult() {
        assertTrue(LocalDate.ofYearDay(2020, 1).isLeapYear());   // 2020 is leap
        assertFalse(LocalDate.ofYearDay(2019, 1).isLeapYear());  // 2019 is not
        assertFalse(LocalDate.ofYearDay(1900, 1).isLeapYear());  // divisible by 100, not 400
        assertTrue(LocalDate.ofYearDay(2000, 1).isLeapYear());   // divisible by 400
    }

    @Test
    void comparison_isAfterIsBefore() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        assertTrue(today.isAfter(yesterday));
        assertFalse(today.isBefore(yesterday));
        assertFalse(today.equals(yesterday));
    }

    @Test
    void isSupported_minutesNotSupported() {
        assertFalse(LocalDate.now().isSupported(ChronoUnit.MINUTES));
        assertTrue(LocalDate.now().isSupported(ChronoUnit.DAYS));
    }

    @Test
    void chronoField_get() {
        LocalDate date = LocalDate.of(2020, 6, 15);
        assertEquals(15, date.get(ChronoField.DAY_OF_MONTH));
    }
}
