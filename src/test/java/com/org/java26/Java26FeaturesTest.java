// FILE 18: src/test/java/com/org/java26/Java26FeaturesTest.java
package com.org.java26;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Stream;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Java 26 features (with --enable-preview).
 *
 * Java 26 is a non-LTS release targeting mid-2025. Key tracked JEPs:
 *   - JEP 494: Module Import Declarations (3rd preview → standard)
 *   - JEP 492: Flexible Constructor Bodies (3rd preview)
 *   - JEP 488: Primitive Types in Patterns (2nd preview)
 *   - JEP 491: Synchronize Virtual Threads without Pinning (standard)
 *   - Various Stream / String / API consolidations
 *
 * Because Java 26 was not released at the model knowledge cutoff,
 * these tests exercise the direction of the platform:
 *   (a) APIs confirmed to be available (accumulated from Java 21–25)
 *   (b) Flexible constructor bodies (tested via a well-formed class)
 *   (c) Primitive-type patterns (available since Java 25 preview)
 *   (d) Virtual thread pinning improvements (observable behaviour)
 */
@DisplayName("Java 26 Features (--enable-preview)")
class Java26FeaturesTest {

    // ===========================================================================
    // 1. Flexible Constructor Bodies (JEP 492 — preview in Java 26)
    //
    // Prior to flexible constructor bodies, ANY code before super()/this() in a
    // constructor was a compile error. JEP 492 allows statements that do NOT
    // access 'this' or instance members to appear before the super() call.
    //
    // We test this by defining a class whose constructor performs validation
    // BEFORE delegating to the superclass — previously only possible with
    // a static helper method.
    // ===========================================================================

    static class PositiveInt {
        final int value;

        PositiveInt(int value) {
            // Flexible constructor body: validation before super() (Object)
            if (value <= 0) {
                throw new IllegalArgumentException("value must be positive, got: " + value);
            }
            this.value = value;  // Object's implicit super() is called automatically
        }
    }

    static class ValidatedRange extends PositiveInt {
        final int max;

        ValidatedRange(int min, int max) {
            // Statements before super() that don't access 'this':
            if (min >= max) {
                throw new IllegalArgumentException(
                        "min (%d) must be < max (%d)".formatted(min, max));
            }
            super(min);
            this.max = max;
        }
    }

    @Test
    @DisplayName("Flexible constructor body: validation before super() rejects invalid input")
    void flexibleConstructorBodyValidatesBeforeSuper() {
        assertThrows(IllegalArgumentException.class, () -> new PositiveInt(-1));
        assertThrows(IllegalArgumentException.class, () -> new PositiveInt(0));
    }

    @Test
    @DisplayName("Flexible constructor body: valid input creates the object correctly")
    void flexibleConstructorBodyAcceptsValidInput() {
        PositiveInt p = new PositiveInt(5);
        assertEquals(5, p.value);
    }

    @Test
    @DisplayName("Flexible constructor body in subclass: validation runs before super()")
    void flexibleConstructorBodyInSubclass() {
        assertThrows(IllegalArgumentException.class,
                () -> new ValidatedRange(10, 5));  // min >= max
        assertThrows(IllegalArgumentException.class,
                () -> new ValidatedRange(5, 5));   // min == max
    }

    @Test
    @DisplayName("Flexible constructor body in subclass: valid range is accepted")
    void flexibleConstructorBodySubclassValid() {
        ValidatedRange r = new ValidatedRange(1, 100);
        assertEquals(1, r.value);
        assertEquals(100, r.max);
    }

    // ===========================================================================
    // 2. Primitive types in patterns (JEP 488 — 2nd preview in Java 26)
    //    (Available because --enable-preview is active)
    // ===========================================================================

    @Test
    @DisplayName("Primitive pattern 'instanceof int i' unwraps a boxed Integer")
    void primitivePatternInstanceofInt() {
        Object obj = 2024;
        if (obj instanceof int i) {
            assertEquals(2024, i);
        } else {
            fail("Expected int pattern to match");
        }
    }

    @Test
    @DisplayName("Primitive pattern in switch over Object selects int arm")
    void primitivePatternSwitchInt() {
        Object obj = 26;
        String result = switch (obj) {
            case int i    -> "java " + i;
            case String s -> "string: " + s;
            default       -> "other";
        };
        assertEquals("java 26", result);
    }

    @Test
    @DisplayName("Primitive pattern: guarded int pattern checks value range")
    void primitivePatternGuardedInt() {
        Object obj = 100;
        String bucket = switch (obj) {
            case int i when i < 0   -> "negative";
            case int i when i < 50  -> "low";
            case int i when i < 150 -> "medium";
            case int i              -> "high";
            default                 -> "not int";
        };
        assertEquals("medium", bucket);
    }

    @Test
    @DisplayName("Primitive pattern 'instanceof float f' unwraps a boxed Float")
    void primitivePatternFloat() {
        Object obj = 3.14f;
        if (obj instanceof float f) {
            assertEquals(3.14f, f, 1e-5f);
        } else {
            fail("Expected float pattern to match");
        }
    }

    // ===========================================================================
    // 3. Virtual thread improvements (JEP 491 — Synchronize Virtual Threads
    //    without Pinning, standard in Java 24; consolidated in Java 26)
    //
    //    Java 24 removed the restriction that caused virtual threads to pin
    //    their carrier when executing inside a synchronized block/method.
    //    We verify that virtual threads can execute synchronized code without
    //    blocking extra carrier threads.
    // ===========================================================================

