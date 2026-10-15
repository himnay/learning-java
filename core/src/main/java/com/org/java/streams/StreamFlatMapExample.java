package com.org.java.streams;

import com.org.java.data.Student;
import com.org.java.data.StudentDataBase;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class StreamFlatMapExample {

    private StreamFlatMapExample() {
    }

    /** Prints student activities. */
    private static List<String> printStudentActivities() {
        return StudentDataBase.getAllStudents()
                .stream()
                .map(Student::getActivities) //Stream<List<String>>
                .flatMap(List::stream) //<Stream<String>
                .collect(toList());
    }

    /** Prints unique student activities. */
    private static List<String> printUniqueStudentActivities() {
        return StudentDataBase.getAllStudents()
                .stream()
                .map(Student::getActivities)
                .flatMap(List::stream)
                .distinct()
                .sorted()
                .collect(toList());
    }

    private static long getStudentActivitiesCount() {
        return StudentDataBase.getAllStudents()
                .stream()
                .map(Student::getActivities)
                .flatMap(List::stream)
                .distinct()
                .count();
    }

    /** Application entry point. */
    public static void main(String[] args) {
        System.out.println("Student Activities : " + printStudentActivities());
        System.out.println("Unique Student Activities : " + printUniqueStudentActivities());
        System.out.println("Unique Student Activities Count: " + getStudentActivitiesCount());

    }

}
