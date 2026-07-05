package com.org.java.optional;

import com.org.java.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.function.Supplier;

import static com.org.java.StudentDataBase.getAllStudents;
import static org.junit.jupiter.api.Assertions.*;

class OptionalExampleTest {

    private final Supplier<Student> firstStudent = () -> getAllStudents().get(0); // Adam

    @Test
    @DisplayName("isPresent() returns true when the Optional wraps a non-null value")
    void isPresent_trueForNonNullValue() {
        Optional<Student> opt = Optional.ofNullable(firstStudent.get());
        assertTrue(opt.isPresent());
        assertEquals("Adam", opt.map(Student::getName).get());
    }

    @Test
    @DisplayName("isPresent() returns false for an empty Optional")
    void empty_isFalseForIsPresent() {
        Optional<String> empty = Optional.empty();
        assertFalse(empty.isPresent());
    }

    @Test
    @DisplayName("orElse() returns the wrapped value when the Optional is present")
    void orElse_returnsValueWhenPresent() {
        Optional<Student> opt = Optional.ofNullable(firstStudent.get());
        String name = opt.map(Student::getName).orElse("Default");
        assertEquals("Adam", name);
    }

    @Test
    @DisplayName("orElse() returns the default value when the Optional was created from null")
    void orElse_returnsDefaultWhenNull() {
        Optional<Student> opt = Optional.ofNullable(null);
        String name = opt.map(Student::getName).orElse("Default");
        assertEquals("Default", name);
    }

    @Test
    @DisplayName("orElseGet() invokes the supplier to produce a value when the Optional is empty")
    void orElseGet_usesSupplierWhenNull() {
        Optional<String> opt = Optional.ofNullable(null);
        String result = opt.orElseGet(() -> "Generated");
        assertEquals("Generated", result);
    }

    @Test
    @DisplayName("orElseThrow() throws the supplied exception when the Optional is empty")
    void orElseThrow_throwsWhenNull() {
        Optional<String> opt = Optional.ofNullable(null);
        assertThrows(RuntimeException.class,
                () -> opt.orElseThrow(() -> new RuntimeException("Not found")));
    }

    @Test
    @DisplayName("ifPresent() runs the consumer when the Optional holds a non-null value")
    void ifPresent_executesConsumerWhenNonNull() {
        Optional<String> opt = Optional.ofNullable("Himansu Nayak");
        StringBuilder sb = new StringBuilder();
        opt.ifPresent(sb::append);
        assertEquals("Himansu Nayak", sb.toString());
    }

    @Test
    @DisplayName("filter() produces an empty Optional when the value fails the predicate")
    void filter_returnsEmptyWhenPredicateFails() {
        Optional<Student> opt = Optional.ofNullable(firstStudent.get()); // Adam gpa=3.6
        Optional<Student> filtered = opt.filter(s -> s.getGpa() >= 4.0);
        assertFalse(filtered.isPresent());
    }

    @Test
    @DisplayName("filter() keeps the value present when it satisfies the predicate")
    void filter_returnsValueWhenPredicatePasses() {
        Optional<Student> opt = Optional.ofNullable(firstStudent.get()); // Adam gpa=3.6
        Optional<Student> filtered = opt.filter(s -> s.getGpa() >= 3.5);
        assertTrue(filtered.isPresent());
        assertEquals(3.6, filtered.map(Student::getGpa).get());
    }

    @Test
    @DisplayName("map() transforms the wrapped student into their GPA value")
    void map_transformsWrappedValue() {
        Optional<Student> opt = Optional.ofNullable(firstStudent.get());
        Optional<Double> gpa = opt.filter(s -> s.getGpa() >= 3.5).map(Student::getGpa);
        assertTrue(gpa.isPresent());
        assertEquals(3.6, gpa.get());
    }

    @Test
    @DisplayName("flatMap() chains an Optional-returning method to retrieve the student's bike name")
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
