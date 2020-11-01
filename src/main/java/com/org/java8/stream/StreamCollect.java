package com.org.java8.stream;

import com.org.java8.Student;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.org.java8.StudentDataBase.getAllStudents;
import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.partitioningBy;

public class StreamCollect {
    public static void main(String[] args) {

        // 1. joining
        String joining = getAllStudents().stream()
                .map(Student::getName)
                .collect(Collectors.joining(", ", "[ ", " ]"));
        System.out.println(joining);

        // 2. counting
        long counting = getAllStudents().stream()
                .filter(student -> student.getGpa() >= 3.9)
                .collect(Collectors.counting());
        System.out.println(counting);

        // 3 . mapping
        List<String> mapName = getAllStudents().stream()
                .collect(Collectors.mapping(Student::getName, toList()));
        System.out.println(mapName);

        // 4. set
        Set<String> setName = getAllStudents().stream()
                .collect(Collectors.mapping(Student::getName, toSet()));
        System.out.println(setName);

        // 5. minby and maxby
        Optional<Student> minBy = getAllStudents().stream()
                .collect(Collectors.minBy(Comparator.comparing(Student::getGpa)));
        System.out.println(minBy.get());

        Optional<Student> maxBy = getAllStudents().stream()
                .collect(Collectors.maxBy(Comparator.comparing(Student::getGpa)));
        System.out.println(maxBy.get());

        // 6. sum and avg
        long summingInt = getAllStudents().stream()
                .collect(Collectors.summingInt(Student::getNotebook));
        System.out.println(summingInt);

        Double averagingInt = getAllStudents().stream()
                .collect(Collectors.averagingInt(Student::getNotebook));
        System.out.println(averagingInt);

        // 7. groupingBy
        Map<String, List<Student>> groupingByCollector = getAllStudents().stream()
                .collect(groupingBy(Student::getGender));
        System.out.println(groupingByCollector);

        // custom grouping
        Map<String, List<Student>> customGroupingBy = getAllStudents().stream()
                .collect(groupingBy(student -> student.getGpa() >= 3.9 ? "OUTSTANDING" : "AVERAGE"));
        System.out.println(customGroupingBy);

        // 2 argument groupingBy
        Map<Integer, Map<String, List<Student>>> groupingBy2Argument = getAllStudents().stream()
                .collect(groupingBy(Student::getGradeLevel,
                        groupingBy(student -> student.getGpa() >= 3.8 ? "OUTSTANDING" : "AVERAGE")));
        System.out.println(groupingBy2Argument);

        // 3 argument groupingBy.
        // 1st argument key, 3rd argument value, 2nd argument type
        LinkedHashMap<String, Set<Student>> groupingBy3Argument = getAllStudents().stream()
                .collect(groupingBy(Student::getName, LinkedHashMap::new, toSet()));
        System.out.println(groupingBy3Argument);

        // 4. minBy , maxBy and collectingAndThen
        Map<Integer, Optional<Student>> groupingByMinBy = getAllStudents().stream()
                .collect(groupingBy(Student::getGradeLevel, minBy(Comparator.comparing(Student::getGpa))));
        System.out.println(groupingByMinBy);

        Map<Integer, Optional<Student>> groupingByMaxBy = getAllStudents().stream()
                .collect(groupingBy(Student::getGradeLevel, maxBy(Comparator.comparing(Student::getGpa))));
        System.out.println(groupingByMaxBy);

        Map<Integer, Student> groupingByCollectingAndThenMaxBy = getAllStudents().stream()
                .collect(groupingBy(Student::getGradeLevel, collectingAndThen(maxBy(Comparator.comparing(Student::getGpa)), Optional::get)));
        System.out.println(groupingByCollectingAndThenMaxBy);

        // 5. partitioningBy
        // key - false  value = list of students
        // key - true   value = list of students
        Predicate<Student> studentPredicate = (student) -> student.getGpa() >= 3.9;
        Map<Boolean, List<Student>> partitioningByList = getAllStudents().stream()
                .collect(partitioningBy(studentPredicate));
        System.out.println(partitioningByList);

        Map<Boolean, Set<Student>> partitioningBySet = getAllStudents().stream()
                .collect(partitioningBy(studentPredicate, toSet()));
        System.out.println(partitioningBySet);

    }
}
