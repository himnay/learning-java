package com.org.java.misc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathTest {

    @Test
    @DisplayName("Math.addExact() throws ArithmeticException instead of silently overflowing")
    void addExact_throwsOnIntOverflow() {
        assertThrows(ArithmeticException.class,
                () -> Math.addExact(Integer.MAX_VALUE, 1));
    }

    @Test
    @DisplayName("Math.toIntExact() throws ArithmeticException when a long value is outside the int range")
    void toIntExact_throwsWhenLongExceedsIntRange() {
        assertThrows(ArithmeticException.class,
                () -> Math.toIntExact(Long.MAX_VALUE));
    }

    @Test
    @DisplayName("Ordinary int addition silently wraps around on overflow instead of throwing")
    void integerOverflow_wrapsAroundSilently() {
        // Plain int arithmetic wraps; addExact does not
        assertEquals(Integer.MIN_VALUE, Integer.MAX_VALUE + 1);
    }

    @Test
    @DisplayName("Integer.parseUnsignedInt() rejects a string representing a negative number")
    void parseUnsignedInt_rejectsNegativeString() {
        assertThrows(NumberFormatException.class,
                () -> Integer.parseUnsignedInt("-123", 10));
    }

    @Test
    @DisplayName("Integer.parseUnsignedInt() accepts values beyond Integer.MAX_VALUE that fit as unsigned")
    void parseUnsignedInt_acceptsValuesBeyondIntMaxValue() {
        long maxUnsigned = (1L << 32) - 1; // 4294967295
        String s = String.valueOf(maxUnsigned);
        int unsignedInt = Integer.parseUnsignedInt(s, 10);
        assertEquals(s, Integer.toUnsignedString(unsignedInt, 10));
    }

    @Test
    @DisplayName("Integer.parseInt() rejects values that exceed the signed int range")
    void parseInt_rejectsValuesBeyondIntMaxValue() {
        long maxUnsigned = (1L << 32) - 1;
        assertThrows(NumberFormatException.class,
                () -> Integer.parseInt(String.valueOf(maxUnsigned), 10));
    }
}
