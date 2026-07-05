package com.org.java.stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StreamFactoryTest {

    @Test
    @DisplayName("Stream.of() creates a stream containing exactly the provided elements in order")
    void streamOf_containsAllProvidedElements() {
        List<String> result = Stream.of("Alpha", "Beta", "Gamma", "Theta")
                .collect(Collectors.toList());
        assertEquals(4, result.size());
        assertEquals("Alpha", result.get(0));
        assertEquals("Theta", result.get(3));
    }

    @Test
    @DisplayName("Stream.iterate() generates a doubling sequence starting from the seed value")
    void streamIterate_producesDoublingSequence() {
        List<Integer> result = Stream.iterate(1, x -> x * 2)
                .limit(10)
                .collect(Collectors.toList());
        assertEquals(10, result.size());
        assertEquals(1,   result.get(0));
        assertEquals(2,   result.get(1));
        assertEquals(4,   result.get(2));
        assertEquals(512, result.get(9));
    }

    @Test
    @DisplayName("Stream.generate() produces the requested number of elements from the supplier")
    void streamGenerate_producesRequestedNumberOfElements() {
        List<Integer> result = Stream.generate(() -> 42)
                .limit(5)
                .collect(Collectors.toList());
        assertEquals(5, result.size());
        assertTrue(result.stream().allMatch(n -> n == 42));
    }
}
