package com.org.java.concurrent;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class LongAdderTest {

    private static final int NUM_INCREMENTS = 10_000;

    @Test
    @DisplayName("LongAdder accumulates the exact count under concurrent increments")
    void increment_isThreadSafe() {
        LongAdder adder = new LongAdder();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        IntStream.range(0, NUM_INCREMENTS).forEach(i -> executor.submit(adder::increment));
        ConcurrentUtils.stop(executor);
        assertEquals(NUM_INCREMENTS, adder.sumThenReset());
    }

    @Test
    @DisplayName("LongAdder.add() sums fixed amounts added concurrently from multiple threads")
    void add_sumsFixedAmounts() {
        LongAdder adder = new LongAdder();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        IntStream.range(0, NUM_INCREMENTS).forEach(i -> executor.submit(() -> adder.add(2)));
        ConcurrentUtils.stop(executor);
        assertEquals(NUM_INCREMENTS * 2L, adder.sumThenReset());
    }

    @Test
    @DisplayName("sumThenReset() returns the current sum and resets the adder back to zero")
    void sumThenReset_resetsToZero() {
        LongAdder adder = new LongAdder();
        adder.add(42);
        assertEquals(42, adder.sumThenReset());
        assertEquals(0, adder.sum());
    }
}
