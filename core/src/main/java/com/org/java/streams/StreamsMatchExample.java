package com.org.java.streams;

import com.org.java.data.StudentDataBase;

public class StreamsMatchExample {

    private StreamsMatchExample() {
    }

    /** Returns the all match. */
    private static boolean allMatch(){
        return StudentDataBase.getAllStudents().stream()
                .allMatch(student -> student.getGpa()>=3.9);
    }

    /** Returns the any match. */
    private static boolean anyMatch(){
        return StudentDataBase.getAllStudents().stream()
                .anyMatch(student -> student.getGpa()>=3.9);
    }

    /** Returns the none match. */
    private static boolean noneMatch(){
        return StudentDataBase.getAllStudents().stream()
                .noneMatch(student -> student.getGpa()>=3.9);
    }

    /** Application entry point. */
    public static void main(String[] args) {

        System.out.println("Result of allMatch : " + allMatch());
        System.out.println("Result of anyMatch : " + anyMatch());
        System.out.println("Result of noneMatch : " + noneMatch());
    }
}
