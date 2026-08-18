package com.org.java.concurrent;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class ExecutorsTest {

    @Test
    void singleThreadExecutor_runsTaskToCompletion() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(() -> "done");
        assertEquals("done", future.get(5, TimeUnit.SECONDS));
        executor.shutdownNow();
    }

    @Test
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
    void scheduledExecutor_schedulesTaskWithDelay() throws Exception {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        CountDownLatch latch = new CountDownLatch(1);
        executor.schedule(latch::countDown, 100, TimeUnit.MILLISECONDS);
        assertTrue(latch.await(5, TimeUnit.SECONDS));
        executor.shutdown();
    }
}
