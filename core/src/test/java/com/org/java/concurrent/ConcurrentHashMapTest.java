package com.org.java.concurrent;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class ConcurrentHashMapTest {

    private ConcurrentHashMap<String, String> buildMap() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("foo", "bar");
        map.put("han", "solo");
        map.put("r2", "d2");
        map.put("c3", "p0");
        return map;
    }

    @Test
    @DisplayName("forEach() visits every entry in the map exactly once")
    void forEach_iteratesAllEntries() {
        ConcurrentHashMap<String, String> map = buildMap();
        AtomicInteger count = new AtomicInteger();
        map.forEach(1, (k, v) -> count.incrementAndGet());
        assertEquals(4, count.get());
    }

    @Test
    @DisplayName("mappingCount() returns the number of entries in the map")
    void mappingCount_returnsSize() {
        ConcurrentHashMap<String, String> map = buildMap();
        assertEquals(4, map.mappingCount());
    }

    @Test
    @DisplayName("search() returns a transformed value for the first entry matching the predicate")
    void search_findsMatchingEntry() {
        ConcurrentHashMap<String, String> map = buildMap();
        String result = map.search(1, (k, v) ->
                k.equals("foo") && v.equals("bar") ? "foobar" : null);
        assertEquals("foobar", result);
    }

    @Test
    @DisplayName("searchValues() returns the first value satisfying the given condition")
    void searchValues_findsLongValue() {
        ConcurrentHashMap<String, String> map = buildMap();
        // "solo" has length 4 > 3
        String result = map.searchValues(1, v -> v.length() > 3 ? v : null);
        assertEquals("solo", result);
    }

    @Test
    @DisplayName("reduce() combines all entries into a single aggregated string")
    void reduce_combinesAllEntries() {
        ConcurrentHashMap<String, String> map = buildMap();
        String reduced = map.reduce(1,
                (k, v) -> k + "=" + v,
                (s1, s2) -> s1 + ", " + s2);
        assertNotNull(reduced);
        assertTrue(reduced.contains("foo=bar"));
        assertTrue(reduced.contains("han=solo"));
    }

    @Test
    @DisplayName("putIfAbsent() keeps the existing value when the key is already present")
    void putIfAbsent_doesNotOverwriteExisting() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "original");
        map.putIfAbsent("key", "overwrite");
        assertEquals("original", map.get("key"));
    }
}
