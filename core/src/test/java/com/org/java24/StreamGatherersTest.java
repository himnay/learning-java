// FILE 16: src/test/java/com/org/java24/StreamGatherersTest.java
package com.org.java24;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Gatherer;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Stream.gather() and Gatherers API (Java 24)")
class StreamGatherersTest {

    // ---------------------------------------------------------------------------
    // Gatherers.windowFixed()
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("windowFixed(2) partitions stream into windows of exactly 2 elements")
    void windowFixedBasic() {
        List<List<Integer>> windows = Stream.of(1, 2, 3, 4, 5, 6)
                .gather(Gatherers.windowFixed(2))
                .toList();
        assertEquals(3, windows.size());
        assertEquals(List.of(1, 2), windows.get(0));
        assertEquals(List.of(3, 4), windows.get(1));
        assertEquals(List.of(5, 6), windows.get(2));
    }

    @Test
    @DisplayName("windowFixed(3) produces a trailing partial window for non-divisible sizes")
    void windowFixedPartialWindow() {
        List<List<Integer>> windows = Stream.of(1, 2, 3, 4, 5)
                .gather(Gatherers.windowFixed(3))
                .toList();
        assertEquals(2, windows.size());
        assertEquals(List.of(1, 2, 3), windows.get(0));
        assertEquals(List.of(4, 5), windows.get(1)); // trailing partial window
    }

    @Test
    @DisplayName("windowFixed(1) gives one-element windows — same as identity")
    void windowFixedSizeOne() {
        List<List<String>> windows = Stream.of("a", "b", "c")
                .gather(Gatherers.windowFixed(1))
                .toList();
        assertEquals(3, windows.size());
        assertEquals(List.of("a"), windows.get(0));
        assertEquals(List.of("b"), windows.get(1));
        assertEquals(List.of("c"), windows.get(2));
    }

    @Test
    @DisplayName("windowFixed on empty stream produces empty list")
    void windowFixedEmptyStream() {
        List<List<Integer>> windows = Stream.<Integer>empty()
                .gather(Gatherers.windowFixed(3))
                .toList();
        assertTrue(windows.isEmpty());
    }

    // ---------------------------------------------------------------------------
    // Gatherers.windowSliding()
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("windowSliding(3) produces overlapping windows of size 3")
    void windowSliding() {
        List<List<Integer>> windows = Stream.of(1, 2, 3, 4, 5)
                .gather(Gatherers.windowSliding(3))
                .toList();
        assertEquals(3, windows.size());
        assertEquals(List.of(1, 2, 3), windows.get(0));
        assertEquals(List.of(2, 3, 4), windows.get(1));
        assertEquals(List.of(3, 4, 5), windows.get(2));
    }

    // ---------------------------------------------------------------------------
    // Gatherers.scan() — running accumulation (like reduce but emits each step)
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("scan() produces a running sum (prefix sums)")
    void scanRunningSum() {
        List<Integer> prefixSums = Stream.of(1, 2, 3, 4, 5)
                .gather(Gatherers.scan(() -> 0, Integer::sum))
                .toList();
        assertEquals(List.of(1, 3, 6, 10, 15), prefixSums);
    }

    @Test
    @DisplayName("scan() produces a running maximum")
    void scanRunningMax() {
        List<Integer> runningMax = Stream.of(3, 1, 4, 1, 5, 9, 2, 6)
                .gather(Gatherers.scan(() -> Integer.MIN_VALUE, Math::max))
                .toList();
        assertEquals(List.of(3, 3, 4, 4, 5, 9, 9, 9), runningMax);
    }

    @Test
    @DisplayName("scan() on single-element stream returns that element")
    void scanSingleElement() {
        List<Integer> result = Stream.of(42)
                .gather(Gatherers.scan(() -> 0, Integer::sum))
                .toList();
        assertEquals(List.of(42), result);
    }

    @Test
    @DisplayName("scan() on empty stream returns empty list")
    void scanEmptyStream() {
        List<Integer> result = Stream.<Integer>empty()
                .gather(Gatherers.scan(() -> 0, Integer::sum))
                .toList();
        assertTrue(result.isEmpty());
    }

    // ---------------------------------------------------------------------------
    // Gatherers.fold() — terminal fold returning a single Optional result
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("fold() computes the sum of all elements")
    void foldSum() {
        Optional<Integer> result = Stream.of(1, 2, 3, 4, 5)
                .gather(Gatherers.fold(() -> 0, Integer::sum))
                .findFirst();
        assertTrue(result.isPresent());
        assertEquals(15, result.get());
    }

