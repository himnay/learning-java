package com.org.java.functionalInterface;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.BiPredicate;

import static org.junit.jupiter.api.Assertions.*;

class PredicatesBiTest {

    static final BiPredicate<Integer, Double> biPredicate =
            (gradeLevel, gpa) -> gradeLevel >= 3 && gpa >= 3.9;

    @Test
    @DisplayName("The BiPredicate passes when both grade level and GPA meet their thresholds")
    void test_satisfiesBothConditions() {
        assertTrue(biPredicate.test(3, 3.9));
        assertTrue(biPredicate.test(4, 4.0));
    }

    @Test
    @DisplayName("The BiPredicate fails when the grade level is below the required threshold")
    void test_failsWhenGradeLevelTooLow() {
        assertFalse(biPredicate.test(2, 4.0));
    }

    @Test
    @DisplayName("The BiPredicate fails when the GPA is below the required threshold")
    void test_failsWhenGpaTooLow() {
        assertFalse(biPredicate.test(3, 3.8));
    }

    @Test
    @DisplayName("negate() inverts the BiPredicate's result for inputs that would otherwise fail")
    void negate_invertsBiPredicate() {
        assertTrue(biPredicate.negate().test(2, 3.0));
    }

    @Test
    @DisplayName("and() requires both the original BiPredicate and the additional one to pass")
    void and_combinesWithAnotherBiPredicate() {
        BiPredicate<Integer, Double> isTopGrade = (grade, gpa) -> grade == 4;
        assertTrue(biPredicate.and(isTopGrade).test(4, 3.9));
        assertFalse(biPredicate.and(isTopGrade).test(3, 3.9)); // grade 3 fails isTopGrade
    }
}
