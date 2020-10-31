package com.org.java8.functionalInterface;

import com.org.java8.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.org.java8.StudentDataBase.getAllStudents;

public class FIFunction {
    public static void main(String[] args) {
        // 1.
        Function<String, String> function = (name) -> name.toUpperCase();
        Function<String, String> function2 = (name) -> name.concat("Nayak");

        System.out.println(function.apply("Himansu"));
        System.out.println(function.andThen(function2).apply("Himansu"));
        System.out.println(function.compose(function2).apply("Himansu"));

        // 2.
        Function<List<Student>, Map<String, Double>> listMapFunction = (students) -> {
            Map<String, Double> nameGPA = new HashMap<>();
            students.forEach(student -> nameGPA.put(student.getName(), student.getGpa()));
            return nameGPA;
        };
        Map<String, Double> nameGPA = listMapFunction.apply(getAllStudents());
        System.out.println(nameGPA);
    }
}
