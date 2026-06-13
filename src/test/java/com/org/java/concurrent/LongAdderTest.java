package com.org.java.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class LongAdderTest {

    private static final int NUM_INCREMENTS = 10_000;

    @Test
    void increment_isThreadSafe() {
        LongAdder adder = new LongAdder();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        IntStream.range(0, NUM_INCREMENTS).forEach(i -> executor.submit(adder::increment));
        ConcurrentUtils.stop(executor);
        assertEquals(NUM_INCREMENTS, adder.sumThenReset());
    }

    @Test
    void add_sumsFixedAmounts() {
        LongAdder adder = new LongAdder();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        IntStream.range(0, NUM_INCREMENTS).forEach(i -> executor.submit(() -> adder.add(2)));
        ConcurrentUtils.stop(executor);
        assertEquals(NUM_INCREMENTS * 2L, adder.sumThenReset());
    }

    @Test
    void sumThenReset_resetsToZero() {
        LongAdder adder = new LongAdder();
        adder.add(42);
        assertEquals(42, adder.sumThenReset());
        assertEquals(0, adder.sum());
    }
}
