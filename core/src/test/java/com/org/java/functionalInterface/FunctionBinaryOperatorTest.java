package com.org.java.functionalInterface;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.function.BinaryOperator;

import static org.junit.jupiter.api.Assertions.*;

class FunctionBinaryOperatorTest {

    private final BinaryOperator<Integer> multiply = (a, b) -> a * b;
    private final Comparator<Integer> naturalOrder  = Integer::compareTo;

    @Test
    void apply_multipliesTwoIntegers() {
        assertEquals(6,  multiply.apply(2, 3));
        assertEquals(0,  multiply.apply(0, 99));
        assertEquals(-6, multiply.apply(-2, 3));
    }

    @Test
    void maxBy_returnsLargerOperand() {
        assertEquals(3, BinaryOperator.maxBy(naturalOrder).apply(2, 3));
        assertEquals(3, BinaryOperator.maxBy(naturalOrder).apply(3, 2));
    }

    @Test
    void minBy_returnsSmallerOperand() {
        assertEquals(2, BinaryOperator.minBy(naturalOrder).apply(2, 3));
        assertEquals(2, BinaryOperator.minBy(naturalOrder).apply(3, 2));
    }

    @Test
    void andThen_chainsOperatorsSequentially() {
        BinaryOperator<Integer> add = Integer::sum;
        // add(2,3)=5, then multiply result by itself isn't directly chainable via andThen
        // but compose/andThen on Function<T,T> works:
        java.util.function.Function<Integer, Integer> doubler = x -> x * 2;
        assertEquals(10, add.andThen(doubler).apply(2, 3)); // (2+3)*2
    }
}
