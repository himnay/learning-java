package com.org.java.concurrent;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class AtomicTest {

    private static final int NUM_INCREMENTS = 1000;

    @Test
    @DisplayName("AtomicInteger increments correctly under concurrent access from multiple threads")
    void incrementAndGet_isThreadSafe() {
        AtomicInteger atomicInt = new AtomicInteger(0);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        IntStream.range(0, NUM_INCREMENTS).forEach(i -> executor.submit(atomicInt::incrementAndGet));
        ConcurrentUtils.stop(executor);
        assertEquals(NUM_INCREMENTS, atomicInt.get());
    }

    @Test
    @DisplayName("accumulateAndGet sums all concurrently submitted values using Integer::sum")
    void accumulateAndGet_sumsAllSubmittedValues() {
        AtomicInteger atomicInt = new AtomicInteger(0);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        // Sum 0..NUM_INCREMENTS-1
        IntStream.range(0, NUM_INCREMENTS).forEach(i ->
                executor.submit(() -> atomicInt.accumulateAndGet(i, Integer::sum)));
        ConcurrentUtils.stop(executor);
        int expected = (NUM_INCREMENTS - 1) * NUM_INCREMENTS / 2; // Gauss
        assertEquals(expected, atomicInt.get());
    }

    @Test
    @DisplayName("updateAndGet applies the update function consistently across concurrent calls")
    void updateAndGet_addsConstantPerUpdate() {
        AtomicInteger atomicInt = new AtomicInteger(0);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        IntStream.range(0, NUM_INCREMENTS).forEach(i ->
                executor.submit(() -> atomicInt.updateAndGet(n -> n + 2)));
        ConcurrentUtils.stop(executor);
        assertEquals(NUM_INCREMENTS * 2, atomicInt.get());
    }

    @Test
    @DisplayName("compareAndSet succeeds and updates the value when the expected value matches")
    void compareAndSet_succeedsWhenExpectedMatches() {
        AtomicInteger ai = new AtomicInteger(0);
        assertTrue(ai.compareAndSet(0, 10));
        assertEquals(10, ai.get());
    }

    @Test
    @DisplayName("compareAndSet fails and leaves the value unchanged when the expected value does not match")
    void compareAndSet_failsWhenExpectedDoesNotMatch() {
        AtomicInteger ai = new AtomicInteger(5);
        assertFalse(ai.compareAndSet(0, 10));
        assertEquals(5, ai.get());
    }
}
