package com.org.java11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Java 11 – String Enhancements")
class Java11StringTest {

    @Test
    @DisplayName("isBlank returns true for empty and whitespace-only strings")
    void isBlank_whitespaceAndEmpty() {
        assertTrue("".isBlank());
        assertTrue("   ".isBlank());
        assertTrue("\t\n".isBlank());
        assertFalse("hello".isBlank());
        assertFalse("  hi  ".isBlank());
    }

    @Test
    @DisplayName("strip removes leading and trailing Unicode whitespace")
    void strip_removesUnicodeWhitespace() {
        assertEquals("hello", "  hello  ".strip());
        assertEquals("hello", "\t hello \n".strip());
        assertEquals("hello world", "  hello world  ".strip());
    }

    @Test
    @DisplayName("strip vs trim: strip handles Unicode whitespace chars")
    void stripLeadingAndTrailing() {
        assertEquals("hello  ", "  hello  ".stripLeading());
        assertEquals("  hello", "  hello  ".stripTrailing());
    }

    @Test
    @DisplayName("lines streams each line without the terminator")
    void lines_streamsEachLine() {
        List<String> lines = "line1\nline2\nline3".lines().collect(Collectors.toList());
        assertEquals(3, lines.size());
        assertEquals("line1", lines.get(0));
        assertEquals("line3", lines.get(2));
    }

    @Test
    @DisplayName("lines handles Windows-style line endings")
    void lines_windowsEndings() {
        long count = "a\r\nb\r\nc".lines().count();
        assertEquals(3, count);
    }

    @Test
    @DisplayName("repeat duplicates the string n times")
    void repeat_duplicatesString() {
        assertEquals("abcabcabc", "abc".repeat(3));
        assertEquals("",          "abc".repeat(0));
        assertEquals("x",         "x".repeat(1));
    }

    @Test
    @DisplayName("repeat with zero returns empty string")
    void repeat_zeroTimesIsEmpty() {
        assertEquals("", "anything".repeat(0));
    }
}
