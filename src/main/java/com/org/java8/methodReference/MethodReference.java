package com.org.java8.methodReference;

import com.org.java8.Student;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class MethodReference {
    public static void main(String[] args) {
        // 1.
        Function<String, String> function = String::toUpperCase;
        System.out.println(function.apply("himansu"));

        // 2.
        Consumer<String> consumer = System.out::println;
        consumer.accept("Nayak");

        // 3.
        Consumer<String> staticMethodConsumer = MethodReference::write;
        staticMethodConsumer.accept("Hello World");

        // 4.
        Predicate<Integer> predicate = MethodReference::isSeniorCitizen;
        predicate.test(70);

        // constructor ref
        Supplier<Student> supplier = Student::new;
        System.out.println(supplier.get().getGender());

        // function
        Function<String, Student> functionStudent = Student::new;
        System.out.println(functionStudent.apply("Himnasu"));

    }

    public static void write(String string) {
        System.out.println(string);
    }

    public static boolean isSeniorCitizen(int age) {
        return age >= 60;
    }
}
