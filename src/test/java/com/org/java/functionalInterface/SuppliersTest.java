package com.org.java.functionalInterface;

import com.org.java.Student;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

class SuppliersTest {

    @Test
    void get_returnsNewStudentInstance() {
        Supplier<Student> supplier = () ->
                new Student("Himansu", 2, 3.2, "Male", asList("Swimming", "basketball"));
        Student s = supplier.get();
        assertEquals("Himansu", s.getName());
        assertEquals(2, s.getGradeLevel());
        assertEquals(3.2, s.getGpa());
    }

    @Test
    void get_returnsDifferentInstancesEachCall() {
        Supplier<Student> supplier = () -> new Student("Clone", 1, 2.0, "male", asList());
        Student s1 = supplier.get();
        Student s2 = supplier.get();
        assertNotSame(s1, s2);
        assertEquals(s1.getName(), s2.getName());
    }
}
