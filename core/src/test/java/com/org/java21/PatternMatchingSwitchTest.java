// FILE 12: src/test/java/com/org/java21/PatternMatchingSwitchTest.java
package com.org.java21;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pattern Matching for Switch (Java 21)")
class PatternMatchingSwitchTest {

    // ---------------------------------------------------------------------------
    // Sealed type hierarchy for exhaustiveness tests
    // ---------------------------------------------------------------------------

    sealed interface Expr permits Num, Add, Mul, Neg {}
    record Num(double value)           implements Expr {}
    record Add(Expr left, Expr right)  implements Expr {}
    record Mul(Expr left, Expr right)  implements Expr {}
    record Neg(Expr expr)              implements Expr {}

    sealed interface Vehicle permits Car, Truck, Motorcycle {}
    record Car(String model, int seats)   implements Vehicle {}
    record Truck(double payload)          implements Vehicle {}
    record Motorcycle(boolean hasSidecar) implements Vehicle {}

    // ---------------------------------------------------------------------------
    // Type patterns
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Type pattern in switch selects the correct arm based on runtime type")
    void typePatternSwitch() {
        Object obj = "hello";
        String result = switch (obj) {
            case Integer i -> "int: " + i;
            case String s  -> "str: " + s;
            case Double d  -> "dbl: " + d;
            default        -> "other";
        };
        assertEquals("str: hello", result);
    }

    @Test
    @DisplayName("Type pattern switch handles multiple types correctly")
    void typePatternSwitchMultipleTypes() {
        Object[] inputs = {42, 3.14, "text", true};
        String[] expected = {"int: 42", "dbl: 3.14", "str: text", "other"};
        for (int i = 0; i < inputs.length; i++) {
            Object obj = inputs[i];
            String result = switch (obj) {
                case Integer n -> "int: " + n;
                case Double  d -> "dbl: " + d;
                case String  s -> "str: " + s;
                default        -> "other";
            };
            assertEquals(expected[i], result);
        }
    }

    // ---------------------------------------------------------------------------
    // Guarded patterns (when clause)
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Guarded pattern matches only when guard is true")
    void guardedPattern() {
        Object obj = 42;
        String result = switch (obj) {
            case Integer i when i < 0  -> "negative";
            case Integer i when i == 0 -> "zero";
            case Integer i when i > 0  -> "positive";
            default                    -> "not int";
        };
        assertEquals("positive", result);
    }

    @Test
    @DisplayName("Guarded pattern falls through to next arm when guard is false")
    void guardedPatternFallThrough() {
        Object obj = -5;
        String result = switch (obj) {
            case Integer i when i > 0 -> "positive";
            case Integer i            -> "non-positive: " + i;
            default                   -> "not int";
        };
        assertEquals("non-positive: -5", result);
    }

    @Test
    @DisplayName("Guarded pattern with string length check")
    void guardedPatternStringLength() {
        Object obj = "Java 21";
        String result = switch (obj) {
            case String s when s.length() > 10 -> "long string";
            case String s when s.startsWith("Java") -> "java string: " + s;
            case String s -> "other string";
            default -> "not string";
        };
        assertEquals("java string: Java 21", result);
    }

    // ---------------------------------------------------------------------------
    // Null handling in switch
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Switch with explicit null case handles null without NullPointerException")
    void nullCaseInSwitch() {
        Object obj = null;
        String result = switch (obj) {
            case null    -> "it is null";
            case String s -> "string";
            default      -> "other";
        };
        assertEquals("it is null", result);
    }

    @Test
    @DisplayName("Switch without null case throws NullPointerException for null input")
    void noNullCaseThrowsNpe() {
        Object obj = null;
        assertThrows(NullPointerException.class, () -> {
            String ignored = switch (obj) {
                case String s -> "string";
                case Integer i -> "int";
                default -> "other";
            };
        });
    }

    @Test
    @DisplayName("Null and default can be combined in one case label")
    void nullAndDefaultCombined() {
        Object obj = null;
        String result = switch (obj) {
            case String s -> "string: " + s;
            case null, default -> "null or other";
        };
        assertEquals("null or other", result);

        obj = 42;
        result = switch (obj) {
            case String s -> "string: " + s;
            case null, default -> "null or other";
        };
        assertEquals("null or other", result);
    }

    // ---------------------------------------------------------------------------
    // Exhaustiveness with sealed types
    // ---------------------------------------------------------------------------

    static double evaluate(Expr expr) {
        return switch (expr) {
            case Num(double v)        -> v;
            case Add(Expr l, Expr r)  -> evaluate(l) + evaluate(r);
            case Mul(Expr l, Expr r)  -> evaluate(l) * evaluate(r);
            case Neg(Expr e)          -> -evaluate(e);
        };
    }

    @Test
    @DisplayName("Exhaustive switch over sealed Expr computes numeric values")
    void exhaustiveSealedSwitch() {
        Expr two   = new Num(2);
        Expr three = new Num(3);
        // (2 + 3) * -(2)
        Expr expr  = new Mul(new Add(two, three), new Neg(new Num(2)));
        assertEquals(-10.0, evaluate(expr), 1e-9);
    }

    @Test
    @DisplayName("Num evaluates to its literal value")
    void numEvaluatesCorrectly() {
        assertEquals(42.0, evaluate(new Num(42)), 1e-9);
    }

    @Test
    @DisplayName("Neg negates the inner expression")
    void negNegatesExpression() {
        assertEquals(-7.0, evaluate(new Neg(new Num(7))), 1e-9);
    }

    // ---------------------------------------------------------------------------
    // Vehicle dispatch — sealed types with pattern matching
    // ---------------------------------------------------------------------------

    static String describe(Vehicle v) {
        return switch (v) {
            case Car(String m, int s) when s > 5   -> m + " (minivan, " + s + " seats)";
            case Car(String m, int s)               -> m + " (" + s + " seats)";
            case Truck(double p) when p > 10.0      -> "heavy truck (" + p + "t)";
            case Truck(double p)                    -> "truck (" + p + "t)";
            case Motorcycle(boolean sc) when sc     -> "motorcycle with sidecar";
            case Motorcycle(boolean sc)             -> "motorcycle";
        };
    }

    @Test
    @DisplayName("Pattern matching switch correctly classifies a minivan")
    void vehicleMinivan() {
        assertEquals("Sprinter (minivan, 8 seats)", describe(new Car("Sprinter", 8)));
    }

    @Test
    @DisplayName("Pattern matching switch correctly classifies a regular car")
    void vehicleRegularCar() {
        assertEquals("Civic (5 seats)", describe(new Car("Civic", 5)));
    }

    @Test
    @DisplayName("Pattern matching switch correctly classifies a heavy truck")
    void vehicleHeavyTruck() {
        assertEquals("heavy truck (20.0t)", describe(new Truck(20.0)));
    }

    @Test
    @DisplayName("Pattern matching switch correctly classifies motorcycle with sidecar")
    void vehicleMotorcycleWithSidecar() {
        assertEquals("motorcycle with sidecar", describe(new Motorcycle(true)));
    }

    // ---------------------------------------------------------------------------
    // Dominance — more specific pattern before less specific
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("More-specific guarded pattern precedes general type pattern in switch")
    void patternDominanceOrder() {
        Object obj = 100;
        String result = switch (obj) {
            case Integer i when i > 50 -> "big int";
            case Integer i             -> "small int";
            default                    -> "other";
        };
        assertEquals("big int", result);

        obj = 10;
        result = switch (obj) {
            case Integer i when i > 50 -> "big int";
            case Integer i             -> "small int";
            default                    -> "other";
        };
        assertEquals("small int", result);
    }
}
