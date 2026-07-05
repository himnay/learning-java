package com.org.java.concurrent;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ThreadsTest {

    @Test
    @DisplayName("A new Thread executes its Runnable on a thread distinct from the calling thread")
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
    @DisplayName("Calling run() directly on a Runnable executes it on the current thread, not a new one")
    void runnable_runOnCurrentThread_usesMainThreadName() {
        List<String> names = new ArrayList<>();
        Runnable r = () -> names.add(Thread.currentThread().getName());
        r.run(); // executes on current thread
        assertEquals(1, names.size());
        assertEquals(Thread.currentThread().getName(), names.get(0));
    }

    @Test
    @DisplayName("A sleeping thread still counts down the latch once it wakes up and completes")
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
