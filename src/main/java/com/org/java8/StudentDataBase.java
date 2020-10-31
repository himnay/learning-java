package com.org.java8;
import java.util.Arrays;
import java.util.List;

public class StudentDataBase {

    /**
     * Total of 6 students in the database.
     * @return
     */
    public static List<com.aig.Student> getAllStudents(){

        /**
         * 2nd grade students
         */
        com.aig.Student student1 = new com.aig.Student("Adam",2,3.6, "male",Arrays.asList("swimming", "basketball","volleyball"));
        com.aig.Student student2 = new com.aig.Student("Jenny",2,3.8,"female", Arrays.asList("swimming", "gymnastics","soccer"));
        /**
         * 3rd grade students
         */
        com.aig.Student student3 = new com.aig.Student("Emily",3,4.0,"female", Arrays.asList("swimming", "gymnastics","aerobics"));
        com.aig.Student student4 = new com.aig.Student("Dave",3,3.9,"male", Arrays.asList("swimming", "gymnastics","soccer"));
        /**
         * 4th grade students
         */
        com.aig.Student student5 = new com.aig.Student("Sophia",4,3.5,"female", Arrays.asList("swimming", "dancing","football"));
        com.aig.Student student6 = new com.aig.Student("James",4,3.9,"male", Arrays.asList("swimming", "basketball","baseball","football"));

        List<com.aig.Student> students = Arrays.asList(student1,student2,student3,student4,student5,student6);
        return students;
    }
}
