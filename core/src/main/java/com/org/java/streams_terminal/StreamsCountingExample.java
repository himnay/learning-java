package com.org.java.streams_terminal;

import com.org.java.data.StudentDataBase;

import java.util.stream.Collectors;

public class StreamsCountingExample {

    /** Counts. */
    public static long count(){
       return  StudentDataBase.getAllStudents()
                .stream()
                .filter(student -> student.getGpa()>=3.9)
                .collect(Collectors.counting());
    }

    /** Application entry point. */
    public static void main(String[] args) {

        System.out.println(count());
    }
}
