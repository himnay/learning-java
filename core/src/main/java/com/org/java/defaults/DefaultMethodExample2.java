package com.org.java.defaults;

import com.org.java.data.Student;
import com.org.java.data.StudentDataBase;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class DefaultMethodExample2 {

    static Comparator<Student> nameComparator = Comparator.comparing(Student::getName);
    static Comparator<Student> gpaComparator = Comparator.comparing(Student::getGpa);
    static Comparator<Student> genderComparator = Comparator.comparing(Student::getGender);
    static Comparator<Student> gradeComparator = Comparator.comparing(Student::getGradeLevel);
    static Consumer<Student> studentConsumer = student -> System.out.println("student : " + student);

    /** Sorts by name. */
    public static void sortByName(List<Student> studentList){

        studentList.sort(Comparator.comparing(Student::getName)); // inline
        studentList.sort(nameComparator); // Using a reference
        System.out.println("After Sort BY Name : ");
        studentList.forEach(studentConsumer);
    }

    /** Sorts by gpa. */
    public static void sortByGPA(List<Student> studentList){

        studentList.sort(gpaComparator);
        System.out.println("After Sort BY GPA : ");
        studentList.forEach(studentConsumer);
    }

    /** Sorts by gender. */
    public static void sortByGender(){

        List<Student> studentList = StudentDataBase.getAllStudents();
        Comparator<Student> nullLast = Comparator.nullsFirst(genderComparator);
        studentList.sort(nullLast);
        System.out.println("After Sort By Gender : ");
        studentList.forEach(studentConsumer);

    }


    /** Handles comparator chaining. */
    public static void comparatorChaining(){

        List<Student> studentList = StudentDataBase.getAllStudents();
        studentList.sort(gradeComparator.thenComparing(nameComparator));
        System.out.println("Comparator Chaining List");
        studentList.forEach(studentConsumer);
    }

    /** Application entry point. */
    public static void main(String[] args) {
        List<Student> studentList = StudentDataBase.getAllStudents();
        System.out.println("Original List");
        studentList.forEach(student -> System.out.println("student : " + student));
        /*sortByGender();
        sortByName(studentList);
        sortByGPA(studentList);*/
        comparatorChaining();

    }
}
