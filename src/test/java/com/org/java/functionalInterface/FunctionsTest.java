package com.org.java.functionalInterface;

import com.org.java.Student;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.org.java.StudentDataBase.getAllStudents;
import static org.junit.jupiter.api.Assertions.*;

class FunctionsTest {

    @Test
    void apply_uppercasesString() {
        Function<String, String> upper = String::toUpperCase;
        assertEquals("HIMANSU", upper.apply("himansu"));
    }

    @Test
    void andThen_appliesFirstThenSecond() {
        Function<String, String> upper  = String::toUpperCase;
        Function<String, String> concat = name -> name.concat("Nayak");
        // upper first → "HIMANSU", then concat → "HIMANSUNayak"
        assertEquals("HIMANSUNayak", upper.andThen(concat).apply("Himansu"));
    }

    @Test
    void compose_appliesSecondThenFirst() {
        Function<String, String> upper  = String::toUpperCase;
        Function<String, String> concat = name -> name.concat("Nayak");
        // compose: concat("Himansu") → "HimansuNayak", then upper → "HIMANSUNAYAK"
        assertEquals("HIMANSUNAYAK", upper.compose(concat).apply("Himansu"));
    }

    @Test
    void functionWithStudents_buildsNameGpaMap() {
        Function<java.util.List<Student>, Map<String, Double>> toGpaMap = students -> {
            Map<String, Double> map = new HashMap<>();
            students.forEach(s -> map.put(s.getName(), s.getGpa()));
            return map;
        };
        Map<String, Double> result = toGpaMap.apply(getAllStudents());
        assertEquals(4.0, result.get("Emily"));
        assertEquals(3.5, result.get("Sophia"));
    }
}
