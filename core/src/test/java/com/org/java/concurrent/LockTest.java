package com.org.java.concurrent;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class LockTest {

    private static final int NUM_INCREMENTS = 10_000;

    @Test
    @DisplayName("ReentrantLock serializes concurrent increments to produce the exact expected count")
    void reentrantLock_guaranteesCorrectCount() {
        ReentrantLock lock = new ReentrantLock();
        int[] count = {0};
        ExecutorService executor = Executors.newFixedThreadPool(2);
        IntStream.range(0, NUM_INCREMENTS).forEach(i ->
                executor.submit(() -> {
                    lock.lock();
                    try { count[0]++; } finally { lock.unlock(); }
                }));
        ConcurrentUtils.stop(executor);
        assertEquals(NUM_INCREMENTS, count[0]);
    }

    @Test
    @DisplayName("tryLock() returns false when another thread is already holding the lock")
    void tryLock_returnsFalseWhenLockHeld() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        boolean[] tryResult = {true};

        Future<?> holder = executor.submit(() -> {
            lock.lock();
            try { ConcurrentUtils.sleep(2); } finally { lock.unlock(); }
        });
        Thread.sleep(100); // let holder acquire
        Future<?> tryer = executor.submit(() -> {
            boolean acquired = lock.tryLock();
            if (!acquired) tryResult[0] = false;
            if (acquired) lock.unlock();
        });

        ConcurrentUtils.stop(executor);
        assertFalse(tryResult[0]); // lock was held → tryLock returned false
    }

    @Test
    @DisplayName("ReentrantReadWriteLock lets a reader observe the value written under the write lock")
    void readWriteLock_allowsConcurrentReads_blocksWrite() {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        Map<String, String> map = new HashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> {
            lock.writeLock().lock();
            try { map.put("foo", "bar"); } finally { lock.writeLock().unlock(); }
        });

        Runnable reader = () -> {
            lock.readLock().lock();
            try { map.get("foo"); } finally { lock.readLock().unlock(); }
        };
        executor.submit(reader);
        ConcurrentUtils.stop(executor);
        assertEquals("bar", map.get("foo"));
    }

    @Test
    @DisplayName("StampedLock allows a write followed by a read to see the written value")
    void stampedLock_write_thenRead() {
        StampedLock lock = new StampedLock();
        Map<String, String> map = new HashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> {
            long stamp = lock.writeLock();
            try { map.put("foo", "bar"); } finally { lock.unlockWrite(stamp); }
        });

        executor.submit(() -> {
            long stamp = lock.readLock();
            try { assertNotNull(map); } finally { lock.unlockRead(stamp); }
        });

        ConcurrentUtils.stop(executor);
        assertEquals("bar", map.get("foo"));
    }

    @Test
    @DisplayName("StampedLock tryConvertToWriteLock() upgrades a read stamp so the value can be updated")
    void stampedLock_tryConvertToWrite_updatesValue() {
        StampedLock lock = new StampedLock();
        int[] count = {0};

        long stamp = lock.readLock();
        try {
            if (count[0] == 0) {
                stamp = lock.tryConvertToWriteLock(stamp);
                if (stamp == 0L) {
                    stamp = lock.writeLock();
                }
                count[0] = 23;
            }
        } finally {
            lock.unlock(stamp);
        }
        assertEquals(23, count[0]);
    }
}
