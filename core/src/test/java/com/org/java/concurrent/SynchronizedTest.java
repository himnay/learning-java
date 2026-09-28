package com.org.java.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class SynchronizedTest {

    private static final int NUM_INCREMENTS = 10_000;

    /** A plain int counter guarded by synchronized methods, which lock on the Counter instance. */
    private static final class Counter {
        private int count;

        synchronized void increment() { count++; }

        synchronized int get() { return count; }
    }

    @Test
    void synchronizedMethod_guaranteesCorrectCount() {
        Counter counter = new Counter();
        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, NUM_INCREMENTS).forEach(i -> executor.submit(counter::increment));
        ConcurrentUtils.stop(executor);
        assertEquals(NUM_INCREMENTS, counter.get());
    }

    @Test
    void synchronizedBlock_onClassObject_guaranteesCorrectCount() {
        int[] count = {0};
        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, NUM_INCREMENTS).forEach(i ->
                executor.submit(() -> {
                    synchronized (SynchronizedTest.class) {
                        count[0]++;
                    }
                }));
        ConcurrentUtils.stop(executor);
        assertEquals(NUM_INCREMENTS, count[0]);
    }
}
