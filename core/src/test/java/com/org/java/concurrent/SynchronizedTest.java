package com.org.java.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class SynchronizedTest {

    private static final int NUM_INCREMENTS = 10_000;

    @Test
    void synchronizedMethod_guaranteesCorrectCount() {
        AtomicInteger count = new AtomicInteger(0);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Object lock = new Object();

        IntStream.range(0, NUM_INCREMENTS).forEach(i ->
                executor.submit(() -> {
                    synchronized (lock) {
                        count.incrementAndGet();
                    }
                }));
        ConcurrentUtils.stop(executor);
        assertEquals(NUM_INCREMENTS, count.get());
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
