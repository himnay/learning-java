package com.org.java.misc;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class CheckedFunctionsTest {

    @Test
    void function_wrapsCheckedExceptionAsRuntime() {
        Function<String, Integer> parseInt = CheckedFunctions.function(Integer::parseInt);
        assertEquals(42, parseInt.apply("42"));
    }

    @Test
    void function_rethrowsCheckedExceptionAsRuntimeException() {
        Function<String, Integer> parseInt = CheckedFunctions.function(Integer::parseInt);
        assertThrows(RuntimeException.class, () -> parseInt.apply("not-a-number"));
    }

    @Test
    void predicate_wrapsCheckedPredicate() {
        Predicate<String> isNumeric = CheckedFunctions.predicate(s -> {
            Integer.parseInt(s);
            return true;
        });
        assertTrue(isNumeric.test("123"));
        assertThrows(RuntimeException.class, () -> isNumeric.test("abc"));
    }

    @Test
    void consumer_wrapsCheckedConsumer() {
        StringBuilder sb = new StringBuilder();
        Consumer<String> appender = CheckedFunctions.consumer(sb::append);
        appender.accept("hello");
        assertEquals("hello", sb.toString());
    }

    @Test
    void function_passesRuntimeExceptionThrough() {
        Function<String, String> thrower = CheckedFunctions.function(s -> {
            throw new IllegalArgumentException("bad");
        });
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> thrower.apply("x"));
        assertEquals("bad", ex.getMessage());
    }
}
