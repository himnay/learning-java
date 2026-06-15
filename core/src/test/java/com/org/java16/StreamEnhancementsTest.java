// FILE 6: src/test/java/com/org/java16/StreamEnhancementsTest.java
package com.org.java16;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Stream Enhancements (Java 16)")
class StreamEnhancementsTest {

    // ---------------------------------------------------------------------------
    // Stream.toList() — Java 16, returns an unmodifiable List
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Stream.toList() collects elements into a List in encounter order")
    void toListPreservesOrder() {
        List<Integer> result = Stream.of(3, 1, 4, 1, 5, 9).toList();
        assertEquals(List.of(3, 1, 4, 1, 5, 9), result);
    }

    @Test
    @DisplayName("Stream.toList() returns an unmodifiable List")
    void toListIsUnmodifiable() {
        List<String> result = Stream.of("a", "b", "c").toList();
        assertThrows(UnsupportedOperationException.class, () -> result.add("d"));
    }

    @Test
    @DisplayName("Stream.toList() on empty stream returns empty list")
    void toListEmptyStream() {
        List<String> result = Stream.<String>empty().toList();
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Stream.toList() allows null elements")
    void toListAllowsNulls() {
        List<String> result = Stream.of("a", null, "c").toList();
        assertEquals(3, result.size());
        assertNull(result.get(1));
    }

    @Test
    @DisplayName("Stream.toList() preserves duplicates")
    void toListPreservesDuplicates() {
        List<String> result = Stream.of("x", "x", "y").toList();
        assertEquals(3, result.size());
        assertEquals("x", result.get(0));
        assertEquals("x", result.get(1));
        assertEquals("y", result.get(2));
    }

    @Test
    @DisplayName("Stream.toList() after filter contains only matching elements")
    void toListAfterFilter() {
        List<Integer> evens = Stream.of(1, 2, 3, 4, 5, 6)
                .filter(n -> n % 2 == 0)
                .toList();
        assertEquals(List.of(2, 4, 6), evens);
    }

    @Test
    @DisplayName("Stream.toList() after map transforms elements correctly")
    void toListAfterMap() {
        List<String> result = Stream.of(1, 2, 3)
                .map(n -> "item-" + n)
                .toList();
        assertEquals(List.of("item-1", "item-2", "item-3"), result);
    }

    // ---------------------------------------------------------------------------
    // Stream.mapMulti() — Java 16
    // mapMulti is a flexible alternative to flatMap
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("mapMulti flattens a list-valued mapping without intermediate streams")
    void mapMultiFlattensList() {
        List<Integer> result = Stream.of(List.of(1, 2), List.of(3, 4), List.of(5))
                .<Integer>mapMulti((list, consumer) -> list.forEach(consumer))
                .toList();
        assertEquals(List.of(1, 2, 3, 4, 5), result);
    }

    @Test
    @DisplayName("mapMulti can emit zero elements (acts as filter)")
    void mapMultiEmitsZeroElements() {
        List<Integer> odds = Stream.of(1, 2, 3, 4, 5)
                .<Integer>mapMulti((n, consumer) -> {
                    if (n % 2 != 0) consumer.accept(n);
                })
                .toList();
        assertEquals(List.of(1, 3, 5), odds);
    }

    @Test
    @DisplayName("mapMulti can emit more than one element per input (expansion)")
    void mapMultiExpandsElements() {
        List<String> result = Stream.of("a", "b")
                .<String>mapMulti((s, consumer) -> {
                    consumer.accept(s.toUpperCase());
                    consumer.accept(s.toLowerCase());
                    consumer.accept(s + "!");
                })
                .toList();
        assertEquals(List.of("A", "a", "a!", "B", "b", "b!"), result);
    }

    @Test
    @DisplayName("mapMulti with type narrowing filters only instances of target type")
    void mapMultiTypeNarrowing() {
        List<Object> mixed = List.of(1, "two", 3, "four", 5);
        List<String> strings = mixed.stream()
                .<String>mapMulti((obj, consumer) -> {
                    if (obj instanceof String s) consumer.accept(s);
                })
                .toList();
        assertEquals(List.of("two", "four"), strings);
    }

    @Test
    @DisplayName("mapMulti on empty stream produces empty list")
    void mapMultiOnEmptyStream() {
        List<Integer> result = Stream.<Integer>empty()
                .<Integer>mapMulti((n, consumer) -> consumer.accept(n * 2))
                .toList();
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("mapMulti and flatMap produce identical results for simple list expansion")
    void mapMultiEquivalentToFlatMap() {
        List<List<Integer>> input = List.of(List.of(1, 2), List.of(3, 4));

        List<Integer> viaFlatMap = input.stream()
                .flatMap(List::stream)
                .toList();

        List<Integer> viaMapMulti = input.stream()
                .<Integer>mapMulti((list, consumer) -> list.forEach(consumer))
                .toList();

        assertEquals(viaFlatMap, viaMapMulti);
    }

    @Test
    @DisplayName("mapMultiToInt converts objects to int primitives efficiently")
    void mapMultiToInt() {
        int[] result = Stream.of("1", "2", "3")
                .mapMultiToInt((s, consumer) -> consumer.accept(Integer.parseInt(s)))
                .toArray();
        assertArrayEquals(new int[]{1, 2, 3}, result);
    }
}
