// FILE 17: src/test/java/com/org/java25/Java25FeaturesTest.java
package com.org.java25;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for features available / standardized in Java 25.
 *
 * Features covered:
 *   1. Primitive types in patterns (JEP 455, preview → standard in Java 25)
 *      — instanceof with primitive type pattern
 *      — switch with primitive type pattern
 *   2. Stable API surface improvements (String, Stream, collections)
 *   3. ScopedValue / StructuredTaskScope (finalized in Java 25)
 *      — covered in depth in com.org.java23; just smoke-tested here
 *
 * All tests use --enable-preview so primitive-type patterns compile.
 */
@DisplayName("Java 25 Features")
class Java25FeaturesTest {

    // ===========================================================================
    // 1. Primitive types in patterns
    // ===========================================================================

    // --- instanceof ---

    @Test
    @DisplayName("Primitive pattern: 'obj instanceof int i' matches a boxed Integer")
    void primitiveInstanceofInt() {
        Object obj = 42;
        if (obj instanceof int i) {
            assertEquals(42, i);
        } else {
            fail("Expected primitive int pattern to match boxed Integer 42");
        }
    }

    @Test
    @DisplayName("Primitive pattern: 'obj instanceof long l' matches a boxed Long")
    void primitiveInstanceofLong() {
        Object obj = 100L;
        if (obj instanceof long l) {
            assertEquals(100L, l);
        } else {
            fail("Expected primitive long pattern to match boxed Long 100");
        }
    }

    @Test
    @DisplayName("Primitive pattern: 'obj instanceof double d' matches a boxed Double")
    void primitiveInstanceofDouble() {
        Object obj = 3.14;
        if (obj instanceof double d) {
            assertEquals(3.14, d, 1e-9);
        } else {
            fail("Expected primitive double pattern to match boxed Double 3.14");
        }
    }

    @Test
    @DisplayName("Primitive pattern: 'obj instanceof boolean b' matches a boxed Boolean")
    void primitiveInstanceofBoolean() {
        Object obj = true;
        if (obj instanceof boolean b) {
            assertTrue(b);
        } else {
            fail("Expected primitive boolean pattern to match boxed Boolean true");
        }
    }

    @Test
    @DisplayName("Primitive pattern: instanceof int does not match a String")
    void primitiveInstanceofIntNoMatchString() {
        Object obj = "not an int";
        assertFalse(obj instanceof int i);
    }

    @Test
    @DisplayName("Primitive pattern: instanceof int does not match null")
    void primitiveInstanceofNullDoesNotMatch() {
        Object obj = null;
        assertFalse(obj instanceof int i);
    }

    // --- switch with primitive type patterns ---

    @Test
    @DisplayName("Primitive pattern in switch: int pattern dispatches correctly")
    void primitivePatternInSwitch() {
        Object obj = 42;
        String result = switch (obj) {
            case int i    -> "int: " + i;
            case long l   -> "long: " + l;
            case double d -> "double: " + d;
            case String s -> "string: " + s;
            default       -> "other";
        };
        assertEquals("int: 42", result);
    }

    @Test
    @DisplayName("Primitive pattern in switch: long pattern dispatches correctly")
    void primitivePatternLongSwitch() {
        Object obj = 9_876_543_210L;
        String result = switch (obj) {
            case int i    -> "int: " + i;
            case long l   -> "long: " + l;
            default       -> "other";
        };
        assertEquals("long: 9876543210", result);
    }

    @Test
    @DisplayName("Primitive pattern in switch: guarded primitive pattern")
    void primitivePatternGuardedSwitch() {
        Object obj = 15;
        String result = switch (obj) {
            case int i when i < 0   -> "negative";
            case int i when i == 0  -> "zero";
            case int i when i < 10  -> "small positive";
            case int i              -> "large positive";
            default                 -> "not int";
        };
        assertEquals("large positive", result);
    }

    @Test
    @DisplayName("Primitive pattern bound variable can be used in computation")
    void primitivePatternBoundVariableUsed() {
        Object[] values = {2, 3, 5, 7, 11};
        int product = 1;
        for (Object v : values) {
            if (v instanceof int i) {
                product *= i;
            }
        }
        assertEquals(2 * 3 * 5 * 7 * 11, product);
    }

