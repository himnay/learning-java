package com.org.java.functionalInterfaces;

import com.org.java.data.Student;
import com.org.java.data.StudentDataBase;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public class BiPredicateExample {

    static BiPredicate<Integer,Double> biPredicate = (gradeLevel, gpa) ->  gradeLevel>=3 && gpa >=3.9 ;

    static Consumer<Student> consumer = (student) -> {
            if(biPredicate.test(student.getGradeLevel(),student.getGpa())){
                System.out.println(student);
            }
    };

    /** Filters students. */
    public static  void filterStudents(){

        List<Student> studentList = StudentDataBase.getAllStudents();

        studentList.forEach(consumer);
    }

    /** Application entry point. */
    public static void main(String[] args) {
        filterStudents();
    }

}
