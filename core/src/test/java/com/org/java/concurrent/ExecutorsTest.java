package com.org.java.concurrent;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class ExecutorsTest {

    @Test
    @DisplayName("A single-thread executor runs a submitted task to completion")
    void singleThreadExecutor_runsTaskToCompletion() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(() -> "done");
        assertEquals("done", future.get(5, TimeUnit.SECONDS));
        executor.shutdownNow();
    }

    @Test
    @DisplayName("Future.get() blocks until the submitted task completes and returns its result")
    void futureGet_returnsResultAfterCompletion() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> future = executor.submit(() -> {
            TimeUnit.MILLISECONDS.sleep(100);
            return 123;
        });
        assertFalse(future.isDone());
        assertEquals(123, future.get(5, TimeUnit.SECONDS));
        assertTrue(future.isDone());
        executor.shutdownNow();
    }

    @Test
    @DisplayName("Future.get() with a timeout throws TimeoutException when the task takes too long")
    void futureGet_throwsTimeoutExceptionWhenTooSlow() {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> future = executor.submit(() -> {
            TimeUnit.SECONDS.sleep(5);
            return 42;
        });
        assertThrows(TimeoutException.class,
                () -> future.get(200, TimeUnit.MILLISECONDS));
        future.cancel(true);
        executor.shutdownNow();
    }

    @Test
    @DisplayName("invokeAll() runs every submitted task and returns a completed future for each")
    void invokeAll_returnsAllResults() throws Exception {
        ExecutorService executor = Executors.newWorkStealingPool();
        List<Callable<String>> tasks = Arrays.asList(
                () -> "task1", () -> "task2", () -> "task3");
        List<Future<String>> futures = executor.invokeAll(tasks);
        assertEquals(3, futures.size());
        for (Future<String> f : futures) {
            assertTrue(f.isDone());
        }
        executor.shutdown();
    }

    @Test
    @DisplayName("invokeAny() returns the result of whichever submitted task finishes first")
    void invokeAny_returnsFirstCompletedResult() throws Exception {
        ExecutorService executor = Executors.newWorkStealingPool();
        List<Callable<String>> tasks = Arrays.asList(
                () -> { TimeUnit.SECONDS.sleep(2); return "slow"; },
                () -> "fast");
        String result = executor.invokeAny(tasks, 5, TimeUnit.SECONDS);
        assertEquals("fast", result);
        executor.shutdown();
    }

    @Test
    @DisplayName("A scheduled executor runs a task after the configured delay")
    void scheduledExecutor_schedulesTaskWithDelay() throws Exception {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        CountDownLatch latch = new CountDownLatch(1);
        executor.schedule(latch::countDown, 100, TimeUnit.MILLISECONDS);
        assertTrue(latch.await(5, TimeUnit.SECONDS));
        executor.shutdown();
    }
}
