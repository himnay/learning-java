package com.org.java10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Java 10 – Collection Improvements")
class Java10CollectionsTest {

    @Test
    @DisplayName("List.copyOf returns unmodifiable copy independent of original")
    void listCopyOf_isIndependentAndUnmodifiable() {
        var original = new ArrayList<>(List.of("a", "b", "c"));
        var copy = List.copyOf(original);
        original.add("d");
        assertEquals(3, copy.size()); // copy is not affected
        assertThrows(UnsupportedOperationException.class, () -> copy.add("e"));
    }

    @Test
    @DisplayName("Set.copyOf returns unmodifiable copy")
    void setCopyOf_isUnmodifiable() {
        var original = new HashSet<>(Set.of("x", "y"));
        var copy = Set.copyOf(original);
        original.add("z");
        assertEquals(2, copy.size());
        assertThrows(UnsupportedOperationException.class, () -> copy.add("w"));
    }

    @Test
    @DisplayName("Map.copyOf returns unmodifiable copy")
    void mapCopyOf_isUnmodifiable() {
        var original = new HashMap<>(Map.of("k1", 1, "k2", 2));
        var copy = Map.copyOf(original);
        original.put("k3", 3);
        assertEquals(2, copy.size());
        assertThrows(UnsupportedOperationException.class, () -> copy.put("k4", 4));
    }

    @Test
    @DisplayName("Collectors.toUnmodifiableList collects to unmodifiable list")
    void toUnmodifiableList_isUnmodifiable() {
        var list = List.of(3, 1, 2).stream()
                .sorted()
                .collect(Collectors.toUnmodifiableList());
        assertEquals(List.of(1, 2, 3), list);
        assertThrows(UnsupportedOperationException.class, () -> list.add(4));
    }

    @Test
    @DisplayName("Collectors.toUnmodifiableSet collects to unmodifiable set")
    void toUnmodifiableSet_isUnmodifiable() {
        var set = List.of("a", "b", "a").stream()
                .collect(Collectors.toUnmodifiableSet());
        assertEquals(2, set.size());
        assertThrows(UnsupportedOperationException.class, () -> set.add("c"));
    }

    @Test
    @DisplayName("Collectors.toUnmodifiableMap collects to unmodifiable map")
    void toUnmodifiableMap_isUnmodifiable() {
        var map = List.of("one", "two", "three").stream()
                .collect(Collectors.toUnmodifiableMap(
                        s -> s, String::length));
        assertEquals(3, map.get("one"));
        assertEquals(5, map.get("three"));
        assertThrows(UnsupportedOperationException.class, () -> map.put("four", 4));
    }

    @Test
    @DisplayName("Optional.orElseThrow (no-arg) throws NoSuchElementException when empty")
    void optionalOrElseThrow_noArg() {
        assertThrows(NoSuchElementException.class,
                () -> Optional.<String>empty().orElseThrow());
    }

    @Test
    @DisplayName("Optional.orElseThrow (no-arg) returns value when present")
    void optionalOrElseThrow_returnsValueWhenPresent() {
        assertEquals("hello", Optional.of("hello").orElseThrow());
    }
}
