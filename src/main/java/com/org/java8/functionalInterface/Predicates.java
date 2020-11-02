package com.org.java8.functionalInterface;

import com.org.java8.Student;

import java.util.function.Predicate;

import static com.org.java8.StudentDataBase.getAllStudents;

/**
 * argument but return type is boolean
 */
public class Predicates {
    public static void main(String[] args) {
        // 1. predicate
        Predicate<Integer> predicate = (a) -> {
            return a % 2 == 0;
        };
        System.out.println(predicate.test(2));

        // 1.1 lambda style
        Predicate<Integer> lambdaPredicate = (a) -> a % 5 == 0;
        System.out.println(lambdaPredicate.test(2));

        //2 short-circuit and, or and negate
        System.out.println(predicate.and(lambdaPredicate).test(20));
        System.out.println(predicate.or(lambdaPredicate).test(5));
        System.out.println(predicate.and(lambdaPredicate).negate().test(20));

        // 3 predicate block
        Predicate<Student> studentPredicate = (s) -> s.getGradeLevel() >= 3;
        getAllStudents().forEach(student -> {
            if (studentPredicate.test(student)) {
                System.out.println(student);
            }
        });
    }
}
