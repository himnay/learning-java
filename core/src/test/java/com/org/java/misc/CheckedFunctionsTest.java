package com.org.java.misc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class CheckedFunctionsTest {

    @Test
    @DisplayName("CheckedFunctions.function() wraps a checked-exception function and still returns its result normally")
    void function_wrapsCheckedExceptionAsRuntime() {
        Function<String, Integer> parseInt = CheckedFunctions.function(Integer::parseInt);
        assertEquals(42, parseInt.apply("42"));
    }

    @Test
    @DisplayName("CheckedFunctions.function() rethrows a caught checked exception as an unchecked RuntimeException")
    void function_rethrowsCheckedExceptionAsRuntimeException() {
        Function<String, Integer> parseInt = CheckedFunctions.function(Integer::parseInt);
        assertThrows(RuntimeException.class, () -> parseInt.apply("not-a-number"));
    }

    @Test
    @DisplayName("CheckedFunctions.predicate() wraps a checked predicate, passing valid input and throwing on invalid input")
    void predicate_wrapsCheckedPredicate() {
        Predicate<String> isNumeric = CheckedFunctions.predicate(s -> {
            Integer.parseInt(s);
            return true;
        });
        assertTrue(isNumeric.test("123"));
        assertThrows(RuntimeException.class, () -> isNumeric.test("abc"));
    }

    @Test
    @DisplayName("CheckedFunctions.consumer() wraps a checked consumer and still performs its side effect")
    void consumer_wrapsCheckedConsumer() {
        StringBuilder sb = new StringBuilder();
        Consumer<String> appender = CheckedFunctions.consumer(sb::append);
        appender.accept("hello");
        assertEquals("hello", sb.toString());
    }

    @Test
    @DisplayName("CheckedFunctions.function() lets an unchecked RuntimeException propagate through unchanged")
    void function_passesRuntimeExceptionThrough() {
        Function<String, String> thrower = CheckedFunctions.function(s -> {
            throw new IllegalArgumentException("bad");
        });
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> thrower.apply("x"));
        assertEquals("bad", ex.getMessage());
    }
}
