package com.org.java8.defaultInterface;

import com.org.java8.Student;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import static com.org.java8.StudentDataBase.getAllStudents;
import static java.util.Comparator.comparing;

public class DefaultInterfaceExample {
    private static Consumer<Student> studentConsumer = System.out::println;

    public static void main(String[] args) {
        // 1.
        List<String> names = Arrays.asList("Alpha", "Beta", "Gamma", "Theta");
        names.sort(Comparator.reverseOrder());
        System.out.println(names);

        // 2. sort by name, comparator chaining, accept null values
        List<Student> students = getAllStudents();
        Comparator<Student> comparatorName = Comparator.nullsLast(comparing(Student::getName));
        Comparator<Student> comparatorGender = comparing(Student::getGender);
        students.sort(comparatorName.thenComparing(comparatorGender));
        students.forEach(studentConsumer);
    }
}
