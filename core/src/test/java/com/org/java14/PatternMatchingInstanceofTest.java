// FILE 2: src/test/java/com/org/java14/PatternMatchingInstanceofTest.java
package com.org.java14;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pattern Matching instanceof (Java 14 preview → Java 16 standard)")
class PatternMatchingInstanceofTest {

    // ---------------------------------------------------------------------------
    // Basic pattern variable binding
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("instanceof pattern variable is bound when type matches")
    void basicPatternBinding() {
        Object obj = "Hello, Java 14!";
        if (obj instanceof String s) {
            assertEquals(15, s.length());
            assertTrue(s.startsWith("Hello"));
        } else {
            fail("Expected obj to be a String");
        }
    }

    @Test
    @DisplayName("instanceof pattern does not bind when type does not match")
    void patternNotBoundOnMismatch() {
        Object obj = 42;
        boolean matched = obj instanceof String s;
        assertFalse(matched);
    }

    @Test
    @DisplayName("Pattern variable is usable in the same boolean expression via &&")
    void patternVariableUsableInSameAnd() {
        Object obj = "preview";
        // pattern variable 's' is in scope to the right of &&
        if (obj instanceof String s && s.length() > 3) {
            assertTrue(s.contains("view"));
        } else {
            fail("Should have matched and satisfied the guard");
        }
    }

    // ---------------------------------------------------------------------------
    // Combining with &&
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Pattern combined with && allows immediate use of bound variable")
    void combinedWithAnd() {
        Object obj = 100;
        if (obj instanceof Integer i && i > 50) {
            assertTrue(i > 50);
            assertEquals(100, i);
        } else {
            fail("Expected to enter the if-branch");
        }
    }

    @Test
    @DisplayName("Pattern with && short-circuits when guard is false")
    void andShortCircuitOnFalseGuard() {
        Object obj = 10;
        boolean entered = false;
        if (obj instanceof Integer i && i > 50) {
            entered = true;
        }
        assertFalse(entered, "Should not enter branch when guard i > 50 is false");
    }

    // ---------------------------------------------------------------------------
    // Negation
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Negated instanceof pattern takes the else branch")
    void negationWithElse() {
        Object obj = 3.14;
        String description;
        if (!(obj instanceof String s)) {
            description = "not a string";
        } else {
            description = s.toUpperCase();
        }
        assertEquals("not a string", description);
    }

    // ---------------------------------------------------------------------------
    // Polymorphic dispatch without casting
    // ---------------------------------------------------------------------------

    static double area(Object shape) {
        if (shape instanceof Circle c) {
            return Math.PI * c.radius() * c.radius();
        } else if (shape instanceof Rectangle r) {
            return r.width() * r.height();
        }
        return 0;
    }

    record Circle(double radius) {}
    record Rectangle(double width, double height) {}

    @Test
    @DisplayName("Pattern matching replaces explicit casts in polymorphic dispatch")
    void polymorphicDispatch() {
        assertEquals(Math.PI * 4, area(new Circle(2.0)), 1e-9);
        assertEquals(12.0, area(new Rectangle(3.0, 4.0)), 1e-9);
        assertEquals(0.0, area("unknown"), 1e-9);
    }

    // ---------------------------------------------------------------------------
    // Null handling
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("instanceof pattern returns false for null (no NullPointerException)")
    void nullDoesNotMatch() {
        Object obj = null;
        assertFalse(obj instanceof String s);
    }

    // ---------------------------------------------------------------------------
    // Nested / chained patterns
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Multiple chained instanceof checks each bind their own variable")
    void chainedInstanceofChecks() {
        Object[] objects = {"text", 42, 3.14, true};
        int stringCount = 0, intCount = 0;
        for (Object o : objects) {
            if (o instanceof String s) {
                assertFalse(s.isEmpty());
                stringCount++;
            } else if (o instanceof Integer i) {
                assertTrue(i > 0);
                intCount++;
            }
        }
        assertEquals(1, stringCount);
        assertEquals(1, intCount);
    }

    // ---------------------------------------------------------------------------
    // Pattern variable scope
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Pattern variable scope is limited to the enclosing block")
    void patternVariableScopeIsLimited() {
        Object obj = "scoped";
        boolean result = obj instanceof String s && !s.isEmpty();
        assertTrue(result);
        // 's' is not accessible here — compile-time scoping is enforced by the JVM
    }
}
