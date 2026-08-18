package com.org.java.dateTime;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;

import static org.junit.jupiter.api.Assertions.*;

class LocalDateTimeTest {

    @Test
    void now_hasValidDateAndTimeFields() {
        LocalDateTime ldt = LocalDateTime.now();
        assertTrue(ldt.getHour() >= 0 && ldt.getHour() <= 23);
        assertTrue(ldt.getDayOfMonth() >= 1 && ldt.getDayOfMonth() <= 31);
    }

    @Test
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
    void of_fromLocalDateAndLocalTime() {
        LocalDate date = LocalDate.of(2020, 6, 15);
        LocalTime time = LocalTime.of(10, 30);
        LocalDateTime ldt = LocalDateTime.of(date, time);
        assertEquals(2020, ldt.getYear());
        assertEquals(10,   ldt.getHour());
    }

    @Test
    void getField_viaChronoField() {
        LocalDateTime ldt = LocalDateTime.of(2020, 6, 15, 10, 30, 0);
        assertEquals(15, ldt.get(ChronoField.DAY_OF_MONTH));
    }

    @Test
    void plusHours_addsHours() {
        LocalDateTime ldt = LocalDateTime.of(2020, 6, 15, 10, 0);
        assertEquals(11, ldt.plusHours(1).getHour());
    }

    @Test
    void plusMinutes_addsMinutes() {
        LocalDateTime ldt = LocalDateTime.of(2020, 6, 15, 10, 58);
        LocalDateTime result = ldt.plusMinutes(2);
        assertEquals(11, result.getHour());
        assertEquals(0,  result.getMinute());
    }

    @Test
    void plusWeeks_addsWeeks() {
        LocalDateTime ldt = LocalDateTime.of(2020, 1, 1, 0, 0);
        assertEquals(LocalDateTime.of(2020, 1, 22, 0, 0), ldt.plusWeeks(3));
    }
}
