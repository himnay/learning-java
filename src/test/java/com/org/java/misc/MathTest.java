package com.org.java.misc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathTest {

    @Test
    void addExact_throwsOnIntOverflow() {
        assertThrows(ArithmeticException.class,
                () -> Math.addExact(Integer.MAX_VALUE, 1));
    }

    @Test
    void toIntExact_throwsWhenLongExceedsIntRange() {
        assertThrows(ArithmeticException.class,
                () -> Math.toIntExact(Long.MAX_VALUE));
    }

    @Test
    void integerOverflow_wrapsAroundSilently() {
        // Plain int arithmetic wraps; addExact does not
        assertEquals(Integer.MIN_VALUE, Integer.MAX_VALUE + 1);
    }

    @Test
    void parseUnsignedInt_rejectsNegativeString() {
        assertThrows(NumberFormatException.class,
                () -> Integer.parseUnsignedInt("-123", 10));
    }

    @Test
    void parseUnsignedInt_acceptsValuesBeyondIntMaxValue() {
        long maxUnsigned = (1L << 32) - 1; // 4294967295
        String s = String.valueOf(maxUnsigned);
        int unsignedInt = Integer.parseUnsignedInt(s, 10);
        assertEquals(s, Integer.toUnsignedString(unsignedInt, 10));
    }

    @Test
    void parseInt_rejectsValuesBeyondIntMaxValue() {
        long maxUnsigned = (1L << 32) - 1;
        assertThrows(NumberFormatException.class,
                () -> Integer.parseInt(String.valueOf(maxUnsigned), 10));
    }
}
