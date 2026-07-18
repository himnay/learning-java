package com.org.java.optional;

import com.org.java.data.Bike;
import com.org.java.data.Student;
import com.org.java.data.StudentDataBase;

import java.util.Optional;

public class OptionalMapFlatMapExample {

    /** Handles optional flat map. */
    public static void optionalFlatMap(){
      Optional<Student> studentOptional =  StudentDataBase.getOptionalStudent();
      if(studentOptional.isPresent()){
          Optional<Bike> bikeOptional= studentOptional.
                  flatMap(Student::getBike); //
          System.out.println("bikeOptional : " + bikeOptional);
      }
    }

    /** Handles optional map. */
    public static void optionalMap(){
        Optional<Student> studentOptional =  StudentDataBase.getOptionalStudent();

        if(studentOptional.isPresent()){
            Optional<String> nameOptional= studentOptional.
                    map(Student::getName); //
            System.out.println("nameOptional : " + nameOptional);
        }
    }

    /** Handles optional filter. */
    public static void optionalFilter(){

        Optional<Student> studentOptional = StudentDataBase.getOptionalStudent()
                .filter(student -> student.getGpa()>=4.3);

        studentOptional.ifPresent(student -> System.out.println(student));

    }

    /** Application entry point. */
    public static void main(String[] args) {
        optionalFlatMap();
        optionalMap();
        optionalFilter();
    }
}
