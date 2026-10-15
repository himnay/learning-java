package com.org.java.streams;

import com.org.java.data.Student;
import com.org.java.data.StudentDataBase;

import java.util.function.Predicate;

public class StreamsFilterMapReduceExample {

    private StreamsFilterMapReduceExample() {
    }

    static Predicate<Student> genderPredicate = student -> student.getGender().equals("male");

    static Predicate<Student> gradeLevel = student -> student.getGradeLevel() == 2;

    private static int noOfNoteBooks(){
        return StudentDataBase.getAllStudents().stream()
                //.filter(genderPredicate)
                .filter(gradeLevel)
                .map(Student::getNoteBooks)
               // .reduce(0,(a,b)->a+b); //summing the notebooks.
                .reduce(0,Integer::sum); //summing the notebooks.
    }
    /** Application entry point. */
    public static void main(String[] args) {

        System.out.println("Total No of NoteBooks are : " + noOfNoteBooks());
    }
}
