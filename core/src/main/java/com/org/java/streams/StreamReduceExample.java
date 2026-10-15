package com.org.java.streams;

import com.org.java.data.Student;
import com.org.java.data.StudentDataBase;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

public class StreamReduceExample {

    private StreamReduceExample() {
    }

    /** Returns the perform multiplication. */
    private static int performMultiplication(List<Integer> integerList){

        return integerList.stream()
                .reduce(1, (a,b) -> a*b);

    }

    /** Returns the perform multiplication with no initial value. */
    private static Optional<Integer> performMultiplicationWithNoInitialValue(List<Integer> integerList){

        return integerList.stream()
                .reduce( (a,b) -> a*b); // performs multiplication for each element in the stream and returns a new result.
    }

    /** Returns the combine student names. */
    private static String combineStudentNames(){

        return StudentDataBase.getAllStudents().stream()
                .map(Student::getName)
                .distinct()
                .reduce("",(a,b) -> a.concat(b));  // concatenates each element in the stream.
    }

    private static Optional<Student> getHighestGradeStudent(){
        return StudentDataBase.getAllStudents().stream()
                .reduce((s1,s2)->(s1.getGpa()>s2.getGpa()) ? s1 : s2);
    }

    /** Application entry point. */
    public static void main(String[] args) {

        List<Integer> integerList = Arrays.asList(1,3,5,7);
        //List<Integer> integerList = Arrays.asList();

        System.out.println("Result is : " + performMultiplication(integerList));
        Optional<Integer> result = performMultiplicationWithNoInitialValue(integerList);

        if(result.isPresent()){
            System.out.println("Result is : " +result.get());
        }else{
            System.out.println("Result is : " +0);
        }

        System.out.println(combineStudentNames());
        System.out.println(getHighestGradeStudent().get());

        StudentDataBase.getAllStudents().forEach(student -> student.setGpa(4.0));
    }
}
