package com.org.java.streams;

import com.org.java.data.StudentDataBase;

public class StreamsMatchExample {


    /** Returns the all match. */
    public static boolean allMatch(){

        boolean result = StudentDataBase.getAllStudents().stream()
                .allMatch(student -> student.getGpa()>=3.9);

        return result;
    }

    /** Returns the any match. */
    public static boolean anyMatch(){

        boolean result = StudentDataBase.getAllStudents().stream()
                .anyMatch(student -> student.getGpa()>=3.9);

        return result;
    }

    /** Returns the none match. */
    public static boolean noneMatch(){

        boolean result = StudentDataBase.getAllStudents().stream()
                .noneMatch(student -> student.getGpa()>=3.9);

        return result;
    }

    /** Application entry point. */
    public static void main(String[] args) {

        System.out.println("Result of allMatch : " + allMatch());
        System.out.println("Result of anyMatch : " + anyMatch());
        System.out.println("Result of noneMatch : " + noneMatch());
    }
}
