package com.org.java.dateTime;

import com.org.java.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.org.java.StudentDataBase.getAllStudents;
import static org.junit.jupiter.api.Assertions.*;

class ParallelStreamsTest {

    @Test
    @DisplayName("A parallel LongStream sum matches the Gauss-formula expected total")
    void parallelSum_producesCorrectResult() {
        long expected = (long) 100_000 * 100_001 / 2; // Gauss formula
        // IntStream.sum() returns int and overflows for n=100_000; use LongStream
        long actual = LongStream.rangeClosed(1, 100_000).parallel().sum();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("A parallel stream collects distinct, sorted student activities")
    void parallelStream_distinctSortedActivities() {
        List<String> activities = getAllStudents().parallelStream()
                .map(Student::getActivities)
                .flatMap(List::stream)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        assertFalse(activities.isEmpty());
        assertTrue(activities.contains("swimming"));
        // verify sorted order
        for (int i = 1; i < activities.size(); i++) {
            assertTrue(activities.get(i - 1).compareTo(activities.get(i)) <= 0);
        }
    }

    @Test
    @DisplayName("A parallel stream pipeline produces the same result as the equivalent sequential pipeline")
    void parallelStream_sameResultAsSequential() {
        List<String> sequential = getAllStudents().stream()
                .map(Student::getActivities)
                .flatMap(List::stream)
                .distinct().sorted()
                .collect(Collectors.toList());

        List<String> parallel = getAllStudents().parallelStream()
                .map(Student::getActivities)
                .flatMap(List::stream)
                .distinct().sorted()
                .collect(Collectors.toList());

        assertEquals(sequential, parallel);
    }
}
