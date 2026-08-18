package com.org.java.misc;

import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class ConcurrencyTest {

    private ConcurrentHashMap<Integer, UUID> buildMap() {
        ConcurrentHashMap<Integer, UUID> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 100; i++) {
            map.put(i, UUID.randomUUID());
        }
        return map;
    }

    @Test
    void forEachValue_iteratesAllValues() {
        ConcurrentHashMap<Integer, UUID> map = buildMap();
        AtomicInteger count = new AtomicInteger(0);
        map.forEachValue(1, uuid -> count.incrementAndGet());
        assertEquals(100, count.get());
    }

    @Test
    void forEach_withCondition_processesMatchingEntries() {
        ConcurrentHashMap<Integer, UUID> map = buildMap();
        int[] count = {0};
        map.forEach((id, uuid) -> {
            if (id % 10 == 0) count[0]++;
        });
        assertEquals(10, count[0]); // keys 0,10,20,...,90
    }

    @Test
    void search_returnsFirstMatchOrNull() {
        ConcurrentHashMap<Integer, UUID> map = new ConcurrentHashMap<>();
        map.put(1, UUID.fromString("00000000-0000-0000-0000-000000000001"));
        map.put(2, UUID.fromString("00000000-0000-0000-0000-000000000002"));

        UUID found = map.search(1, (id, uuid) ->
                uuid.toString().endsWith("0001") ? uuid : null);
        assertNotNull(found);
        assertTrue(found.toString().endsWith("0001"));
    }

    @Test
    void search_returnsNullWhenNoMatch() {
        ConcurrentHashMap<Integer, UUID> map = buildMap();
        UUID result = map.search(1, (id, uuid) -> null);
        assertNull(result);
    }
}
