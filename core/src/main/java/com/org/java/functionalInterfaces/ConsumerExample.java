package com.org.java.functionalInterfaces;

import com.org.java.data.Student;
import com.org.java.data.StudentDataBase;

import java.util.List;
import java.util.function.Consumer;

public class ConsumerExample {

    static Consumer<Student>  c1= p -> System.out.println(p);

    static Consumer<Student>  c2= p -> System.out.print(p.getName().toUpperCase());

    static Consumer<Student>  c3= p -> System.out.println(p.getActivities());


    /** Prints name. */
    public static void printName(){

        List<Student> personList = StudentDataBase.getAllStudents();

        personList.forEach(c1);

    }

    /** Prints name and activities. */
    public static void printNameAndActivities(){
        System.out.println("printNameAndActivities : ");
        List<Student> personList = StudentDataBase.getAllStudents();
        personList.forEach(c2.andThen(c3));
    }

    /** Prints name and activities using condition. */
    public static void printNameAndActivitiesUsingCondition(){
        System.out.println("printNameAndActivitiesUsingCondition : ");
        List<Student> personList = StudentDataBase.getAllStudents();
        personList.forEach((s) -> {
            if( s.getGradeLevel()>=3 && s.getGpa()>3.9){
                c2.andThen(c3).accept(s);
            }
        });
    }

    /** Application entry point. */
    public static void main(String[] args) {

        Consumer<String> c1 = (s) -> System.out.println(s.toUpperCase());

        c1.accept("java8");

        printName();
        printNameAndActivities();
        printNameAndActivitiesUsingCondition();





    }
}
