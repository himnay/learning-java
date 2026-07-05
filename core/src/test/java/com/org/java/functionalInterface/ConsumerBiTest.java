package com.org.java.functionalInterface;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import static com.org.java.StudentDataBase.getAllStudents;
import static org.junit.jupiter.api.Assertions.*;

class ConsumerBiTest {

    @Test
    @DisplayName("A BiConsumer accepts and combines two arguments into one captured value")
    void biConsumer_acceptsTwoArguments() {
        List<String> captured = new ArrayList<>();
        BiConsumer<String, Integer> biConsumer = (a, b) -> captured.add(a + " " + b);
        biConsumer.accept("himansu", 1);
        assertEquals("himansu 1", captured.get(0));
    }

    @Test
    @DisplayName("andThen() runs a multiplication BiConsumer followed by a division BiConsumer in order")
    void andThen_chainsMultiplication_thenDivision() {
        List<Integer> results = new ArrayList<>();
        BiConsumer<Integer, Integer> mult = (a, b) -> results.add(a * b);
        BiConsumer<Integer, Integer> div  = (a, b) -> results.add(a / b);
        mult.andThen(div).accept(10, 5);
        assertEquals(50, results.get(0)); // 10*5
        assertEquals(2,  results.get(1)); // 10/5
    }

    @Test
    @DisplayName("A BiConsumer applied to each student logs the name and activity count for every student")
    void biConsumerWithList_iteratesStudentActivities() {
        List<String> seen = new ArrayList<>();
        BiConsumer<String, List<String>> logActivities =
                (name, activities) -> seen.add(name + ":" + activities.size());
        getAllStudents().forEach(s -> logActivities.accept(s.getName(), s.getActivities()));
        assertEquals(7, seen.size());
        assertTrue(seen.stream().anyMatch(e -> e.startsWith("Emily")));
    }
}
