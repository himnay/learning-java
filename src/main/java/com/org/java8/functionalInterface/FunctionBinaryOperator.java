package com.org.java8.functionalInterface;

import java.util.Comparator;
import java.util.function.BinaryOperator;

/**
 * take 2 same datatype argument and same return type
 */
public class FunctionBinaryOperator {
    public static void main(String[] args) {
        BinaryOperator<Integer> binaryOperator = (a, b) -> a*b;
        System.out.println(binaryOperator.apply(2, 3));

        Comparator<Integer> comparator = (a, b) -> a.compareTo(b);
        System.out.println(BinaryOperator.maxBy(comparator).apply(2, 3));
        System.out.println(BinaryOperator.minBy(comparator).apply(2, 3));
    }
}
