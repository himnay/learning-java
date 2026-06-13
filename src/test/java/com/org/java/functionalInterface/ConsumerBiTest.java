package com.org.java.functionalInterface;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import static com.org.java.StudentDataBase.getAllStudents;
import static org.junit.jupiter.api.Assertions.*;

class ConsumerBiTest {

    @Test
    void biConsumer_acceptsTwoArguments() {
        List<String> captured = new ArrayList<>();
        BiConsumer<String, Integer> biConsumer = (a, b) -> captured.add(a + " " + b);
        biConsumer.accept("himansu", 1);
        assertEquals("himansu 1", captured.get(0));
    }

    @Test
    void andThen_chainsMultiplication_thenDivision() {
        List<Integer> results = new ArrayList<>();
        BiConsumer<Integer, Integer> mult = (a, b) -> results.add(a * b);
        BiConsumer<Integer, Integer> div  = (a, b) -> results.add(a / b);
        mult.andThen(div).accept(10, 5);
        assertEquals(50, results.get(0)); // 10*5
        assertEquals(2,  results.get(1)); // 10/5
    }

    @Test
    void biConsumerWithList_iteratesStudentActivities() {
        List<String> seen = new ArrayList<>();
        BiConsumer<String, List<String>> logActivities =
                (name, activities) -> seen.add(name + ":" + activities.size());
        getAllStudents().forEach(s -> logActivities.accept(s.getName(), s.getActivities()));
        assertEquals(7, seen.size());
        assertTrue(seen.stream().anyMatch(e -> e.startsWith("Emily")));
    }
}
