package com.org.java8.functionalInterface;

import com.org.java8.Student;

import java.util.function.Supplier;

import static java.util.Arrays.asList;

/**
 * no argument but only return type
 */
public class Suppliers {
    public static void main(String[] args) {
        Supplier<Student> studentSupplier = () -> new Student("Himansu", 2, 3.2, "Male", asList("Swimming", "basketball"));
        System.out.println(studentSupplier.get());
    }
}
