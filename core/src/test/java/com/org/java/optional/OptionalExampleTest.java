package com.org.java.optional;

import com.org.java.Student;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.function.Supplier;

import static com.org.java.StudentDataBase.getAllStudents;
import static org.junit.jupiter.api.Assertions.*;

class OptionalExampleTest {

    private final Supplier<Student> firstStudent = () -> getAllStudents().get(0); // Adam

    @Test
    void isPresent_trueForNonNullValue() {
        Optional<Student> opt = Optional.ofNullable(firstStudent.get());
        assertTrue(opt.isPresent());
        assertEquals("Adam", opt.map(Student::getName).get());
    }

    @Test
    void empty_isFalseForIsPresent() {
        Optional<String> empty = Optional.empty();
        assertFalse(empty.isPresent());
    }

    @Test
    void orElse_returnsValueWhenPresent() {
        Optional<Student> opt = Optional.ofNullable(firstStudent.get());
        String name = opt.map(Student::getName).orElse("Default");
        assertEquals("Adam", name);
    }

    @Test
    void orElse_returnsDefaultWhenNull() {
        Optional<Student> opt = Optional.ofNullable(null);
        String name = opt.map(Student::getName).orElse("Default");
        assertEquals("Default", name);
    }

    @Test
    void orElseGet_usesSupplierWhenNull() {
        Optional<String> opt = Optional.ofNullable(null);
        String result = opt.orElseGet(() -> "Generated");
        assertEquals("Generated", result);
    }

    @Test
    void orElseThrow_throwsWhenNull() {
        Optional<String> opt = Optional.ofNullable(null);
        assertThrows(RuntimeException.class,
                () -> opt.orElseThrow(() -> new RuntimeException("Not found")));
    }

    @Test
    void ifPresent_executesConsumerWhenNonNull() {
        Optional<String> opt = Optional.ofNullable("Himansu Nayak");
        StringBuilder sb = new StringBuilder();
        opt.ifPresent(sb::append);
        assertEquals("Himansu Nayak", sb.toString());
    }

    @Test
    void filter_returnsEmptyWhenPredicateFails() {
        Optional<Student> opt = Optional.ofNullable(firstStudent.get()); // Adam gpa=3.6
        Optional<Student> filtered = opt.filter(s -> s.getGpa() >= 4.0);
        assertFalse(filtered.isPresent());
    }

    @Test
    void filter_returnsValueWhenPredicatePasses() {
        Optional<Student> opt = Optional.ofNullable(firstStudent.get()); // Adam gpa=3.6
        Optional<Student> filtered = opt.filter(s -> s.getGpa() >= 3.5);
        assertTrue(filtered.isPresent());
        assertEquals(3.6, filtered.map(Student::getGpa).get());
    }

    @Test
    void map_transformsWrappedValue() {
        Optional<Student> opt = Optional.ofNullable(firstStudent.get());
        Optional<Double> gpa = opt.filter(s -> s.getGpa() >= 3.5).map(Student::getGpa);
        assertTrue(gpa.isPresent());
        assertEquals(3.6, gpa.get());
    }

    @Test
    void flatMap_chainsOptionalReturningMethods() {
        // Adam's bike is present → bike.getName() should be "Suzuki"
        Optional<Student> opt = Optional.ofNullable(firstStudent.get());
        Optional<String> bikeName = opt
                .filter(s -> s.getGpa() >= 3.5)
                .flatMap(Student::getBike)
                .map(Student.Bike::getName);
        assertTrue(bikeName.isPresent());
        assertEquals("Suzuki", bikeName.get());
    }
}
