package com.org.java.dateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class LocalTimeTest {

    @Test
    @DisplayName("LocalTime.now() returns a time with valid hour and minute fields")
    void now_hasValidFields() {
        LocalTime t = LocalTime.now();
        assertTrue(t.getHour() >= 0 && t.getHour() <= 23);
        assertTrue(t.getMinute() >= 0 && t.getMinute() <= 59);
    }

    @Test
    @DisplayName("LocalTime.of() with hour, minute, and second creates the exact time requested")
    void of_createsExactTime() {
        LocalTime t = LocalTime.of(23, 59, 10);
        assertEquals(23, t.getHour());
        assertEquals(59, t.getMinute());
        assertEquals(10, t.getSecond());
    }

    @Test
    @DisplayName("LocalTime.of() with a nanosecond argument preserves the nanosecond value")
    void of_withNanoseconds() {
        LocalTime t = LocalTime.of(23, 59, 10, 980_980_980);
        assertEquals(980_980_980, t.getNano());
    }

    @Test
    @DisplayName("get(ChronoField.CLOCK_HOUR_OF_DAY) returns the correct clock-hour value")
    void getChronoField_returnsCorrectValue() {
        LocalTime t = LocalTime.of(14, 30);
        assertEquals(14, t.get(ChronoField.CLOCK_HOUR_OF_DAY));
        assertEquals(30, t.getMinute());
    }

    @Test
    @DisplayName("minusMinutes() subtracts minutes from the time")
    void minusMinutes_decreasesTime() {
        LocalTime t = LocalTime.of(12, 30);
        assertEquals(LocalTime.of(12, 0), t.minusMinutes(30));
    }

    @Test
    @DisplayName("minus() with a ChronoUnit subtracts the given amount of that unit from the time")
    void minus_withChronoUnit() {
        LocalTime t = LocalTime.of(12, 0);
        assertEquals(LocalTime.of(10, 0), t.minus(2, ChronoUnit.HOURS));
    }

    @Test
    @DisplayName("with(LocalTime.MIDNIGHT) resets the time to midnight")
    void withMidnight_resetsToMidnight() {
        LocalTime t = LocalTime.of(15, 30);
        assertEquals(LocalTime.MIDNIGHT, t.with(LocalTime.MIDNIGHT));
    }

    @Test
    @DisplayName("with(ChronoField.HOUR_OF_DAY, ...) changes only the hour field")
    void withHourField_changesHour() {
        LocalTime t = LocalTime.of(10, 30);
        assertEquals(22, t.with(ChronoField.HOUR_OF_DAY, 22).getHour());
    }

    @Test
    @DisplayName("withHour() fluently returns a copy of the time with a different hour")
    void withHour_fluent() {
        LocalTime t = LocalTime.of(10, 30);
        assertEquals(LocalTime.of(8, 30), t.withHour(8));
    }

    @Test
    @DisplayName("plusMinutes() rolls the minute value over into the next hour when it exceeds 59")
    void plusMinutes_increasesTime() {
        LocalTime t = LocalTime.of(10, 50);
        assertEquals(LocalTime.of(11, 0), t.plusMinutes(10));
    }
}
