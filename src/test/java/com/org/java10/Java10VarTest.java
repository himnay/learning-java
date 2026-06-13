package com.org.java10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Java 10 – Local Variable Type Inference (var)")
class Java10VarTest {

    @Test
    @DisplayName("var infers String type from string literal")
    void var_infersPrimitiveAndStringTypes() {
        var message = "Hello, Java 10";
        var count   = 42;
        var pi      = 3.14;
        assertEquals("Hello, Java 10", message);
        assertEquals(42,   count);
        assertEquals(3.14, pi);
    }

    @Test
    @DisplayName("var infers List type from factory method")
    void var_infersList() {
        var list = new ArrayList<String>();
        list.add("alpha");
        list.add("beta");
        assertEquals(2, list.size());
        assertEquals("alpha", list.get(0));
    }

    @Test
    @DisplayName("var in for-each loop infers element type")
    void var_inForEachLoop() {
        var numbers = List.of(1, 2, 3, 4, 5);
        var sum = 0;
        for (var n : numbers) {
            sum += n;
        }
        assertEquals(15, sum);
    }

    @Test
    @DisplayName("var in traditional for loop")
    void var_inTraditionalForLoop() {
        var result = new StringBuilder();
        for (var i = 0; i < 3; i++) {
            result.append(i);
        }
        assertEquals("012", result.toString());
    }

    @Test
    @DisplayName("var infers Map type")
    void var_infersMap() {
        var map = new HashMap<String, Integer>();
        map.put("a", 1);
        map.put("b", 2);
        var keys = map.keySet();
        assertEquals(2, keys.size());
    }

    @Test
    @DisplayName("var with stream pipeline")
    void var_withStreamPipeline() {
        var names  = List.of("Alice", "Bob", "Charlie");
        var upper  = names.stream()
                          .map(String::toUpperCase)
                          .collect(Collectors.toList());
        assertEquals("ALICE", upper.get(0));
        assertEquals("CHARLIE", upper.get(2));
    }

    @Test
    @DisplayName("var infers anonymous class type (local scope only)")
    void var_infersAnonymousClass() {
        var runner = new Runnable() {
            String message = "run";
            @Override public void run() {}
        };
        runner.run();
        assertEquals("run", runner.message); // var captures the exact anonymous type
    }
}
