package com.org.java.defaultInterface;

import com.org.java.Student;
import com.org.java.defaultInterface.diamond.Interface1;
import com.org.java.defaultInterface.diamond.Interface2;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static com.org.java.StudentDataBase.getAllStudents;
import static java.util.Comparator.comparing;
import static org.junit.jupiter.api.Assertions.*;

class DefaultInterfaceTest {

    @Test
    void sortStringsReverseOrder() {
        List<String> names = Arrays.asList("Alpha", "Beta", "Gamma", "Theta");
        names.sort(Comparator.reverseOrder());
        assertEquals("Theta", names.get(0));
        assertEquals("Alpha", names.get(3));
    }

    @Test
    void sortStudentsByName_withNullsLast() {
        List<Student> students = getAllStudents();
        Comparator<Student> byName = Comparator.nullsLast(comparing(Student::getName));
        students.sort(byName);
        // First student alphabetically: Adam
        assertEquals("Adam", students.get(0).getName());
    }

    @Test
    void chainedComparators_byNameThenGender() {
        List<Student> students = getAllStudents();
        Comparator<Student> byName   = Comparator.nullsLast(comparing(Student::getName));
        Comparator<Student> byGender = comparing(Student::getGender);
        students.sort(byName.thenComparing(byGender));
        // Adam appears twice; both have same name → gender tiebreak (male == male)
        assertEquals("Adam", students.get(0).getName());
        assertEquals("Adam", students.get(1).getName());
    }

    @Test
    void diamondProblem_resolvedByOverride() {
        // When a class implements two interfaces with the same default method,
        // it MUST override to resolve ambiguity.
        class Client implements Interface1, Interface2 {
            @Override
            public void methodA() {
                Interface1.super.methodA(); // explicitly delegate to Interface1
            }
        }
        Client c = new Client();
        assertDoesNotThrow(c::methodA);
    }

    @Test
    void defaultMethod_canBeCalledViaInterface() {
        Interface1 impl = new Interface1() {}; // anonymous impl using default
        assertDoesNotThrow(impl::methodA);
    }
}
