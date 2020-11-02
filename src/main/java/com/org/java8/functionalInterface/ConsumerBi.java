package com.org.java8.functionalInterface;

import java.util.List;
import java.util.function.BiConsumer;

import static com.org.java8.StudentDataBase.getAllStudents;

/**
 * take 2 argument of any type but no return type
 */
public class ConsumerBi {
    public static void main(String[] args) {
        // 1. basic bi-consumer
        BiConsumer<String, Integer> biConsumer = (a, b) -> {
            System.out.println(a + " " + b);
        };
        biConsumer.accept("himansu", 1);

        // 2. bi-consumer chaining
        BiConsumer<Integer, Integer> mult = (a,b) -> System.out.println(a*b);
        BiConsumer<Integer, Integer> div = (a,b) -> System.out.println(a/b);
        mult.andThen(div).accept(10, 5);

        // 3. bi-consumer collection argument
        BiConsumer<String, List<String>> listBiConsumer = (name, activities) -> System.out.println(name + " " + activities);
        getAllStudents().forEach(student -> listBiConsumer.accept(student.getName(), student.getActivities()));
    }
}