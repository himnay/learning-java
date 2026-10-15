package com.org.java.streams;

import com.org.java.data.Student;
import com.org.java.data.StudentDataBase;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StreamsExample {

    private StreamsExample() {
    }

    /** Application entry point. */
    public static void main(String[] args) {

        Predicate<Student> gpaPredicate = student -> student.getGpa()>=3.9;

        Map<String,List<String>> studentMap = StudentDataBase.getAllStudents().stream(). //.parallelStream, don't forget.
                filter(gpaPredicate) // Stream<Student>
                .collect(Collectors.toMap(Student::getName ,Student::getActivities ));

        System.out.println("studentMap  : " + studentMap);

        List<String> studentActivities = StudentDataBase.getAllStudents().
                stream() // Stream<Student>
                .map(Student::getActivities) //<Stream<List<Activities>>
                .flatMap(List::stream) //<Stream<String>
                .distinct() // removes duplicates
                .collect(Collectors.toList()); //collects it to a list.

        List<String> namesList = StudentDataBase.getAllStudents().
                stream() // Stream<Student>
                .peek(System.out::println)
                .map(Student::getName) //<Stream<List<Activities>>
                .peek(System.out::println)
                .distinct() // removes duplicates
                .collect(Collectors.toList()); //collects it to a list.

        System.out.println("namesList  : " + namesList);

    }
}
