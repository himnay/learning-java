package com.org.java.dateTime;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class LocalTimeTest {

    @Test
    void now_hasValidFields() {
        LocalTime t = LocalTime.now();
        assertTrue(t.getHour() >= 0 && t.getHour() <= 23);
        assertTrue(t.getMinute() >= 0 && t.getMinute() <= 59);
    }

    @Test
    void of_createsExactTime() {
        LocalTime t = LocalTime.of(23, 59, 10);
        assertEquals(23, t.getHour());
        assertEquals(59, t.getMinute());
        assertEquals(10, t.getSecond());
    }

    @Test
    void of_withNanoseconds() {
        LocalTime t = LocalTime.of(23, 59, 10, 980_980_980);
        assertEquals(980_980_980, t.getNano());
    }

    @Test
    void getChronoField_returnsCorrectValue() {
        LocalTime t = LocalTime.of(14, 30);
        assertEquals(14, t.get(ChronoField.CLOCK_HOUR_OF_DAY));
        assertEquals(30, t.getMinute());
    }

    @Test
    void minusMinutes_decreasesTime() {
        LocalTime t = LocalTime.of(12, 30);
        assertEquals(LocalTime.of(12, 0), t.minusMinutes(30));
    }

    @Test
    void minus_withChronoUnit() {
        LocalTime t = LocalTime.of(12, 0);
        assertEquals(LocalTime.of(10, 0), t.minus(2, ChronoUnit.HOURS));
    }

    @Test
    void withMidnight_resetsToMidnight() {
        LocalTime t = LocalTime.of(15, 30);
        assertEquals(LocalTime.MIDNIGHT, t.with(LocalTime.MIDNIGHT));
    }

    @Test
    void withHourField_changesHour() {
        LocalTime t = LocalTime.of(10, 30);
        assertEquals(22, t.with(ChronoField.HOUR_OF_DAY, 22).getHour());
    }

    @Test
    void withHour_fluent() {
        LocalTime t = LocalTime.of(10, 30);
        assertEquals(LocalTime.of(8, 30), t.withHour(8));
    }

    @Test
    void plusMinutes_increasesTime() {
        LocalTime t = LocalTime.of(10, 50);
        assertEquals(LocalTime.of(11, 0), t.plusMinutes(10));
    }
}
