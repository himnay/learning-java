package com.org.java.functionalInterface;

import com.org.java.Student;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

import static com.org.java.StudentDataBase.getAllStudents;
import static com.org.java.functionalInterface.PredicatesBiTest.biPredicate;
import static org.junit.jupiter.api.Assertions.*;

class FunctionBiTest {

    private final BiFunction<List<Student>, BiPredicate<Integer, Double>, Map<String, Double>> filteredGpaMap =
            (students, predicate) -> {
                Map<String, Double> result = new HashMap<>();
                students.forEach(s -> {
                    if (predicate.test(s.getGradeLevel(), s.getGpa())) {
                        result.put(s.getName(), s.getGpa());
                    }
                });
                return result;
            };

    @Test
    void apply_returnsOnlyStudentsMeetingBiPredicate() {
        // biPredicate: gradeLevel >= 3 AND gpa >= 3.9
        Map<String, Double> result = filteredGpaMap.apply(getAllStudents(), biPredicate);
        assertEquals(3, result.size());
        assertTrue(result.containsKey("Emily"));
        assertTrue(result.containsKey("Dave"));
        assertTrue(result.containsKey("James"));
        assertEquals(4.0, result.get("Emily"));
    }

    @Test
    void apply_withRelaxedPredicate_returnsMoreStudents() {
        BiPredicate<Integer, Double> relaxed = (grade, gpa) -> gpa >= 3.5;
        Map<String, Double> result = filteredGpaMap.apply(getAllStudents(), relaxed);
        // All 7 students have gpa >= 3.5, but Adam appears twice so map has 6 entries
        assertEquals(6, result.size());
    }
}