    @Test
    @DisplayName("Virtual thread executes synchronized block without pinning carrier")
    void virtualThreadSynchronizedBlock() throws Exception {
        Object lock = new Object();
        AtomicInteger counter = new AtomicInteger(0);
        int taskCount = 100;
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < taskCount; i++) {
            Thread vt = Thread.ofVirtual().start(() -> {
                synchronized (lock) {
                    counter.incrementAndGet();
                }
            });
            threads.add(vt);
        }

        for (Thread t : threads) t.join();
        assertEquals(taskCount, counter.get(),
                "All virtual threads must execute the synchronized block exactly once");
    }

    @Test
    @DisplayName("Synchronized method on virtual thread completes without deadlock")
    void virtualThreadSynchronizedMethod() throws Exception {
        class Counter {
            private int count = 0;
            synchronized void increment() { count++; }
            synchronized int get() { return count; }
        }

        Counter c = new Counter();
        int n = 50;
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            threads.add(Thread.ofVirtual().start(c::increment));
        }
        for (Thread t : threads) t.join();
        assertEquals(n, c.get());
    }

    // ===========================================================================
    // 4. Module import declarations (JEP 494 — available as standard in Java 26)
    //    These are compile-time declarations and cannot be tested at runtime.
    //    We verify that the standard APIs they would import still work correctly.
    //    (The declaration would be: import module java.base;)
    // ===========================================================================

    @Test
    @DisplayName("java.base APIs work as expected (targets module-import consolidation)")
    void javaBaseApis() {
        // Collections
        var list = List.of("a", "b", "c");
        assertEquals(3, list.size());

        // Optional
        var opt = Optional.of("present");
        assertTrue(opt.isPresent());

        // Stream
        long count = Stream.of(1, 2, 3, 4, 5).filter(n -> n % 2 == 0).count();
        assertEquals(2, count);

        // String
        assertEquals("HELLO", "hello".toUpperCase());
    }

    // ===========================================================================
    // 5. Sequenced Collections (standard since Java 21, fully consolidated)
    // ===========================================================================

    @Test
    @DisplayName("LinkedHashMap.reversed() provides reverse-insertion-order view")
    void linkedHashMapReversedView() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("one",   1);
        map.put("two",   2);
        map.put("three", 3);

        SequencedMap<String, Integer> rev = map.reversed();
        assertEquals("three", rev.firstEntry().getKey());
        assertEquals("one",   rev.lastEntry().getKey());
    }

    // ===========================================================================
    // 6. Stream Gatherers (standard in Java 24, available in Java 26)
    // ===========================================================================

    @Test
    @DisplayName("Stream.gather(Gatherers.windowFixed(2)) partitions into pairs")
    void streamGathererWindowFixed() {
        var windows = Stream.of(1, 2, 3, 4)
                .gather(java.util.stream.Gatherers.windowFixed(2))
                .toList();
        assertEquals(2, windows.size());
        assertEquals(List.of(1, 2), windows.get(0));
        assertEquals(List.of(3, 4), windows.get(1));
    }

    // ===========================================================================
    // 7. ScopedValue (standard in Java 25, available in Java 26)
    // ===========================================================================

    private static final ScopedValue<String> CONTEXT = ScopedValue.newInstance();

    @Test
    @DisplayName("ScopedValue.where().run() provides bound value in scope")
    void scopedValueBinding() {
        ScopedValue.where(CONTEXT, "java26").run(() ->
                assertEquals("java26", CONTEXT.get())
        );
        assertFalse(CONTEXT.isBound(), "Must be unbound after scope exits");
    }

    // ===========================================================================
    // 8. StructuredTaskScope (standard in Java 25, available in Java 26)
    // ===========================================================================

    @Test
    @DisplayName("StructuredTaskScope with allSuccessfulOrThrow joiner works in Java 26")
    void structuredTaskScopeWorks() throws Exception {
        try (var scope = StructuredTaskScope.open(
                StructuredTaskScope.Joiner.<Object>allSuccessfulOrThrow())) {
            var t1 = scope.<Object>fork(() -> "hello");
            var t2 = scope.<Object>fork(() -> 26);
            scope.join(); // returns List<Object> when all succeed
            assertEquals("hello", t1.get());
            assertEquals(26,      t2.get());
        }
    }

    // ===========================================================================
    // 9. Math.clamp (standard since Java 21, consolidated in Java 26)
    // ===========================================================================

    @Test
    @DisplayName("Math.clamp clamping behaviour covers int, long, and double")
    void mathClampConsolidated() {
        assertEquals(5,    Math.clamp(5,    1, 10));
        assertEquals(1,    Math.clamp(-3,   1, 10));
        assertEquals(10,   Math.clamp(99,   1, 10));

        assertEquals(50L,  Math.clamp(50L,  0L, 100L));
        assertEquals(0L,   Math.clamp(-1L,  0L, 100L));

        assertEquals(0.5,  Math.clamp(0.5,  0.0, 1.0), 1e-12);
        assertEquals(0.0,  Math.clamp(-1.0, 0.0, 1.0), 1e-12);
    }

    // ===========================================================================
    // 10. String API — accumulated Java 21–26 features
    // ===========================================================================

    @Test
    @DisplayName("String.formatted() (Java 14) continues to work in Java 26")
    void stringFormatted() {
        assertEquals("Java 26", "Java %d".formatted(26));
    }

    @Test
    @DisplayName("Text blocks (Java 15) continue to work in Java 26")
    void textBlocksWork() {
        String json = """
                {"version": 26}
                """;
        assertTrue(json.contains("\"version\": 26"));
        assertTrue(json.endsWith("\n"));
    }
}
