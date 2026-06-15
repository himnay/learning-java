// FILE 4: src/test/java/com/org/java15/TextBlocksTest.java
package com.org.java15;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Text Blocks (Java 15)")
class TextBlocksTest {

    // ---------------------------------------------------------------------------
    // Basic text block
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Text block trims common leading whitespace (incidental indentation)")
    void basicTextBlockIndentStripping() {
        String json = """
                {
                    "key": "value"
                }
                """;
        // The closing delimiter is on its own line at column 16 (same as the open),
        // so all 16-character leading whitespace is stripped.
        assertTrue(json.startsWith("{"), "Should start with '{'");
        assertTrue(json.contains("    \"key\": \"value\""), "Inner indent should be preserved relative to outer");
        assertTrue(json.endsWith("}\n"), "Should end with '\\n' after the closing brace");
    }

    @Test
    @DisplayName("Text block content equals an equivalent regular String literal")
    void textBlockEqualsRegularString() {
        String textBlock = """
                Hello,
                World!
                """;
        String regular = "Hello,\nWorld!\n";
        assertEquals(regular, textBlock);
    }

    // ---------------------------------------------------------------------------
    // Trailing newline
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Closing delimiter on its own line adds a trailing newline")
    void closingDelimiterOnOwnLineAddsNewline() {
        String s = """
                line
                """;
        assertTrue(s.endsWith("\n"), "Must end with newline when closing \"\"\" is on its own line");
    }

    @Test
    @DisplayName("Closing delimiter on the last content line suppresses trailing newline")
    void closingDelimiterOnLastLineNoTrailingNewline() {
        String s = """
                no newline at end""";
        assertFalse(s.endsWith("\n"), "No trailing newline when closing delimiter is inline");
        assertEquals("no newline at end", s);
    }

    // ---------------------------------------------------------------------------
    // Line-continuation escape  \
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Backslash line continuation joins two lines into one logical line")
    void lineContinuationEscape() {
        String s = """
                First \
                Second""";
        assertEquals("First Second", s);
    }

    @Test
    @DisplayName("Multiple backslash continuations produce a single concatenated line")
    void multipleLineContinuations() {
        String s = """
                a \
                b \
                c""";
        assertEquals("a b c", s);
    }

    // ---------------------------------------------------------------------------
    // Explicit trailing space  \s
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("\\s preserves trailing spaces that would otherwise be stripped")
    void explicitTrailingSpaceEscape() {
        String s = """
                line1   \s
                line2""";
        // "line1   \s" — the \s acts as a space, so there are 4 trailing spaces total,
        // but the important thing: the line ends with a space character.
        String firstLine = s.lines().findFirst().orElse("");
        assertTrue(firstLine.endsWith(" "), "\\s should preserve at least one trailing space");
    }

    // ---------------------------------------------------------------------------
    // stripIndent() and translateEscapes()
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("stripIndent() removes common leading whitespace from a regular string")
    void stripIndent() {
        // stripIndent() computes the common prefix as the minimum indentation across
        // all non-empty lines. A trailing newline introduces an empty last line whose
        // indent is 0, so we must NOT include a trailing newline in the input.
        String indented = "    line1\n    line2\n    line3";
        String stripped = indented.stripIndent();
        assertEquals("line1\nline2\nline3", stripped);
    }

    @Test
    @DisplayName("translateEscapes() converts \\n literal to actual newline character")
    void translateEscapes() {
        String withLiteralEscape = "Hello\\nWorld";
        // Before translation: contains the two-character sequence backslash + n
        assertEquals(12, withLiteralEscape.length());
        String translated = withLiteralEscape.translateEscapes();
        // After translation: contains actual newline
        assertEquals("Hello\nWorld", translated);
        assertEquals(11, translated.length());
    }

    @Test
    @DisplayName("translateEscapes() handles \\t, \\\\, and \\\" sequences")
    void translateEscapesMultiple() {
        String raw = "tab:\\t backslash:\\\\ quote:\\\"";
        String translated = raw.translateEscapes();
        assertTrue(translated.contains("\t"), "Should contain actual tab");
        assertTrue(translated.contains("\\"), "Should contain actual backslash");
        assertTrue(translated.contains("\""), "Should contain actual quote");
    }

    // ---------------------------------------------------------------------------
    // Real-world use-cases
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Text block preserves internal indentation relative to leftmost line")
    void relativeIndentationPreserved() {
        String html = """
                <html>
                    <body>
                        <p>Hello</p>
                    </body>
                </html>
                """;
        String[] lines = html.split("\n");
        assertEquals("<html>", lines[0]);
        assertEquals("    <body>", lines[1]);
        assertEquals("        <p>Hello</p>", lines[2]);
        assertEquals("    </body>", lines[3]);
        assertEquals("</html>", lines[4]);
    }

    @Test
    @DisplayName("Text block formatted() interpolates values like String.format()")
    void textBlockWithFormatted() {
        String name = "Alice";
        int age = 30;
        String json = """
                {"name": "%s", "age": %d}
                """.formatted(name, age);
        assertEquals("{\"name\": \"Alice\", \"age\": 30}\n", json);
    }

    @Test
    @DisplayName("Text block can represent multi-line SQL without manual concatenation")
    void textBlockAsSql() {
        String sql = """
                SELECT id, name
                  FROM users
                 WHERE active = true
                 ORDER BY name
                """;
        assertTrue(sql.contains("SELECT id, name"));
        assertTrue(sql.contains("FROM users"));
        assertTrue(sql.contains("WHERE active = true"));
        assertTrue(sql.contains("ORDER BY name"));
        // No leading whitespace on first line after indentation stripping
        assertTrue(sql.startsWith("SELECT"));
    }

    @Test
    @DisplayName("Lines in text block count correctly")
    void textBlockLineCount() {
        String block = """
                one
                two
                three
                """;
        long count = block.lines().count();
        assertEquals(3, count);
    }
}
