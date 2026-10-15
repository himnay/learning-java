package com.org.java.streams;

import com.org.java.data.Student;
import com.org.java.data.StudentDataBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

public class StreamsMapExample {

    private StreamsMapExample() {
    }

    private static List<String> namesUpperCase(List<Student> names){
        return names.stream() //Stream<Student>
                .map(Student::getName) //Stream<String>
                .map(String::toUpperCase) // Stream<String> -> UpperCase
                .collect(toList()); // returns List // terminal operation
    }

    private static Set<String> namesSetUpperCase(List<Student> students){
        return students.stream() //Stream<Student>
                .map(Student::getName) //Stream<String>
                .map(String::toUpperCase) // Stream<String> -> UpperCase
                .collect(toSet()); // returns List // terminal operation
    }

    private static Map<String, Integer> namesLengthMap(ArrayList<String> names){
        return names.stream() //Stream<String>
                .collect(toMap(String::toString,String::length)); // returns Map
    }

    /** Application entry point. */
    public static void main(String[] args) {

        ArrayList<String> names = new ArrayList<>();
        names.add("adam");
        names.add("dan");
        names.add("jenny");

        System.out.println("namesUpperCase List : " + namesUpperCase(StudentDataBase.getAllStudents()));
        System.out.println("namesUpperCase Set : " + namesSetUpperCase(StudentDataBase.getAllStudents()));
        System.out.println("namesLengthMap : " + namesLengthMap(names));

    }

}
