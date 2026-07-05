package com.org.java.misc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MapsTest {

    private Map<Integer, String> buildMap() {
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.putIfAbsent(i, "val" + i);
        }
        return map;
    }

    @Test
    @DisplayName("putIfAbsent() keeps the existing value when the key is already present")
    void putIfAbsent_doesNotOverwriteExistingEntry() {
        Map<Integer, String> map = buildMap();
        map.putIfAbsent(3, "overwrite");
        assertEquals("val3", map.get(3));
    }

    @Test
    @DisplayName("computeIfPresent() transforms the value of an existing key")
    void computeIfPresent_transformsExistingValue() {
        Map<Integer, String> map = buildMap();
        map.computeIfPresent(3, (k, v) -> v + k);
        assertEquals("val33", map.get(3));
    }

    @Test
    @DisplayName("computeIfPresent() removes the entry when the remapping function returns null")
    void computeIfPresent_removesEntryWhenFunctionReturnsNull() {
        Map<Integer, String> map = buildMap();
        map.computeIfPresent(9, (k, v) -> null);
        assertFalse(map.containsKey(9));
    }

    @Test
    @DisplayName("computeIfAbsent() adds a new entry when the key is not already present")
    void computeIfAbsent_addsNewEntry() {
        Map<Integer, String> map = buildMap();
        map.computeIfAbsent(23, k -> "val" + k);
        assertTrue(map.containsKey(23));
        assertEquals("val23", map.get(23));
    }

    @Test
    @DisplayName("computeIfAbsent() does not overwrite a value already present for the key")
    void computeIfAbsent_doesNotOverwriteExistingEntry() {
        Map<Integer, String> map = buildMap();
        map.computeIfAbsent(3, k -> "bam");
        assertEquals("val3", map.get(3));
    }

    @Test
    @DisplayName("getOrDefault() returns the fallback value for a key that is not present")
    void getOrDefault_returnsFallbackForMissingKey() {
        Map<Integer, String> map = buildMap();
        assertEquals("not found", map.getOrDefault(42, "not found"));
        assertEquals("val0", map.getOrDefault(0, "not found"));
    }

    @Test
    @DisplayName("remove(key, value) only removes the entry when the supplied value matches the current one")
    void remove_conditionalOnValue_onlyRemovesWhenValueMatches() {
        Map<Integer, String> map = buildMap();
        // transform val3 → val33 first
        map.computeIfPresent(3, (k, v) -> v + k); // "val3" → "val33"
        assertEquals("val33", map.get(3));

        map.remove(3, "val3");   // wrong value → NOT removed
        assertEquals("val33", map.get(3));

        map.remove(3, "val33"); // correct value → removed
        assertNull(map.get(3));
    }

    @Test
    @DisplayName("merge() inserts the given value directly when the key is absent")
    void merge_insertsWhenKeyAbsent() {
        Map<Integer, String> map = buildMap();
        map.remove(9); // ensure key 9 is absent
        map.merge(9, "val9", String::concat);
        assertEquals("val9", map.get(9));
    }

    @Test
    @DisplayName("merge() combines the existing value with the new one using the remapping function")
    void merge_appliesFunctionWhenKeyPresent() {
        Map<Integer, String> map = buildMap();
        map.merge(9, "concat", String::concat);
        assertEquals("val9concat", map.get(9));
    }
}
