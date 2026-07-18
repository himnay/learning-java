package com.org.java.functionalInterfaces;

import com.org.java.data.Student;
import com.org.java.data.StudentDataBase;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class SupplierExample {

    public static  Supplier<Student> studentSupplier = () -> {
      return  new Student("Adam",2,4.0,"male", Arrays.asList("swimming", "basketball","volleyball"));
    };

    public static  Supplier<List<Student>> studentsSupplier = () -> StudentDataBase.getAllStudents();

    /** Application entry point. */
    public static void main(String[] args) {

        Student student = studentSupplier.get();

        System.out.println("Student is : " + student);

        System.out.println("Students are : " + studentsSupplier.get());
    }
}
