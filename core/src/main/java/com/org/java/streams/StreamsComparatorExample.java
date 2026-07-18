package com.org.java.streams;

import com.org.java.data.Student;
import com.org.java.data.StudentDataBase;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class StreamsComparatorExample {

    /** Sorts students by name. */
    public static List<Student> sortStudentsByName(){

       return  StudentDataBase.getAllStudents().stream()
                .sorted(Comparator.comparing(Student::getName))
                .collect(toList());
    }

    /** Sorts students by gpa. */
    public static List<Student> sortStudentsByGpa(){

        return  StudentDataBase.getAllStudents().stream()
                .sorted(Comparator.comparing(Student::getGpa))
                .collect(toList());
    }

    /** Sorts students by gpa reversed. */
    public static List<Student> sortStudentsByGpaReversed(){

        return  StudentDataBase.getAllStudents().stream()
                .sorted(Comparator.comparing(Student::getGpa).reversed())
                .collect(toList());
    }

    /** Application entry point. */
    public static void main(String[] args) {
        System.out.println("Students sorted by NAME");
        sortStudentsByName().forEach(System.out::println);
        System.out.println("Students sorted by GPA");
         sortStudentsByGpa().forEach(System.out::println);

        System.out.println("Students sorted by GPA Higher to Lower");
        sortStudentsByGpaReversed().forEach(System.out::println);

    }
}
