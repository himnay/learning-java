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
        // 1. Stream Pipeline, Immutable, Cannot be used for add or modify original list
        List<Student> list = getAllStudents().stream()                         // Intermediate Operation
                .filter(gpaPredicate)                                          // Intermediate Operation
                .filter(gradePredicate)                                        // Intermediate Operation
                .collect(Collectors.toList());                                 // Terminal Operation. Also starts the pipeline
        System.out.println(list);

        // 2. lambda debug using peek()
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
                .count();                                                       // Terminal Operation. Also starts the pipeline
        System.out.println(noOfStudents);

        // 5. sorted
        List<Student> sortedStudent = list.stream()
                .sorted(Comparator.comparing(Student::getName))
                .distinct()
                .collect(Collectors.toList());
        System.out.println(sortedStudent);

        // 6. reduce. maintains a state in the memory during calculation
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

        // 8.
        Optional<Integer> min = getAllStudents().stream()
                .map(Student::getNotebook)
                .reduce((n1, n2) -> n1 <= n2 ? n1 : n2);
        System.out.println(min.get());

        // 9. limit and skip
        Optional<Integer> sumLimit = numbers.stream()
                .limit(3)
                .reduce((x, y) -> x + y);
        System.out.println(sumLimit.get());

        Optional<Integer> sumSkip = numbers.stream()
                .skip(3)
                .reduce((x, y) -> x + y);
        System.out.println(sumSkip.get());

        // 10. anymatch, allmatch and nonematch  returns a boolean
        boolean allMatch = getAllStudents().stream()
                .allMatch(i -> i.getGpa() >= 3.5);
        boolean anyMatch = getAllStudents().stream()
                .anyMatch(i -> i.getGpa() >= 4.0);
        boolean noneMatch = getAllStudents().stream()
                .noneMatch(i -> i.getGpa() >= 4.0);
        System.out.println(allMatch + " " + anyMatch + " " + noneMatch);

        // 11. findFirst and findAny returns element
        Optional<Student> findFirstStudent = getAllStudents().stream()
                .filter(i -> i.getGpa() >= 3.9)
                .findFirst();

        Optional<Student> findAnyStudent = getAllStudents().stream()
                .filter(i -> i.getGpa() >= 3.9)
                .findAny();
        System.out.println(findFirstStudent.get() + " " + findAnyStudent);

    }
}