    @Test
    @DisplayName("fold() concatenates strings")
    void foldStringConcat() {
        Optional<String> result = Stream.of("a", "b", "c", "d")
                .gather(Gatherers.fold(() -> "", (acc, s) -> acc + s))
                .findFirst();
        assertTrue(result.isPresent());
        assertEquals("abcd", result.get());
    }

    @Test
    @DisplayName("fold() on empty stream returns the initial value")
    void foldEmptyStream() {
        Optional<Integer> result = Stream.<Integer>empty()
                .gather(Gatherers.fold(() -> 0, Integer::sum))
                .findFirst();
        assertTrue(result.isPresent());
        assertEquals(0, result.get());
    }

    // ---------------------------------------------------------------------------
    // Gatherers.mapConcurrent()
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("mapConcurrent(4, fn) applies the mapping to all elements")
    void mapConcurrent() {
        List<Integer> result = Stream.of(1, 2, 3, 4, 5)
                .gather(Gatherers.mapConcurrent(4, n -> n * n))
                .sorted()
                .toList();
        assertEquals(List.of(1, 4, 9, 16, 25), result);
    }

    @Test
    @DisplayName("mapConcurrent produces the same count as the input stream")
    void mapConcurrentPreservesCount() {
        long count = Stream.of("a", "b", "c", "d", "e", "f")
                .gather(Gatherers.mapConcurrent(2, s -> s.toUpperCase()))
                .count();
        assertEquals(6, count);
    }

    @Test
    @DisplayName("mapConcurrent with concurrency 1 behaves like sequential map")
    void mapConcurrentSequentialEquivalent() {
        List<String> concurrent = Stream.of("x", "y", "z")
                .gather(Gatherers.mapConcurrent(1, String::toUpperCase))
                .toList();
        List<String> sequential = Stream.of("x", "y", "z")
                .map(String::toUpperCase)
                .toList();
        assertEquals(sequential, concurrent);
    }

    // ---------------------------------------------------------------------------
    // Custom Gatherer
    // ---------------------------------------------------------------------------

    /**
     * A custom Gatherer that emits elements only when they are strictly greater
     * than all previously seen elements (running filter for new maximums).
     *
     * State is a one-element Object array: state[0] holds the current maximum (or null).
     */
    @SuppressWarnings("unchecked")
    static <T extends Comparable<T>> Gatherer<T, Object[], T> newMaximaGatherer() {
        return Gatherer.ofSequential(
                () -> new Object[1],                            // initializer: [null]
                (state, element, downstream) -> {               // integrator
                    T current = (T) state[0];
                    if (current == null || element.compareTo(current) > 0) {
                        state[0] = element;
                        return downstream.push(element);
                    }
                    return true; // keep going, but don't emit
                }
        );
    }

    @Test
    @DisplayName("Custom Gatherer: emits only new running maxima from the stream")
    void customGathererRunningMaxima() {
        List<Integer> maxima = Stream.of(1, 3, 2, 5, 4, 7, 6)
                .<Integer>gather(newMaximaGatherer())
                .toList();
        assertEquals(List.of(1, 3, 5, 7), maxima);
    }

    @Test
    @DisplayName("Custom Gatherer: monotonically increasing input produces all elements")
    void customGathererAllIncreasing() {
        List<Integer> maxima = Stream.of(1, 2, 3, 4, 5)
                .<Integer>gather(newMaximaGatherer())
                .toList();
        assertEquals(List.of(1, 2, 3, 4, 5), maxima);
    }

    @Test
    @DisplayName("Custom Gatherer: decreasing input emits only first element")
    void customGathererAllDecreasing() {
        List<Integer> maxima = Stream.of(9, 8, 7, 6, 5)
                .<Integer>gather(newMaximaGatherer())
                .toList();
        assertEquals(List.of(9), maxima);
    }

    // ---------------------------------------------------------------------------
    // Chaining gatherers
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Chaining windowFixed and map produces windowed aggregations")
    void chainWindowedSum() {
        // Window of 2, then sum each window
        List<Integer> windowSums = Stream.of(1, 2, 3, 4, 5, 6)
                .gather(Gatherers.windowFixed(2))
                .map(w -> w.stream().mapToInt(Integer::intValue).sum())
                .toList();
        assertEquals(List.of(3, 7, 11), windowSums);
    }

    @Test
    @DisplayName("scan() followed by filter produces only increments above a threshold")
    void scanThenFilter() {
        List<Integer> result = Stream.of(1, 2, 3, 4, 5)
                .gather(Gatherers.scan(() -> 0, Integer::sum))
                .filter(sum -> sum > 5)
                .toList();
        assertEquals(List.of(6, 10, 15), result);
    }
}
