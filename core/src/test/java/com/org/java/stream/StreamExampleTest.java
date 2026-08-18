package com.org.java.stream;

import com.org.java.Student;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.org.java.StudentDataBase.getAllStudents;
import static org.junit.jupiter.api.Assertions.*;

class StreamExampleTest {

    private static final Predicate<Student> gradePredicate = s -> s.getGradeLevel() >= 3;
    private static final Predicate<Student> gpaPredicate  = s -> s.getGpa() >= 3.9;

    @Test
    void filterChain_returnsStudentsMatchingBothPredicates() {
        List<Student> result = getAllStudents().stream()
                .filter(gpaPredicate)
                .filter(gradePredicate)
                .collect(Collectors.toList());
        // Emily(4.0,grade3), Dave(3.9,grade3), James(3.9,grade4)
        assertEquals(3, result.size());
        assertTrue(result.stream().allMatch(s -> s.getGpa() >= 3.9 && s.getGradeLevel() >= 3));
    }

    @Test
    void mapToSet_deduplicatesUppercasedNames() {
        Set<String> names = getAllStudents().stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .collect(Collectors.toSet());
        assertEquals(6, names.size()); // Adam appears twice in DB → 6 unique
        assertTrue(names.contains("ADAM"));
        assertTrue(names.contains("EMILY"));
    }

    @Test
    void flatMapDistinct_countsUniqueActivities() {
        List<Student> filtered = getAllStudents().stream()
                .filter(gpaPredicate).filter(gradePredicate)
                .collect(Collectors.toList());

        long distinctActivities = filtered.stream()
                .map(Student::getActivities)
                .flatMap(List::stream)
                .distinct()
                .count();
        assertEquals(7, distinctActivities);
    }

    @Test
    void sortedByName_producesAlphabeticalOrder() {
        List<Student> sorted = getAllStudents().stream()
                .filter(gpaPredicate).filter(gradePredicate)
                .sorted(Comparator.comparing(Student::getName))
                .distinct()
                .collect(Collectors.toList());
        for (int i = 1; i < sorted.size(); i++) {
            assertTrue(sorted.get(i - 1).getName().compareTo(sorted.get(i).getName()) <= 0);
        }
    }

    @Test
    void reduce_computesProductOfList() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        long product = numbers.stream().reduce(1, (a, b) -> a * b);
        assertEquals(120L, product);
    }

    @Test
    void reduce_findsStudentWithHighestGpa() {
        Optional<Student> top = getAllStudents().stream()
                .reduce((s1, s2) -> s1.getGpa() > s2.getGpa() ? s1 : s2);
        assertTrue(top.isPresent());
        assertEquals(4.0, top.get().getGpa());
        assertEquals("Emily", top.get().getName());
    }

    @Test
    void limit_sumsOnlyFirstThreeElements() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        int sum = numbers.stream().limit(3).reduce(0, Integer::sum);
        assertEquals(6, sum); // 1+2+3
    }

    @Test
    void skip_sumsAfterSkippingFirstThree() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        int sum = numbers.stream().skip(3).reduce(0, Integer::sum);
        assertEquals(9, sum); // 4+5
    }

    @Test
    void allMatch_trueWhenAllStudentsHaveGpaAboveThreshold() {
        assertTrue(getAllStudents().stream().allMatch(s -> s.getGpa() >= 3.5));
        assertFalse(getAllStudents().stream().allMatch(s -> s.getGpa() >= 4.0));
    }

    @Test
    void anyMatch_trueWhenAtLeastOneStudentHasPerfectGpa() {
        assertTrue(getAllStudents().stream().anyMatch(s -> s.getGpa() >= 4.0));
    }

    @Test
    void noneMatch_falseWhenSomeStudentsHaveHighGpa() {
        assertFalse(getAllStudents().stream().noneMatch(s -> s.getGpa() >= 4.0));
        assertTrue(getAllStudents().stream().noneMatch(s -> s.getGpa() > 4.0));
    }

    @Test
    void findFirst_returnsFirstHighGpaStudent() {
        Optional<Student> first = getAllStudents().stream()
                .filter(s -> s.getGpa() >= 3.9)
                .findFirst();
        assertTrue(first.isPresent());
        assertTrue(first.get().getGpa() >= 3.9);
    }

    @Test
    void collectToMap_mapsNameToGender() {
        Map<String, String> nameGender = getAllStudents().stream()
                .filter(gpaPredicate).filter(gradePredicate)
                .collect(Collectors.toMap(Student::getName, Student::getGender));
        assertEquals("female", nameGender.get("Emily"));
        assertEquals("male",   nameGender.get("Dave"));
        assertEquals("male",   nameGender.get("James"));
    }
}
