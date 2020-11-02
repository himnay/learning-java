package com.org.java8.functionalInterface;

import com.org.java8.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

import static com.org.java8.StudentDataBase.getAllStudents;
import static com.org.java8.functionalInterface.PredicatesBi.biPredicate;

/**
 * take 2 argument type of different datatype are return type are different
 */
public class FunctionBi {
    public static void main(String[] args) {
        BiFunction<List<Student>, BiPredicate<Integer, Double>, Map<String, Double>> listPredicateMapBiFunctions = (students, biPredicate) -> {
            Map<String, Double> nameGPA = new HashMap<>();
            students.forEach(student -> {
                if (biPredicate.test(student.getGradeLevel(), student.getGpa())) {
                    nameGPA.put(student.getName(), student.getGpa());
                }
            });
            return nameGPA;
        };

        Map<String, Double> nameGPA = listPredicateMapBiFunctions.apply(getAllStudents(), biPredicate);
        System.out.println(nameGPA);
    }
}
