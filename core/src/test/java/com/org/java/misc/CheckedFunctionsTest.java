package com.org.java.misc;

import org.junit.jupiter.api.Test;

import java.io.IOException;
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
        IOException checked = new IOException("disk full");
        Function<String, String> read = CheckedFunctions.function(path -> { throw checked; });
        RuntimeException ex = assertThrows(RuntimeException.class, () -> read.apply("a.txt"));
        assertSame(checked, ex.getCause()); // wrapped, not lost
    }

    @Test
    void predicate_wrapsCheckedPredicate() {
        Predicate<String> isNumeric = CheckedFunctions.predicate(s -> {
            Integer.parseInt(s);
            return true;
        });
        assertTrue(isNumeric.test("123"));
        assertThrows(RuntimeException.class, () -> isNumeric.test("abc"));

        IOException checked = new IOException("unreadable");
        Predicate<String> exists = CheckedFunctions.predicate(path -> { throw checked; });
        assertSame(checked, assertThrows(RuntimeException.class, () -> exists.test("a.txt")).getCause());
    }

    @Test
    void consumer_wrapsCheckedConsumer() {
        StringBuilder sb = new StringBuilder();
        Consumer<String> appender = CheckedFunctions.consumer(sb::append);
        appender.accept("hello");
        assertEquals("hello", sb.toString());

        IOException checked = new IOException("stream closed");
        Consumer<String> writer = CheckedFunctions.consumer(line -> { throw checked; });
        assertSame(checked, assertThrows(RuntimeException.class, () -> writer.accept("x")).getCause());
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