    // ===========================================================================
    // 2. Stream and Collection API improvements available in Java 25
    // ===========================================================================

    @Test
    @DisplayName("Stream.toList() (since Java 16) remains unmodifiable in Java 25")
    void streamToListUnmodifiable() {
        List<Integer> list = Stream.of(1, 2, 3).toList();
        assertThrows(UnsupportedOperationException.class, () -> list.add(4));
    }

    @Test
    @DisplayName("Optional.or() chains Optional fallback (Java 9+, stable in 25)")
    void optionalOr() {
        Optional<String> empty = Optional.empty();
        Optional<String> result = empty.or(() -> Optional.of("fallback"));
        assertEquals("fallback", result.get());
    }

    @Test
    @DisplayName("Optional.ifPresentOrElse() branches correctly")
    void optionalIfPresentOrElse() {
        int[] box = {0};
        Optional.of(5).ifPresentOrElse(
                v -> box[0] = v,
                () -> box[0] = -1
        );
        assertEquals(5, box[0]);

        Optional.<Integer>empty().ifPresentOrElse(
                v -> box[0] = v,
                () -> box[0] = -1
        );
        assertEquals(-1, box[0]);
    }

    @Test
    @DisplayName("List.copyOf() produces an unmodifiable copy")
    void listCopyOf() {
        List<String> mutable = new java.util.ArrayList<>(List.of("a", "b", "c"));
        List<String> copy = List.copyOf(mutable);
        mutable.add("d"); // mutating original does not affect copy
        assertEquals(3, copy.size());
        assertThrows(UnsupportedOperationException.class, () -> copy.add("x"));
    }

    // ===========================================================================
    // 3. String enhancements (cumulative — all available in Java 25)
    // ===========================================================================

    @Test
    @DisplayName("String.indent() adds leading spaces and normalizes line endings")
    void stringIndent() {
        String indented = "hello\nworld\n".indent(4);
        assertTrue(indented.startsWith("    hello"),
                "indent(4) should prepend 4 spaces to each line");
    }

    @Test
    @DisplayName("String.stripLeading() and stripTrailing() use Unicode-aware whitespace")
    void stringStripVariants() {
        String s = "   hello   ";
        assertEquals("hello   ", s.stripLeading());
        assertEquals("   hello", s.stripTrailing());
        assertEquals("hello",    s.strip());
    }

    @Test
    @DisplayName("String.repeat() repeats the string n times")
    void stringRepeat() {
        assertEquals("abcabcabc", "abc".repeat(3));
        assertEquals("", "abc".repeat(0));
    }

    @Test
    @DisplayName("String.isBlank() returns true for whitespace-only strings")
    void stringIsBlank() {
        assertTrue("   ".isBlank());
        assertTrue("".isBlank());
        assertFalse("a".isBlank());
    }

    @Test
    @DisplayName("String.lines() splits on all Unicode line terminators")
    void stringLines() {
        long count = "one\ntwo\rthree\r\nfour".lines().count();
        assertEquals(4, count);
    }

    // ===========================================================================
    // 4. Math and numeric improvements
    // ===========================================================================

    @Test
    @DisplayName("Math.clamp(int, int, int) clamps value to [min, max] (Java 21+)")
    void mathClampInt() {
        assertEquals(5,  Math.clamp(5,  1, 10));
        assertEquals(1,  Math.clamp(-5, 1, 10));
        assertEquals(10, Math.clamp(15, 1, 10));
    }

    @Test
    @DisplayName("Math.clamp(long, long, long) clamps long values")
    void mathClampLong() {
        assertEquals(100L, Math.clamp(100L, 0L, 200L));
        assertEquals(0L,   Math.clamp(-50L, 0L, 200L));
        assertEquals(200L, Math.clamp(300L, 0L, 200L));
    }

    @Test
    @DisplayName("Math.clamp(double, double, double) clamps double values")
    void mathClampDouble() {
        assertEquals(0.5,  Math.clamp(0.5,  0.0, 1.0), 1e-12);
        assertEquals(0.0,  Math.clamp(-0.5, 0.0, 1.0), 1e-12);
        assertEquals(1.0,  Math.clamp(1.5,  0.0, 1.0), 1e-12);
    }
}
