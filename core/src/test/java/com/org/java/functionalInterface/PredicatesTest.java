package com.org.java.functionalInterface;

import com.org.java.Student;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.org.java.StudentDataBase.getAllStudents;
import static org.junit.jupiter.api.Assertions.*;

class PredicatesTest {

    private final Predicate<Integer> isEven    = a -> a % 2 == 0;
    private final Predicate<Integer> isDivBy5  = a -> a % 5 == 0;

    @Test
    void test_evenNumber() {
        assertTrue(isEven.test(2));
        assertFalse(isEven.test(3));
    }

    @Test
    void and_bothPredicatesMustBeTrue() {
        assertTrue(isEven.and(isDivBy5).test(20));  // 20: even ✓ and div5 ✓
        assertTrue(isEven.and(isDivBy5).test(10));  // 10: even ✓ and div5 ✓
        assertFalse(isEven.and(isDivBy5).test(5));  // 5: div5 ✓ but not even ✗
        assertFalse(isEven.and(isDivBy5).test(4));  // 4: even ✓ but not div5 ✗
    }

    @Test
    void or_eitherPredicateSuffices() {
        assertTrue(isEven.or(isDivBy5).test(5));   // divisible by 5
        assertTrue(isEven.or(isDivBy5).test(4));   // even
        assertFalse(isEven.or(isDivBy5).test(3));  // neither
    }

    @Test
    void negate_invertsResult() {
        assertFalse(isEven.negate().test(2));
        assertTrue(isEven.negate().test(3));
        assertFalse(isEven.and(isDivBy5).negate().test(20));
    }

    @Test
    void studentPredicate_filtersHighGradeStudents() {
        Predicate<Student> gradeAtLeast3 = s -> s.getGradeLevel() >= 3;
        List<Student> result = getAllStudents().stream()
                .filter(gradeAtLeast3)
                .collect(Collectors.toList());
        // Emily(3), Dave(3), Sophia(4), James(4) = 4 students
        assertEquals(4, result.size());
        assertTrue(result.stream().allMatch(s -> s.getGradeLevel() >= 3));
    }
}
