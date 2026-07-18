package com.org.java.streams_terminal;

import com.org.java.data.Student;
import com.org.java.data.StudentDataBase;

import java.util.stream.Collectors;

public class StreamsJoiningExample {

    /** Returns the joining. */
    public static String joining(){

        return StudentDataBase.getAllStudents().stream()
                .map(Student::getName)
                .collect(Collectors.joining()); // appends all the strings to produce the output,
    }

    /** Returns the joining with delimiter. */
    public static String joiningWithDelimiter(){

        return StudentDataBase.getAllStudents().stream()
                .map(Student::getName)
                .collect(Collectors.joining("-"));
    }

    /** Returns the joining with delimiter with prefix. */
    public static String joiningWithDelimiterWithPrefix(){

        return StudentDataBase.getAllStudents().stream()
                .map(Student::getName)
                .collect(Collectors.joining("-","[","]"));
    }

    /** Application entry point. */
    public static void main(String[] args) {

        System.out.println("Names : "+ joining());
        System.out.println("Names With Delimiter : "+ joiningWithDelimiter());
        System.out.println("Names With Delimiter Prefix and Suffix : "+ joiningWithDelimiterWithPrefix());
    }
}
