// FILE 15: src/test/java/com/org/java23/StructuredConcurrencyTest.java
package com.org.java23;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Structured Concurrency tests using the Java 26 API:
 *   StructuredTaskScope.open(Joiner)
 *
 * In Java 26, ShutdownOnFailure / ShutdownOnSuccess inner classes were replaced
 * by factory methods on StructuredTaskScope.Joiner:
 *   - Joiner.allSuccessfulOrThrow()  — all tasks must succeed, collect results
 *   - Joiner.awaitAllSuccessfulOrThrow() — all must succeed, discard results (Void)
 *   - Joiner.anySuccessfulOrThrow()  — first success wins
 *   - Joiner.awaitAll()              — wait for all, ignore failures
 */
@DisplayName("Structured Concurrency (Java 21–26 API)")
class StructuredConcurrencyTest {

    // -----------------------------------------------------------------------
    // allSuccessfulOrThrow — all tasks must succeed; result is List<T>
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("allSuccessfulOrThrow: both subtasks succeed, results are available")
    void allSuccessfulOrThrowBothSucceed() throws Exception {
        try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.<String>allSuccessfulOrThrow())) {
            var task1 = scope.<String>fork(() -> "result1");
            var task2 = scope.<String>fork(() -> "result2");
            List<String> results = scope.join();
            assertTrue(results.contains("result1"));
            assertTrue(results.contains("result2"));
            assertEquals(StructuredTaskScope.Subtask.State.SUCCESS, task1.state());
            assertEquals(StructuredTaskScope.Subtask.State.SUCCESS, task2.state());
        }
    }

    @Test
    @DisplayName("allSuccessfulOrThrow: forked Subtask.get() returns the computed value")
    void allSuccessfulOrThrowSubtaskGet() throws Exception {
        try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.<Integer>allSuccessfulOrThrow())) {
            var task = scope.<Integer>fork(() -> 42);
            scope.join();
            assertEquals(42, task.get());
        }
    }

    @Test
    @DisplayName("allSuccessfulOrThrow: when one subtask throws, join() throws FailedException")
    void allSuccessfulOrThrowOneTaskFails() {
        assertThrows(StructuredTaskScope.FailedException.class, () -> {
            try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.<String>allSuccessfulOrThrow())) {
                scope.<String>fork(() -> { throw new RuntimeException("deliberate failure"); });
                scope.<String>fork(() -> {
                    Thread.sleep(100);
                    return "ok";
                });
                scope.join(); // throws FailedException
            }
        });
    }

    @Test
    @DisplayName("allSuccessfulOrThrow: failing subtask state is FAILED")
    void subtaskStateFailedOnException() throws Exception {
        try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.<String>awaitAll())) {
            var failing = scope.<String>fork(() -> { throw new RuntimeException("error"); });
            scope.join(); // awaitAll never throws, just waits
            assertEquals(StructuredTaskScope.Subtask.State.FAILED, failing.state());
            assertNotNull(failing.exception());
            assertEquals("error", failing.exception().getMessage());
        }
    }

    // -----------------------------------------------------------------------
    // anySuccessfulOrThrow — first success wins
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("anySuccessfulOrThrow: returns the first successful result")
    void anySuccessfulOrThrowFirstWins() throws Exception {
        try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.<String>anySuccessfulOrThrow())) {
            scope.<String>fork(() -> {
                Thread.sleep(100);
                return "slow";
            });
            scope.<String>fork(() -> "fast");
            String winner = scope.join();
            assertNotNull(winner);
            assertFalse(winner.isEmpty());
        }
    }

    @Test
    @DisplayName("anySuccessfulOrThrow: when all subtasks fail, join() throws FailedException")
    void anySuccessfulOrThrowAllFail() {
        assertThrows(StructuredTaskScope.FailedException.class, () -> {
            try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.<String>anySuccessfulOrThrow())) {
                scope.<String>fork(() -> { throw new RuntimeException("fail1"); });
                scope.<String>fork(() -> { throw new RuntimeException("fail2"); });
                scope.join();
            }
        });
    }

    // -----------------------------------------------------------------------
    // awaitAllSuccessfulOrThrow — all must succeed, Void result
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("awaitAllSuccessfulOrThrow: multiple void tasks complete successfully")
    void awaitAllSuccessfulOrThrowMultipleVoid() throws Exception {
        var counter = new java.util.concurrent.atomic.AtomicInteger(0);
        try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.<Void>awaitAllSuccessfulOrThrow())) {
            scope.<Void>fork(() -> { counter.incrementAndGet(); return null; });
            scope.<Void>fork(() -> { counter.incrementAndGet(); return null; });
            scope.<Void>fork(() -> { counter.incrementAndGet(); return null; });
            scope.join();
        }
        assertEquals(3, counter.get());
    }

    // -----------------------------------------------------------------------
    // awaitAll — tolerant joiner: waits for all regardless of outcome
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("awaitAll: completes even when some tasks fail")
    void awaitAllToleratesFailures() throws Exception {
        var success = new java.util.concurrent.atomic.AtomicBoolean(false);
        try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.<String>awaitAll())) {
            scope.<String>fork(() -> { throw new RuntimeException("boom"); });
            scope.<String>fork(() -> { success.set(true); return "ok"; });
            scope.join(); // returns Void, does not throw
        }
        assertTrue(success.get(), "The successful task should have run to completion");
    }

    // -----------------------------------------------------------------------
    // Nested scopes
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("Nested StructuredTaskScopes complete inner scope before outer proceeds")
    void nestedScopes() throws Exception {
        try (var outer = StructuredTaskScope.open(StructuredTaskScope.Joiner.<Integer>allSuccessfulOrThrow())) {
            var outerTask = outer.<Integer>fork(() -> {
                try (var inner = StructuredTaskScope.open(StructuredTaskScope.Joiner.<Integer>allSuccessfulOrThrow())) {
                    var a = inner.<Integer>fork(() -> 10);
                    var b = inner.<Integer>fork(() -> 20);
                    inner.join(); // inner must complete before outer task returns
                    return a.get() + b.get();
                }
            });
            outer.join();
            assertEquals(30, outerTask.get());
        }
    }

    // -----------------------------------------------------------------------
    // Aggregating multiple results
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("allSuccessfulOrThrow aggregates results from five subtasks")
    void aggregateMultipleSubtasks() throws Exception {
        try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.<Integer>allSuccessfulOrThrow())) {
            var tasks = List.of(
                    scope.<Integer>fork(() -> 1),
                    scope.<Integer>fork(() -> 2),
                    scope.<Integer>fork(() -> 3),
                    scope.<Integer>fork(() -> 4),
                    scope.<Integer>fork(() -> 5)
            );
            scope.join();
            int sum = tasks.stream().mapToInt(StructuredTaskScope.Subtask::get).sum();
            assertEquals(15, sum);
        }
    }

    @Test
    @DisplayName("Subtask running on a virtual thread completes correctly")
    void subtaskCompletesOnVirtualThread() throws Exception {
        try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.<String>allSuccessfulOrThrow())) {
            var task = scope.<String>fork(() -> {
                Thread.sleep(1); // simulate brief I/O
                return "io-done";
            });
            scope.join();
            assertEquals("io-done", task.get());
        }
    }

    // -----------------------------------------------------------------------
    // FailedException carries the original cause
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("FailedException wraps the original exception as its cause")
    void failedExceptionCause() {
        StructuredTaskScope.FailedException ex = assertThrows(
                StructuredTaskScope.FailedException.class, () -> {
                    try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.<String>allSuccessfulOrThrow())) {
                        scope.<String>fork(() -> { throw new IllegalStateException("root cause"); });
                        scope.join();
                    }
                });
        assertNotNull(ex.getCause(), "FailedException must have a cause");
        assertInstanceOf(IllegalStateException.class, ex.getCause());
        assertEquals("root cause", ex.getCause().getMessage());
    }
}
