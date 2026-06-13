// FILE 1: src/test/java/com/org/java14/SwitchExpressionsTest.java
package com.org.java14;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Switch Expressions (Java 14)")
class SwitchExpressionsTest {

    // ---------------------------------------------------------------------------
    // Arrow-form switch expression
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Arrow switch expression returns the matching arm value")
    void arrowSwitchExpression() {
        String day = "MONDAY";
        String kind = switch (day) {
            case "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY" -> "Weekday";
            case "SATURDAY", "SUNDAY" -> "Weekend";
            default -> "Unknown";
        };
        assertEquals("Weekday", kind);
    }

    @Test
    @DisplayName("Arrow switch expression with multiple case labels shares one arm")
    void multipleCaseLabelsArrow() {
        int month = 4; // April
        int daysInMonth = switch (month) {
            case 1, 3, 5, 7, 8, 10, 12 -> 31;
            case 4, 6, 9, 11             -> 30;
            case 2                       -> 28;
            default -> throw new IllegalArgumentException("Invalid month: " + month);
        };
        assertEquals(30, daysInMonth);
    }

    // ---------------------------------------------------------------------------
    // yield in block-form switch expression
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("yield returns value from a block inside a switch expression")
    void yieldInBlockSwitch() {
        int score = 85;
        String grade = switch (score / 10) {
            case 10, 9 -> "A";
            case 8 -> {
                String prefix = score >= 85 ? "B+" : "B";
                yield prefix;
            }
            case 7 -> "C";
            case 6 -> "D";
            default -> "F";
        };
        assertEquals("B+", grade);
    }

    @Test
    @DisplayName("yield can apply conditional logic before returning a value")
    void yieldWithConditionalLogic() {
        for (int input : new int[]{0, 1, 2, 3}) {
            String result = switch (input) {
                case 0 -> "zero";
                case 1 -> "one";
                default -> {
                    yield input % 2 == 0 ? "even" : "odd";
                }
            };
            switch (input) {
                case 0 -> assertEquals("zero", result);
                case 1 -> assertEquals("one", result);
                case 2 -> assertEquals("even", result);
                case 3 -> assertEquals("odd", result);
            }
        }
    }

    // ---------------------------------------------------------------------------
    // Switch expression used as a sub-expression
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Switch expression can be used directly in a method call argument")
    void switchExpressionAsArgument() {
        int value = 3;
        // switch expression as argument to assertEquals directly
        assertEquals(
            "three",
            switch (value) {
                case 1 -> "one";
                case 2 -> "two";
                case 3 -> "three";
                default -> "other";
            }
        );
    }

    @Test
    @DisplayName("Switch expression can return different numeric types via int")
    void switchExpressionNumericReturn() {
        for (int i = 1; i <= 5; i++) {
            int square = switch (i) {
                case 1 -> 1;
                case 2 -> 4;
                case 3 -> 9;
                case 4 -> 16;
                case 5 -> 25;
                default -> -1;
            };
            assertEquals(i * i, square, "square of " + i);
        }
    }

    // ---------------------------------------------------------------------------
    // Traditional switch statement (still valid) vs expression
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Switch expression and traditional switch statement produce same result")
    void expressionVsStatementConsistency() {
        String season = "WINTER";

        // expression form
        String descExpr = switch (season) {
            case "SPRING" -> "Warm";
            case "SUMMER" -> "Hot";
            case "AUTUMN" -> "Cool";
            case "WINTER" -> "Cold";
            default        -> "Unknown";
        };

        // statement form
        String descStmt;
        switch (season) {
            case "SPRING": descStmt = "Warm";  break;
            case "SUMMER": descStmt = "Hot";   break;
            case "AUTUMN": descStmt = "Cool";  break;
            case "WINTER": descStmt = "Cold";  break;
            default:       descStmt = "Unknown";
        }

        assertEquals(descStmt, descExpr);
    }

    // ---------------------------------------------------------------------------
    // Exhaustive switch expression (enum)
    // ---------------------------------------------------------------------------

    enum Direction { NORTH, SOUTH, EAST, WEST }

    @Test
    @DisplayName("Exhaustive switch expression over enum requires no default")
    void exhaustiveSwitchOverEnum() {
        Direction dir = Direction.EAST;
        int dx = switch (dir) {
            case NORTH -> 0;
            case SOUTH -> 0;
            case EAST  -> 1;
            case WEST  -> -1;
        };
        assertEquals(1, dx);
    }

    @Test
    @DisplayName("Switch expression result can be assigned and reused")
    void switchResultAssignment() {
        int priority = 2;
        String label = switch (priority) {
            case 1 -> "HIGH";
            case 2 -> "MEDIUM";
            case 3 -> "LOW";
            default -> "NONE";
        };
        // use the result in a further assertion
        assertTrue(label.length() > 0);
        assertEquals("MEDIUM", label);
    }
}
