package com.org.java.concurrent;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ThreadsTest {

    @Test
    void thread_executesRunnableOnSeparateThread() throws InterruptedException {
        List<String> threadNames = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);

        Runnable runnable = () -> {
            threadNames.add(Thread.currentThread().getName());
            latch.countDown();
        };

        Thread thread = new Thread(runnable);
        thread.start();
        assertTrue(latch.await(5, TimeUnit.SECONDS));
        assertFalse(threadNames.isEmpty());
        assertNotEquals(Thread.currentThread().getName(), threadNames.get(0));
    }

    @Test
    void runnable_runOnCurrentThread_usesMainThreadName() {
        List<String> names = new ArrayList<>();
        Runnable r = () -> names.add(Thread.currentThread().getName());
        r.run(); // executes on current thread
        assertEquals(1, names.size());
        assertEquals(Thread.currentThread().getName(), names.get(0));
    }

    @Test
    void thread_sleepDoesNotPreventCompletion() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Thread t = new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                latch.countDown();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        t.start();
        assertTrue(latch.await(5, TimeUnit.SECONDS));
    }
}
