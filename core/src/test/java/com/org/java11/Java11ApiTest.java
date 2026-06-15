package com.org.java11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Java 11 – Files, Path.of, Predicate.not, Optional.isEmpty")
class Java11ApiTest {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("Files.readString reads file content in one call")
    void filesReadString_readsContent() throws IOException {
        Path file = tempDir.resolve("test.txt");
        Files.writeString(file, "Hello, Java 11!");
        String content = Files.readString(file);
        assertEquals("Hello, Java 11!", content);
    }

    @Test
    @DisplayName("Files.writeString writes string content to file")
    void filesWriteString_writesAndReads() throws IOException {
        Path file = tempDir.resolve("output.txt");
        Files.writeString(file, "line1\nline2\n");
        List<String> lines = Files.readAllLines(file);
        assertEquals(2, lines.size());
        assertEquals("line1", lines.get(0));
    }

    @Test
    @DisplayName("Path.of constructs path from string parts")
    void pathOf_constructsPath() {
        Path path = Path.of("/usr", "local", "bin");
        assertEquals("/usr/local/bin", path.toString());
    }

    @Test
    @DisplayName("Predicate.not negates a predicate")
    void predicateNot_negates() {
        List<String> withBlanks = List.of("a", "  ", "b", "", "c");
        List<String> nonBlanks = withBlanks.stream()
                .filter(Predicate.not(String::isBlank))
                .collect(Collectors.toList());
        assertEquals(List.of("a", "b", "c"), nonBlanks);
    }

    @Test
    @DisplayName("Optional.isEmpty returns true for empty Optional")
    void optionalIsEmpty_trueWhenEmpty() {
        assertTrue(java.util.Optional.empty().isEmpty());
        assertFalse(java.util.Optional.of("value").isEmpty());
    }

    @Test
    @DisplayName("Collection.toArray(IntFunction) produces typed array")
    void collectionToArray_withGenerator() {
        List<String> list = List.of("a", "b", "c");
        String[] arr = list.toArray(String[]::new);
        assertArrayEquals(new String[]{"a", "b", "c"}, arr);
    }

    @Test
    @DisplayName("Files.writeString and readString round-trip with UTF-8")
    void filesReadWriteString_utf8RoundTrip() throws IOException {
        Path file = tempDir.resolve("utf8.txt");
        String content = "こんにちは Java 11 ✓";
        Files.writeString(file, content);
        assertEquals(content, Files.readString(file));
    }
}
