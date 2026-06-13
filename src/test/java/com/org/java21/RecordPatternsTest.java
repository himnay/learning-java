// FILE 11: src/test/java/com/org/java21/RecordPatternsTest.java
package com.org.java21;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Record Patterns (Java 21)")
class RecordPatternsTest {

    // ---------------------------------------------------------------------------
    // Record definitions
    // ---------------------------------------------------------------------------

    record Point(int x, int y) {}

    record ColoredPoint(Point point, String color) {}

    record Line(Point start, Point end) {}

    record Box<T>(T value) {}

    // ---------------------------------------------------------------------------
    // Record patterns in instanceof
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Record pattern in instanceof deconstructs components directly")
    void recordPatternInstanceof() {
        Object obj = new Point(3, 7);
        if (obj instanceof Point(int x, int y)) {
            assertEquals(3, x);
            assertEquals(7, y);
        } else {
            fail("Expected a Point");
        }
    }

    @Test
    @DisplayName("Record pattern instanceof returns false for non-matching type")
    void recordPatternInstanceofNoMatch() {
        Object obj = "not a point";
        assertFalse(obj instanceof Point(int x, int y));
    }

    @Test
    @DisplayName("Record pattern in instanceof allows immediate use of bound variables")
    void recordPatternUsesComponents() {
        Object obj = new Point(4, 3);
        if (obj instanceof Point(int x, int y)) {
            double dist = Math.sqrt(x * x + y * y);
            assertEquals(5.0, dist, 1e-9);
        } else {
            fail("Pattern did not match");
        }
    }

    // ---------------------------------------------------------------------------
    // Nested record patterns
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Nested record pattern deconstructs inner record in one step")
    void nestedRecordPatternInstanceof() {
        Object obj = new ColoredPoint(new Point(1, 2), "red");
        if (obj instanceof ColoredPoint(Point(int x, int y), String color)) {
            assertEquals(1, x);
            assertEquals(2, y);
            assertEquals("red", color);
        } else {
            fail("Nested pattern did not match");
        }
    }

    @Test
    @DisplayName("Deeply nested record pattern extracts multiple levels")
    void deeplyNestedRecordPattern() {
        Object obj = new Line(new Point(0, 0), new Point(5, 5));
        if (obj instanceof Line(Point(int x1, int y1), Point(int x2, int y2))) {
            assertEquals(0, x1);
            assertEquals(0, y1);
            assertEquals(5, x2);
            assertEquals(5, y2);
            // Distance along x-axis
            assertEquals(5, x2 - x1);
        } else {
            fail("Deep nested pattern did not match");
        }
    }

    // ---------------------------------------------------------------------------
    // Record patterns in switch
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Record pattern in switch statement selects correct arm")
    void recordPatternInSwitch() {
        Object obj = new Point(0, 0);
        String label = switch (obj) {
            case Point(int x, int y) when x == 0 && y == 0 -> "origin";
            case Point(int x, int y) when x == 0           -> "on y-axis";
            case Point(int x, int y) when y == 0           -> "on x-axis";
            case Point(int x, int y)                        -> "quadrant";
            default                                         -> "not a point";
        };
        assertEquals("origin", label);
    }

    @Test
    @DisplayName("Record pattern switch selects 'on y-axis' when x == 0 and y != 0")
    void recordPatternSwitchYAxis() {
        Object obj = new Point(0, 5);
        String label = switch (obj) {
            case Point(int x, int y) when x == 0 && y == 0 -> "origin";
            case Point(int x, int y) when x == 0           -> "on y-axis";
            case Point(int x, int y) when y == 0           -> "on x-axis";
            case Point(int x, int y)                        -> "quadrant";
            default                                         -> "not a point";
        };
        assertEquals("on y-axis", label);
    }

    @Test
    @DisplayName("Nested record pattern in switch extracts inner components")
    void nestedRecordPatternInSwitch() {
        Object obj = new ColoredPoint(new Point(2, 3), "blue");
        String result = switch (obj) {
            case ColoredPoint(Point(int x, int y), String c) when c.equals("red")  ->
                    "red at (%d,%d)".formatted(x, y);
            case ColoredPoint(Point(int x, int y), String c) when c.equals("blue") ->
                    "blue at (%d,%d)".formatted(x, y);
            case ColoredPoint(Point(int x, int y), String c) ->
                    "other color %s".formatted(c);
            default -> "unknown";
        };
        assertEquals("blue at (2,3)", result);
    }

    // ---------------------------------------------------------------------------
    // Generic record patterns
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Generic record pattern infers type variable in instanceof")
    void genericRecordPattern() {
        Box<String> box = new Box<>("hello");
        if (box instanceof Box(String s)) {
            assertEquals("hello", s);
            assertEquals(5, s.length());
        } else {
            fail("Generic record pattern did not match");
        }
    }

    @Test
    @DisplayName("Generic record pattern works in switch with type test")
    void genericRecordPatternSwitch() {
        Object obj = new Box<>(42);
        String result = switch (obj) {
            case Box(Integer i) -> "int box: " + i;
            case Box(String s)  -> "string box: " + s;
            default             -> "other";
        };
        assertEquals("int box: 42", result);
    }

    // ---------------------------------------------------------------------------
    // Record patterns with var
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Record pattern with var infers component types")
    void recordPatternWithVar() {
        Object obj = new Point(10, 20);
        if (obj instanceof Point(var x, var y)) {
            // x and y are inferred as int
            assertEquals(10, x);
            assertEquals(20, y);
        } else {
            fail("Pattern with var did not match");
        }
    }

    // ---------------------------------------------------------------------------
    // Null safety
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Record pattern in instanceof returns false for null")
    void recordPatternNullSafe() {
        Object obj = null;
        assertFalse(obj instanceof Point(int x, int y));
    }
}
