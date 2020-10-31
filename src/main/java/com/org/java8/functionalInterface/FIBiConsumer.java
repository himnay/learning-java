package com.org.java8.functionalInterface;

import java.util.List;
import java.util.function.BiConsumer;

import static com.org.java8.StudentDataBase.getAllStudents;

public class FIBiConsumer {
    public static void main(String[] args) {
        // 1.
        BiConsumer<String, String> biConsumer = (a, b) -> {
            System.out.println(a + " " + b);
        };
        biConsumer.accept("himansu", "nayak");

        // 2.
        BiConsumer<Integer, Integer> mult = (a,b) -> System.out.println(a*b);
        BiConsumer<Integer, Integer> div = (a,b) -> System.out.println(a/b);
        mult.andThen(div).accept(10, 5);

        // 3.
        BiConsumer<String, List<String>> listBiConsumer = (name, activities) -> System.out.println(name + " " + activities);
        getAllStudents().forEach(student -> listBiConsumer.accept(student.getName(), student.getActivities()));
    }
}