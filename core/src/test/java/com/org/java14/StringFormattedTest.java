// FILE 3: src/test/java/com/org/java14/StringFormattedTest.java
package com.org.java14;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("String.formatted() and Helpful NPE (Java 14)")
class StringFormattedTest {

    // ---------------------------------------------------------------------------
    // String.formatted() — instance equivalent of String.format()
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("String.formatted() interpolates a single string argument")
    void formattedSingleStringArg() {
        String result = "Hello, %s!".formatted("Java 14");
        assertEquals("Hello, Java 14!", result);
    }

    @Test
    @DisplayName("String.formatted() interpolates multiple arguments")
    void formattedMultipleArgs() {
        String result = "%s is %d years old".formatted("Alice", 30);
        assertEquals("Alice is 30 years old", result);
    }

    @Test
    @DisplayName("String.formatted() supports numeric format specifiers")
    void formattedNumericSpecifiers() {
        String result = "Pi is approximately %.2f".formatted(Math.PI);
        assertEquals("Pi is approximately 3.14", result);
    }

    @Test
    @DisplayName("String.formatted() pads integers with width specifier")
    void formattedIntegerPadding() {
        String result = "%05d".formatted(42);
        assertEquals("00042", result);
    }

    @Test
    @DisplayName("String.formatted() handles %n as platform-neutral newline")
    void formattedNewline() {
        String result = "line1%nline2".formatted();
        String expected = "line1" + System.lineSeparator() + "line2";
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("String.formatted() with hex format specifier")
    void formattedHexSpecifier() {
        String result = "0x%X".formatted(255);
        assertEquals("0xFF", result);
    }

    @Test
    @DisplayName("String.formatted() produces the same result as String.format()")
    void formattedEquivalentToStaticFormat() {
        String template = "(%d, %d)";
        int x = 7, y = 13;
        assertEquals(String.format(template, x, y), template.formatted(x, y));
    }

    @Test
    @DisplayName("String.formatted() can be chained with other String methods")
    void formattedChainedWithOtherMethods() {
        String result = "  Hello, %s!  ".formatted("World").trim();
        assertEquals("Hello, World!", result);
    }

    @Test
    @DisplayName("String.formatted() with boolean argument")
    void formattedBooleanArg() {
        String result = "Preview enabled: %b".formatted(true);
        assertEquals("Preview enabled: true", result);
    }

    @Test
    @DisplayName("String.formatted() with character argument")
    void formattedCharArg() {
        String result = "Grade: %c".formatted('A');
        assertEquals("Grade: A", result);
    }

    @Test
    @DisplayName("String.formatted() with multiple types produces correct output")
    void formattedMixedTypes() {
        String result = "Name: %s, Score: %d, Pass: %b".formatted("Bob", 95, true);
        assertEquals("Name: Bob, Score: 95, Pass: true", result);
    }

    // ---------------------------------------------------------------------------
    // Helpful NullPointerException messages (JEP 358): added in Java 14 behind
    // -XX:+ShowCodeDetailsInExceptionMessages, switched on by default in Java 15.
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("NullPointerException is thrown when dereferencing null")
    void helpfulNpeIsThrown() {
        String s = null;
        NullPointerException npe = assertThrows(NullPointerException.class, () -> {
            int ignored = s.length(); // triggers helpful NPE
        });
        // here we only need the exception; the next test checks its message
        assertNotNull(npe);
    }

    @Test
    @DisplayName("NullPointerException carries a descriptive message in Java 14+")
    void helpfulNpeHasMessage() {
        String[] array = null;
        NullPointerException npe = assertThrows(NullPointerException.class, () -> {
            // This would produce a message like:
            // "Cannot load from object array because 'array' is null"
            Object ignored = array[0];
        });
        // The message names the failed action. The variable name ("array") only appears when the
        // class was compiled with -g, so assert on the action part.
        assertNotNull(npe.getMessage());
        assertTrue(npe.getMessage().startsWith("Cannot load from object array"), npe.getMessage());
    }

    // ---------------------------------------------------------------------------
    // String.formatted() edge cases
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("String.formatted() with no placeholders returns unchanged string")
    void formattedNoPlaceholders() {
        String result = "no placeholders here".formatted();
        assertEquals("no placeholders here", result);
    }

    @Test
    @DisplayName("String.formatted() throws MissingFormatArgumentException on missing arg")
    void formattedMissingArgThrows() {
        assertThrows(java.util.MissingFormatArgumentException.class,
                () -> "%s and %s".formatted("only one"));
    }
}
