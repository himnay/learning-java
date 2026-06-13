package com.org.java9;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Java 9 – Stream Enhancements")
class Java9StreamTest {

    @Test
    @DisplayName("takeWhile stops at first non-matching element")
    void takeWhile_stopsAtFirstNonMatch() {
        List<Integer> result = Stream.of(1, 2, 3, 4, 5, 1, 2)
                .takeWhile(n -> n < 4)
                .collect(Collectors.toList());
        assertEquals(List.of(1, 2, 3), result);
    }

    @Test
    @DisplayName("dropWhile skips leading elements that match predicate")
    void dropWhile_skipsLeadingMatches() {
        List<Integer> result = Stream.of(1, 2, 3, 4, 5)
                .dropWhile(n -> n < 4)
                .collect(Collectors.toList());
        assertEquals(List.of(4, 5), result);
    }

    @Test
    @DisplayName("Stream.iterate with predicate acts like a for-loop")
    void iterate_withPredicateTerminates() {
        List<Integer> result = Stream.iterate(0, n -> n < 10, n -> n + 2)
                .collect(Collectors.toList());
        assertEquals(List.of(0, 2, 4, 6, 8), result);
    }

    @Test
    @DisplayName("Stream.ofNullable returns empty stream for null")
    void ofNullable_emptyForNull() {
        long nullCount = Stream.ofNullable(null).count();
        assertEquals(0, nullCount);
    }

    @Test
    @DisplayName("Stream.ofNullable wraps non-null in a single-element stream")
    void ofNullable_singleElementForNonNull() {
        List<String> result = Stream.ofNullable("hello").collect(Collectors.toList());
        assertEquals(List.of("hello"), result);
    }

    @Test
    @DisplayName("takeWhile on empty stream returns empty")
    void takeWhile_emptyStream() {
        List<Integer> result = Stream.<Integer>empty()
                .takeWhile(n -> n > 0)
                .collect(Collectors.toList());
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("dropWhile drops nothing when predicate never matches")
    void dropWhile_dropsNothingWhenPredicateFails() {
        List<Integer> result = Stream.of(10, 20, 30)
                .dropWhile(n -> n < 5)
                .collect(Collectors.toList());
        assertEquals(List.of(10, 20, 30), result);
    }
}
