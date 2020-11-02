package com.org.java8.dateTime;

import com.org.java8.Student;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.org.java8.StudentDataBase.getAllStudents;

public class ParallelStreams {
    public static void main(String[] args) {
        // uses fork/join framework
        System.out.println(Runtime.getRuntime().availableProcessors());
        long sumParallel = IntStream.rangeClosed(1, 100000).parallel().sum();
        System.out.println(sumParallel);

        // 2.
        List<String> activites = getAllStudents().parallelStream()
                .map(Student::getActivities)
                .flatMap(List::stream)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        System.out.println(activites);

        // NOTE: 3. always check the performance before moving to parallel stream()
        // make sure your code is thread safe before using parallel stream()
    }
}
