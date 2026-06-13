package com.org.java.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class SemaphoreTest {

    private static final int NUM_INCREMENTS = 1_000;

    @Test
    void semaphore1_guaranteesExclusiveAccess() {
        Semaphore semaphore = new Semaphore(1);
        AtomicInteger count = new AtomicInteger(0);
        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, NUM_INCREMENTS).forEach(i ->
                executor.submit(() -> {
                    try {
                        if (semaphore.tryAcquire(5, TimeUnit.SECONDS)) {
                            try { count.incrementAndGet(); } finally { semaphore.release(); }
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }));

        ConcurrentUtils.stop(executor);
        assertEquals(NUM_INCREMENTS, count.get());
    }

    @Test
    void semaphore5_allowsUpToFiveConcurrentPermits() {
        Semaphore semaphore = new Semaphore(5);
        AtomicInteger maxConcurrent = new AtomicInteger(0);
        AtomicInteger current = new AtomicInteger(0);
        ExecutorService executor = Executors.newFixedThreadPool(10);

        IntStream.range(0, 10).forEach(i ->
                executor.submit(() -> {
                    try {
                        if (semaphore.tryAcquire(1, TimeUnit.SECONDS)) {
                            int c = current.incrementAndGet();
                            maxConcurrent.updateAndGet(prev -> Math.max(prev, c));
                            ConcurrentUtils.sleep(1); // hold permit briefly
                            current.decrementAndGet();
                            semaphore.release();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }));

        ConcurrentUtils.stop(executor);
        assertTrue(maxConcurrent.get() <= 5);
    }

    @Test
    void acquire_release_basicBehavior() throws InterruptedException {
        Semaphore sem = new Semaphore(2);
        sem.acquire();
        sem.acquire();
        assertEquals(0, sem.availablePermits());
        sem.release();
        assertEquals(1, sem.availablePermits());
    }
}
