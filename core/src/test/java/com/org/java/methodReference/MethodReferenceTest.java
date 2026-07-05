package com.org.java.methodReference;

import com.org.java.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class MethodReferenceTest {

    public static void write(String s) {
        // used via static method reference
    }

    public static boolean isSeniorCitizen(int age) {
        return age >= 60;
    }

    @Test
    @DisplayName("An unbound instance method reference on a type uppercases the given string")
    void instanceMethodReference_onType() {
        Function<String, String> upper = String::toUpperCase;
        assertEquals("HIMANSU", upper.apply("himansu"));
    }

    @Test
    @DisplayName("A bound instance method reference adds elements to a specific list instance")
    void instanceMethodReference_onInstance() {
        List<String> collected = new ArrayList<>();
        Consumer<String> add = collected::add;
        add.accept("alpha");
        add.accept("beta");
        assertEquals(List.of("alpha", "beta"), collected);
    }

    @Test
    @DisplayName("A static method reference correctly classifies ages as senior or not")
    void staticMethodReference() {
        Predicate<Integer> isSenior = MethodReferenceTest::isSeniorCitizen;
        assertTrue(isSenior.test(65));
        assertFalse(isSenior.test(30));
    }

    @Test
    @DisplayName("A no-arg constructor reference supplies a new Student with default field values")
    void constructorReference_noArgSupplier() {
        Supplier<Student> supplier = Student::new;
        Student s = supplier.get();
        assertNotNull(s);
        assertNull(s.getName()); // default constructor
    }

    @Test
    @DisplayName("A single-arg constructor reference builds a Student using the given name")
    void constructorReference_withArg() {
        Function<String, Student> factory = Student::new; // uses Student(String name)
        Student s = factory.apply("TestStudent");
        assertEquals("TestStudent", s.getName());
    }
}
