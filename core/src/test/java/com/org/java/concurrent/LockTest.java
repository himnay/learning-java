package com.org.java.concurrent;

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
    void tryLock_returnsFalseWhenLockHeld() throws Exception {
        ReentrantLock lock = new ReentrantLock();
        CountDownLatch held = new CountDownLatch(1);
        CountDownLatch release = new CountDownLatch(1);
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Future<?> holder = executor.submit(() -> {
            lock.lock();
            try {
                held.countDown();
                return release.await(5, TimeUnit.SECONDS); // keep the lock until the other thread has tried
            } finally { lock.unlock(); }
        });
        assertTrue(held.await(5, TimeUnit.SECONDS)); // the holder owns the lock now (no sleep-and-hope)

        Future<Boolean> tryer = executor.submit(() -> {
            boolean acquired = lock.tryLock();
            if (acquired) lock.unlock();
            return acquired;
        });
        boolean acquired = tryer.get(5, TimeUnit.SECONDS);
        release.countDown();
        holder.get(5, TimeUnit.SECONDS);
        ConcurrentUtils.stop(executor);

        assertFalse(acquired); // lock was held → tryLock returned false
    }

    @Test
    void readWriteLock_allowsConcurrentReads_blocksWrite() throws Exception {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        Map<String, String> map = new HashMap<>();
        lock.writeLock().lock();
        try { map.put("foo", "bar"); } finally { lock.writeLock().unlock(); }

        CountDownLatch bothReading = new CountDownLatch(2);
        CountDownLatch release = new CountDownLatch(1);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Callable<String> reader = () -> {
            lock.readLock().lock();
            try {
                bothReading.countDown();
                release.await(5, TimeUnit.SECONDS); // hold the read lock until the writer has tried
                return map.get("foo");
            } finally { lock.readLock().unlock(); }
        };
        Future<String> first = executor.submit(reader);
        Future<String> second = executor.submit(reader);

        assertTrue(bothReading.await(5, TimeUnit.SECONDS)); // both threads hold the read lock at once
        assertFalse(lock.writeLock().tryLock());             // so a writer cannot get in
        release.countDown();
        assertEquals("bar", first.get(5, TimeUnit.SECONDS));
        assertEquals("bar", second.get(5, TimeUnit.SECONDS));
        ConcurrentUtils.stop(executor);
    }

    @Test
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
    void stampedLock_optimisticRead_isInvalidatedByAWrite() {
        StampedLock lock = new StampedLock();
        long stamp = lock.tryOptimisticRead(); // no lock taken, just a version stamp
        assertTrue(lock.validate(stamp));      // nothing was written since

        long writeStamp = lock.writeLock();
        lock.unlockWrite(writeStamp);
        assertFalse(lock.validate(stamp));     // a write happened: re-read under a real read lock
    }

    @Test
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
