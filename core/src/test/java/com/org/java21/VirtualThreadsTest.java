// FILE 9: src/test/java/com/org/java21/VirtualThreadsTest.java
package com.org.java21;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Virtual Threads (Java 21)")
class VirtualThreadsTest {

    // ---------------------------------------------------------------------------
    // Thread.ofVirtual().start()
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Thread.ofVirtual().start() creates and immediately starts a virtual thread")
    void ofVirtualStart() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        Thread vt = Thread.ofVirtual().start(() -> counter.incrementAndGet());
        vt.join();
        assertEquals(1, counter.get(), "Virtual thread must have executed the runnable");
    }

    @Test
    @DisplayName("Thread.ofVirtual().start() creates a virtual (not platform) thread")
    void virtualThreadIsVirtual() throws InterruptedException {
        Thread vt = Thread.ofVirtual().start(() -> {});
        vt.join();
        assertTrue(vt.isVirtual(), "Thread must report isVirtual() == true");
    }

    @Test
    @DisplayName("Platform thread reports isVirtual() == false")
    void platformThreadIsNotVirtual() throws InterruptedException {
        Thread pt = Thread.ofPlatform().start(() -> {});
        pt.join();
        assertFalse(pt.isVirtual(), "Platform thread must report isVirtual() == false");
    }

    // ---------------------------------------------------------------------------
    // Named virtual threads
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Virtual thread builder can set a name")
    void namedVirtualThread() throws InterruptedException {
        Thread vt = Thread.ofVirtual().name("my-vthread").start(() -> {});
        vt.join();
        assertEquals("my-vthread", vt.getName());
    }

    // ---------------------------------------------------------------------------
    // Thread.ofVirtual().unstarted()
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("ofVirtual().unstarted() creates thread but does not start it")
    void unstartedVirtualThread() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        Thread vt = Thread.ofVirtual().unstarted(() -> counter.incrementAndGet());
        // Thread is not yet started — counter should still be zero
        assertEquals(0, counter.get());
        vt.start();
        vt.join();
        assertEquals(1, counter.get());
    }

    // ---------------------------------------------------------------------------
    // Thread.ofVirtual().factory()
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Thread.ofVirtual().factory() produces virtual threads")
    void virtualThreadFactory() throws InterruptedException {
        ThreadFactory factory = Thread.ofVirtual().name("vt-", 0).factory();
        Thread vt1 = factory.newThread(() -> {});
        Thread vt2 = factory.newThread(() -> {});
        assertTrue(vt1.isVirtual());
        assertTrue(vt2.isVirtual());
        vt1.start(); vt1.join();
        vt2.start(); vt2.join();
    }

    @Test
    @DisplayName("Factory-created virtual threads carry sequential names")
    void virtualThreadFactorySequentialNames() throws InterruptedException {
        ThreadFactory factory = Thread.ofVirtual().name("worker-", 0).factory();
        Thread t0 = factory.newThread(() -> {});
        Thread t1 = factory.newThread(() -> {});
        assertEquals("worker-0", t0.getName());
        assertEquals("worker-1", t1.getName());
        t0.start(); t0.join();
        t1.start(); t1.join();
    }

    // ---------------------------------------------------------------------------
    // Executors.newVirtualThreadPerTaskExecutor()
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("newVirtualThreadPerTaskExecutor() runs tasks on virtual threads")
    void virtualThreadPerTaskExecutor() throws Exception {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<Boolean> future = executor.submit(() -> Thread.currentThread().isVirtual());
            assertTrue(future.get(), "Tasks must run on virtual threads");
        }
    }

    @Test
    @DisplayName("newVirtualThreadPerTaskExecutor() runs many concurrent tasks")
    void manyTasksOnVirtualThreads() throws Exception {
        int taskCount = 1000;
        AtomicInteger completed = new AtomicInteger(0);
        List<Future<?>> futures = new ArrayList<>();

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < taskCount; i++) {
                futures.add(executor.submit(() -> {
                    assertTrue(Thread.currentThread().isVirtual());
                    completed.incrementAndGet();
                }));
            }
            for (Future<?> f : futures) f.get(); // wait for all
        }

        assertEquals(taskCount, completed.get());
    }

    @Test
    @DisplayName("newVirtualThreadPerTaskExecutor() tasks run sequentially can carry a result")
    void virtualThreadPerTaskExecutorWithResult() throws Exception {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<Integer> f = executor.submit(() -> {
                int sum = 0;
                for (int i = 1; i <= 10; i++) sum += i;
                return sum;
            });
            assertEquals(55, f.get());
        }
    }

    // ---------------------------------------------------------------------------
    // Blocking inside virtual threads
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Virtual thread survives sleep() without blocking platform thread")
    void virtualThreadCanSleep() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        Thread vt = Thread.ofVirtual().start(() -> {
            try {
                Thread.sleep(1); // minimal sleep
                counter.incrementAndGet();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        vt.join(2000); // wait up to 2 s
        assertEquals(1, counter.get());
    }

    // ---------------------------------------------------------------------------
    // Thread state
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("A finished virtual thread has state TERMINATED")
    void terminatedVirtualThreadState() throws InterruptedException {
        Thread vt = Thread.ofVirtual().start(() -> {});
        vt.join();
        assertEquals(Thread.State.TERMINATED, vt.getState());
    }

    @Test
    @DisplayName("Virtual thread is a daemon thread")
    void virtualThreadIsDaemon() throws InterruptedException {
        Thread vt = Thread.ofVirtual().start(() -> {});
        vt.join();
        assertTrue(vt.isDaemon(), "Virtual threads are always daemon threads");
    }
}
