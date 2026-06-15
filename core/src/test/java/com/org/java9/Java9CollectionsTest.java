package com.org.java9;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Java 9 – Collection Factory Methods")
class Java9CollectionsTest {

    @Test
    @DisplayName("List.of creates an unmodifiable list")
    void listOf_isUnmodifiable() {
        List<String> list = List.of("a", "b", "c");
        assertEquals(3, list.size());
        assertEquals("b", list.get(1));
        assertThrows(UnsupportedOperationException.class, () -> list.add("d"));
    }

    @Test
    @DisplayName("List.of preserves order and allows null-free check")
    void listOf_preservesOrder() {
        List<Integer> list = List.of(1, 2, 3, 4, 5);
        assertEquals(List.of(1, 2, 3, 4, 5), list);
        assertThrows(NullPointerException.class, () -> List.of(1, null, 3));
    }

    @Test
    @DisplayName("Set.of creates an unmodifiable set with no duplicates")
    void setOf_isUnmodifiableAndNoDuplicates() {
        Set<String> set = Set.of("x", "y", "z");
        assertEquals(3, set.size());
        assertTrue(set.contains("x"));
        assertThrows(UnsupportedOperationException.class, () -> set.add("w"));
        assertThrows(IllegalArgumentException.class, () -> Set.of("dup", "dup"));
    }

    @Test
    @DisplayName("Map.of creates an unmodifiable map from alternating key-value pairs")
    void mapOf_isUnmodifiable() {
        Map<String, Integer> map = Map.of("one", 1, "two", 2, "three", 3);
        assertEquals(3, map.size());
        assertEquals(1, map.get("one"));
        assertEquals(2, map.get("two"));
        assertThrows(UnsupportedOperationException.class, () -> map.put("four", 4));
    }

    @Test
    @DisplayName("Map.ofEntries allows more than 10 entries")
    void mapOfEntries_supportsMoreThanTenEntries() {
        Map<String, Integer> map = Map.ofEntries(
                Map.entry("a", 1), Map.entry("b", 2), Map.entry("c", 3),
                Map.entry("d", 4), Map.entry("e", 5), Map.entry("f", 6),
                Map.entry("g", 7), Map.entry("h", 8), Map.entry("i", 9),
                Map.entry("j", 10), Map.entry("k", 11)
        );
        assertEquals(11, map.size());
        assertEquals(11, map.get("k"));
    }

    @Test
    @DisplayName("Map.entry creates an immutable entry")
    void mapEntry_isImmutable() {
        Map.Entry<String, Integer> entry = Map.entry("key", 42);
        assertEquals("key", entry.getKey());
        assertEquals(42, entry.getValue());
        assertThrows(UnsupportedOperationException.class, () -> entry.setValue(0));
    }

    @Test
    @DisplayName("List.copyOf produces an unmodifiable copy")
    void listCopyOf_isUnmodifiable() {
        List<String> original = new ArrayList<>(List.of("a", "b", "c"));
        List<String> copy = List.copyOf(original);
        original.add("d");
        assertEquals(3, copy.size()); // copy is independent
        assertThrows(UnsupportedOperationException.class, () -> copy.add("e"));
    }
}
