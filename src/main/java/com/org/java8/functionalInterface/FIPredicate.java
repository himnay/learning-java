package com.org.java8.functionalInterface;

import com.org.java8.Student;

import java.util.function.Predicate;

import static com.org.java8.StudentDataBase.getAllStudents;

public class FIPredicate {
    public static void main(String[] args) {
        // 1
        Predicate<Integer> expression1 = (a) -> {
            return a % 2 == 0;
        };
        Predicate<Integer> expression2 = (a) -> a % 5 == 0;
        System.out.println(expression2.test(2));

        //2
        System.out.println(expression1.and(expression2).test(20));

        // 3
        System.out.println(expression1.or(expression2).test(5));

        // 4
        System.out.println(expression1.and(expression2).negate().test(20));

        // 5
        Predicate<Student> studentPredicate = (s) -> s.getGradeLevel() >= 3;

        getAllStudents().forEach(student -> {
            if (studentPredicate.test(student)) {
                System.out.println(student);
            }
        });
    }
}
