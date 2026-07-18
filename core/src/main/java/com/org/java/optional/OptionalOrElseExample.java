package com.org.java.optional;

import com.org.java.data.Student;

import java.util.Optional;

public class OptionalOrElseExample {


    /** Returns the optional or else. */
    public static String optionalOrElse(){

        //Optional<Student> getOptionalStudent = Optional.ofNullable(StudentDataBase.studentSupplier.get());
        Optional<Student> optionalStudent = Optional.ofNullable(null);

        String name =  optionalStudent.map(Student::getName).orElse("Default");

        return name;

    }

    /** Returns the optional or else get. */
    public static String optionalOrElseGet(){

        //Optional<Student> getOptionalStudent = Optional.ofNullable(StudentDataBase.studentSupplier.get());
        Optional<Student> optionalStudent = Optional.ofNullable(null);

        String name =  optionalStudent.map(Student::getName).orElseGet(()->"Default");

        return name;

    }

    /** Returns the optional or else throw. */
    public static String optionalOrElseThrow(){

        //Optional<Student> getOptionalStudent = Optional.ofNullable(StudentDataBase.studentSupplier.get());
        Optional<Student> optionalStudent = Optional.ofNullable(null);

        String name =  optionalStudent.map(Student::getName).orElseThrow(()->new RuntimeException("No Data available"));

        return name;

    }

    /** Application entry point. */
    public static void main(String[] args) {


        System.out.println(optionalOrElse());
        System.out.println(optionalOrElseGet());
        System.out.println(optionalOrElseThrow());
    }
}
