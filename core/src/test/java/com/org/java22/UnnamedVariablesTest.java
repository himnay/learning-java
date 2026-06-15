// FILE 13: src/test/java/com/org/java22/UnnamedVariablesTest.java
package com.org.java22;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unnamed Variables _ (Java 22)")
class UnnamedVariablesTest {

    // ---------------------------------------------------------------------------
    // Unnamed variable in catch blocks
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Unnamed variable _ in catch block ignores the exception instance")
    void unnamedCatchVariable() {
        int result;
        try {
            result = Integer.parseInt("not-a-number");
        } catch (NumberFormatException _) {
            result = -1;
        }
        assertEquals(-1, result);
    }

    @Test
    @DisplayName("Unnamed _ in catch correctly handles multiple exception types")
    void unnamedCatchMultipleTypes() {
        AtomicInteger errorCount = new AtomicInteger(0);

        // First: NumberFormatException
        try {
            Integer.parseInt("abc");
        } catch (Exception _) {
            errorCount.incrementAndGet();
        }

        // Second: ArithmeticException
        try {
            int ignored = 10 / 0;
        } catch (ArithmeticException _) {
            errorCount.incrementAndGet();
        }

        assertEquals(2, errorCount.get());
    }

    @Test
    @DisplayName("Unnamed _ in catch allows the block body to run regardless of exception value")
    void unnamedCatchRunsBlock() {
        boolean blockRan = false;
        try {
            throw new RuntimeException("intentional");
        } catch (RuntimeException _) {
            blockRan = true;
        }
        assertTrue(blockRan);
    }

    @Test
    @DisplayName("Unnamed _ in catch, nested try-catch with different exception types")
    void unnamedCatchNestedTryCatch() {
        StringBuilder log = new StringBuilder();
        try {
            try {
                throw new IllegalArgumentException("inner");
            } catch (IllegalArgumentException _) {
                log.append("inner_caught;");
                throw new IllegalStateException("outer");
            }
        } catch (IllegalStateException _) {
            log.append("outer_caught;");
        }
        assertEquals("inner_caught;outer_caught;", log.toString());
    }

    // ---------------------------------------------------------------------------
    // Unnamed variable in enhanced for-loop
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Unnamed _ in enhanced for-loop counts iterations without using element")
    void unnamedForLoopVariable() {
        List<String> items = List.of("apple", "banana", "cherry");
        int count = 0;
        for (var _ : items) {
            count++;
        }
        assertEquals(3, count);
    }

    @Test
    @DisplayName("Unnamed _ in for-loop works with primitive int arrays")
    void unnamedForLoopWithIntArray() {
        int iterations = 0;
        for (var _ : new int[]{10, 20, 30, 40, 50}) {
            iterations++;
        }
        assertEquals(5, iterations);
    }

    @Test
    @DisplayName("Unnamed _ in nested for-loop counts total cells across rows")
    void unnamedNestedForLoop() {
        List<List<Integer>> matrix = List.of(
                List.of(1, 2, 3),
                List.of(4, 5, 6)
        );
        int cellCount = 0;
        for (var row : matrix) {
            for (var _ : row) {
                cellCount++;
            }
        }
        assertEquals(6, cellCount);
    }

    // ---------------------------------------------------------------------------
    // Unnamed variable in lambda
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Unnamed _ in lambda ignores the parameter value and returns constant")
    void unnamedLambdaParameter() {
        List<String> words = List.of("one", "two", "three");
        List<String> replaced = words.stream()
                .map(_ -> "replaced")
                .toList();
        assertEquals(List.of("replaced", "replaced", "replaced"), replaced);
    }

    @Test
    @DisplayName("Unnamed _ in lambda counts elements without using their value")
    void unnamedLambdaCount() {
        AtomicInteger count = new AtomicInteger(0);
        List.of(10, 20, 30, 40).forEach(_ -> count.incrementAndGet());
        assertEquals(4, count.get());
    }

    @Test
    @DisplayName("Unnamed _ in lambda used with Stream.generate() processes fixed count")
    void unnamedLambdaWithGenerate() {
        long count = Stream.generate(() -> "x")
                .limit(5)
                .map(_ -> "y")
                .count();
        assertEquals(5, count);
    }

    // ---------------------------------------------------------------------------
    // Unnamed variable in try-with-resources
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Unnamed _ in try-with-resources ignores the resource variable")
    void unnamedTryWithResources() {
        AtomicInteger closedCount = new AtomicInteger(0);
        AutoCloseable resource = closedCount::incrementAndGet;

        try (var _ = resource) {
            // resource is acquired; variable is intentionally unnamed
        } catch (Exception _) {
            fail("Should not throw");
        }

        assertEquals(1, closedCount.get(), "Resource must have been closed exactly once");
    }

    // ---------------------------------------------------------------------------
    // Multiple _ in the same method (separate scopes)
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Multiple unnamed _ variables can appear in separate catch clauses")
    void multipleUnnamedInSeparateCatch() {
        int errorCount = 0;
        try {
            throw new RuntimeException();
        } catch (RuntimeException _) {
            errorCount++;
        }
        try {
            throw new IllegalArgumentException();
        } catch (IllegalArgumentException _) {
            errorCount++;
        }
        assertEquals(2, errorCount);
    }

    @Test
    @DisplayName("Unnamed _ in for-loop and lambda in the same method work independently")
    void unnamedInForLoopAndLambda() {
        List<Integer> nums = List.of(1, 2, 3);
        int loopCount = 0;
        for (var _ : nums) loopCount++;

        long mapCount = nums.stream().map(_ -> 0).count();

        assertEquals(3, loopCount);
        assertEquals(3, mapCount);
    }

    // ---------------------------------------------------------------------------
    // Unnamed _ in instanceof pattern (Java 22+)
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Unnamed _ can be used as a pattern binding placeholder in instanceof")
    void unnamedPatternBinding() {
        Object obj = "hello";
        // Using _ as the pattern variable name when the value is not needed
        boolean isString = obj instanceof String _;
        assertTrue(isString);
    }
}
