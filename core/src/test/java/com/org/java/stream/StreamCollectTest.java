package com.org.java.stream;

import com.org.java.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.org.java.StudentDataBase.getAllStudents;
import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.*;

class StreamCollectTest {

    @Test
    @DisplayName("Collectors.joining() with a delimiter and wrapper wraps and separates the student names")
    void joining_wrapsNamesWithDelimiter() {
        String result = getAllStudents().stream()
                .map(Student::getName)
                .collect(Collectors.joining(", ", "[ ", " ]"));
        assertTrue(result.startsWith("[ "));
        assertTrue(result.endsWith(" ]"));
        assertTrue(result.contains("Adam"));
        assertTrue(result.contains("Emily"));
    }

    @Test
    @DisplayName("Collectors.counting() counts only the students whose GPA meets the threshold")
    void counting_countsStudentsMatchingPredicate() {
        long highGpa = getAllStudents().stream()
                .filter(s -> s.getGpa() >= 3.9)
                .collect(Collectors.counting());
        assertEquals(3, highGpa); // Emily(4.0), Dave(3.9), James(3.9)
    }

    @Test
    @DisplayName("Collectors.mapping() to a list collects every student's name, duplicates included")
    void mappingToList_collectsAllNames() {
        List<String> names = getAllStudents().stream()
                .collect(Collectors.mapping(Student::getName, toList()));
        assertEquals(7, names.size());
        assertTrue(names.contains("Adam"));
    }

    @Test
    @DisplayName("Collectors.mapping() to a set deduplicates student names that occur more than once")
    void mappingToSet_deduplicatesNames() {
        Set<String> names = getAllStudents().stream()
                .collect(Collectors.mapping(Student::getName, toSet()));
        assertEquals(6, names.size()); // Adam appears twice → 6 unique
    }

    @Test
    @DisplayName("Collectors.minBy() on GPA finds the student with the lowest GPA")
    void minByGpa_findsSophia() {
        Optional<Student> min = getAllStudents().stream()
                .collect(Collectors.minBy(Comparator.comparing(Student::getGpa)));
        assertTrue(min.isPresent());
        assertEquals(3.5, min.get().getGpa());
        assertEquals("Sophia", min.get().getName());
    }

    @Test
    @DisplayName("Collectors.maxBy() on GPA finds the student with the highest GPA")
    void maxByGpa_findsEmily() {
        Optional<Student> max = getAllStudents().stream()
                .collect(Collectors.maxBy(Comparator.comparing(Student::getGpa)));
        assertTrue(max.isPresent());
        assertEquals(4.0, max.get().getGpa());
        assertEquals("Emily", max.get().getName());
    }

    @Test
    @DisplayName("groupingBy() partitions students into separate lists keyed by gender")
    void groupingByGender_separatesMaleAndFemale() {
        Map<String, List<Student>> grouped = getAllStudents().stream()
                .collect(groupingBy(Student::getGender));
        assertTrue(grouped.containsKey("male"));
        assertTrue(grouped.containsKey("female"));
        assertTrue(grouped.get("male").stream().allMatch(s -> "male".equals(s.getGender())));
        assertTrue(grouped.get("female").stream().allMatch(s -> "female".equals(s.getGender())));
    }

    @Test
    @DisplayName("groupingBy() with a custom classifier labels students as OUTSTANDING or AVERAGE based on GPA")
    void groupingByCustomLabel_categorisesOutstandingVsAverage() {
        Map<String, List<Student>> grouped = getAllStudents().stream()
                .collect(groupingBy(s -> s.getGpa() >= 3.9 ? "OUTSTANDING" : "AVERAGE"));
        assertTrue(grouped.containsKey("OUTSTANDING"));
        assertTrue(grouped.containsKey("AVERAGE"));
        assertTrue(grouped.get("OUTSTANDING").stream().allMatch(s -> s.getGpa() >= 3.9));
        assertTrue(grouped.get("AVERAGE").stream().allMatch(s -> s.getGpa() < 3.9));
    }

    @Test
    @DisplayName("Nested groupingBy() groups students first by grade level, then by GPA performance label")
    void nestedGrouping_byGradeThenPerformance() {
        Map<Integer, Map<String, List<Student>>> grouped = getAllStudents().stream()
                .collect(groupingBy(Student::getGradeLevel,
                        groupingBy(s -> s.getGpa() >= 3.8 ? "OUTSTANDING" : "AVERAGE")));
        assertEquals(3, grouped.size()); // grades 2, 3, 4
        assertTrue(grouped.containsKey(2));
        assertTrue(grouped.containsKey(3));
        assertTrue(grouped.containsKey(4));
    }

    @Test
    @DisplayName("groupingBy() combined with collectingAndThen(maxBy(...)) finds the best student per grade level")
    void collectingAndThen_bestStudentPerGrade() {
        Map<Integer, Student> best = getAllStudents().stream()
                .collect(groupingBy(Student::getGradeLevel,
                        collectingAndThen(maxBy(Comparator.comparing(Student::getGpa)), Optional::get)));
        assertEquals(3.8, best.get(2).getGpa()); // Jenny
        assertEquals(4.0, best.get(3).getGpa()); // Emily
        assertEquals(3.9, best.get(4).getGpa()); // James
    }

    @Test
    @DisplayName("partitioningBy() splits students into high-GPA and low-GPA groups")
    void partitioningBy_separatesHighAndLowGpa() {
        Predicate<Student> highGpa = s -> s.getGpa() >= 3.9;
        Map<Boolean, List<Student>> partitioned = getAllStudents().stream()
                .collect(partitioningBy(highGpa));
        assertTrue(partitioned.get(true).stream().allMatch(s -> s.getGpa() >= 3.9));
        assertTrue(partitioned.get(false).stream().allMatch(s -> s.getGpa() < 3.9));
        assertEquals(3, partitioned.get(true).size());
        assertEquals(4, partitioned.get(false).size());
    }

    @Test
    @DisplayName("partitioningBy() with a downstream toSet() collector produces non-null partitions for both groups")
    void partitioningByToSet_deduplicatesPartitions() {
        Predicate<Student> highGpa = s -> s.getGpa() >= 3.9;
        Map<Boolean, Set<Student>> partitioned = getAllStudents().stream()
                .collect(partitioningBy(highGpa, toSet()));
        assertNotNull(partitioned.get(true));
        assertNotNull(partitioned.get(false));
    }
}
