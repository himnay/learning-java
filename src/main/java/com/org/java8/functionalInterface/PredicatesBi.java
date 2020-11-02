package com.org.java8.functionalInterface;

public class PredicatesBi {
    static java.util.function.BiPredicate<Integer, Double> biPredicate = (gradeLevel, gpa) -> gradeLevel >= 3 && gpa >= 3.9;

    public static void main(String[] args) {
        System.out.println(biPredicate.test(3,3.9));
    }
}