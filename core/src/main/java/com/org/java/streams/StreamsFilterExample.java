package com.org.java.streams;

import com.org.java.data.Student;
import com.org.java.data.StudentDataBase;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class StreamsFilterExample {

    private StreamsFilterExample() {
    }

    /** Filters students. */
    private static List<Student> filterStudents(){
        return StudentDataBase.getAllStudents()
                .stream()
                .filter(student -> student.getGpa()>=3.9)
                .filter(student -> student.getGender().equals("female"))
                .collect(toList());
    }

    /** Application entry point. */
    public static void main(String[] args) {

        System.out.println("Filtered Students : " + filterStudents());

    }
}
