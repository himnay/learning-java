package com.org.java8.stream;

import com.org.java8.Student;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.org.java8.StudentDataBase.getAllStudents;

public class StreamExample {

    private static Predicate<Student> gradePredicate = (student) -> student.getGradeLevel() >= 3;
    private static Predicate<Student> gpaPredicate = (student) -> student.getGpa() >= 3.9;

    public static void main(String[] args) {
        // 1. Stream Pipeline, Immutable, Cannot be used ofr add or modify original list
        List<Student> list = getAllStudents().stream()                         // Intermediate Operation
                .filter(gpaPredicate)                                          // Intermediate Operation
                .filter(gradePredicate)                                        // Intermediate Operation
                .collect(Collectors.toList());                                 // Terminal Operation. Also starts the pipeline
        System.out.println(list);

        // 2. debug
        Map<String, String> debugMap = getAllStudents().stream()
                .peek(System.out::println)                                      // can list the intermediate stage of the pipeline
                .filter(gpaPredicate)
                .peek(System.out::println)
                .filter(gradePredicate)
                .peek(System.out::println)
                .collect(Collectors.toMap(Student::getName, Student::getGender));
        System.out.println(debugMap);

        // 3. map and set
        Set<String> names = getAllStudents().stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .collect(Collectors.toSet());
        System.out.println(names);

        // 4. flatmap -> distinct -> sorted -> count
        long noOfStudents = list.stream()
                .map(Student::getActivities)
                .flatMap(List::stream)                                              // List<List<String>> -> List<Stream>
                .peek(System.out::println)
                .distinct()
                .sorted()
                .count();                                                       // Terminal Operation. Also starts the pipeline
        System.out.println(noOfStudents);

        // 5. sorted
        List<Student> sortedStudent = list.stream()
                .sorted(Comparator.comparing(Student::getName))
                .distinct()
                .collect(Collectors.toList());
        System.out.println(sortedStudent);

        // 6. reduce
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        BinaryOperator<Integer> binaryOperator = (a, b) -> a * b;
        long sum = numbers.stream()
                .reduce(1, binaryOperator);                             // Terminal Operation. Also starts the pipeline
        System.out.println(sum);

        // 6.1
        Optional<Student> student = getAllStudents().stream()
                .reduce((s1, s2) -> s1.getGpa() > s2.getGpa() ? s1 : s2);       // Binary operator functional interface.
        System.out.println(student);                                            // reduce maintain the previous comparison object

        // 7. map - filter - reduce pattern
        long sumOfNotebooks = getAllStudents().stream()
                .map(Student::getNotebook)
//              .reduce(0, (a, b) -> a + b);   you can also try below approach
                .reduce(0, Integer::sum);
        System.out.println(sumOfNotebooks);
    }
}
