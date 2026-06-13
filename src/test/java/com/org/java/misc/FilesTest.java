package com.org.java.misc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FilesTest {

    @TempDir
    Path tempDir;

    private Path writeFile(String name, String... lines) throws IOException {
        Path file = tempDir.resolve(name);
        Files.write(file, List.of(lines));
        return file;
    }

    @Test
    void newBufferedReader_readsLines() throws IOException {
        Path file = writeFile("sample.txt", "hello", "world");
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            assertEquals("hello", reader.readLine());
            assertEquals("world", reader.readLine());
        }
    }

    @Test
    void newBufferedWriter_writesContent() throws IOException {
        Path file = tempDir.resolve("output.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            writer.write("Hello World");
        }
        assertEquals("Hello World", Files.readString(file));
    }

    @Test
    void filesLines_streamsEachLine() throws IOException {
        Path file = writeFile("lines.txt", "alpha", "beta", "gamma");
        try (Stream<String> lines = Files.lines(file)) {
            long count = lines.count();
            assertEquals(3, count);
        }
    }

    @Test
    void filesLines_filtersByContent() throws IOException {
        Path file = writeFile("script.txt", "print('a')", "var x = 1", "print('b')");
        try (Stream<String> lines = Files.lines(file)) {
            List<String> printLines = lines
                    .filter(l -> l.contains("print"))
                    .collect(Collectors.toList());
            assertEquals(2, printLines.size());
        }
    }

    @Test
    void readerLines_countsPrintOccurrences() throws IOException {
        Path file = writeFile("nashorn.js", "print('a');", "var x = 1;", "print('b');");
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            long count = reader.lines().filter(l -> l.contains("print")).count();
            assertEquals(2, count);
        }
    }

    @Test
    void readAllLinesAndWrite_appendsLine() throws IOException {
        Path src = writeFile("original.txt", "line1", "line2");
        List<String> lines = Files.readAllLines(src);
        lines.add("line3");
        Path dest = tempDir.resolve("modified.txt");
        Files.write(dest, lines);
        List<String> result = Files.readAllLines(dest);
        assertEquals(List.of("line1", "line2", "line3"), result);
    }

    @Test
    void filesWalk_findsFilesUpToDepth() throws IOException {
        Files.createDirectories(tempDir.resolve("sub"));
        writeFile("a.js", "print('a')");
        writeFile("sub/b.js", "print('b')");
        try (Stream<Path> walk = Files.walk(tempDir, 5)) {
            List<String> jsFiles = walk
                    .map(String::valueOf)
                    .filter(p -> p.endsWith(".js"))
                    .collect(Collectors.toList());
            assertEquals(2, jsFiles.size());
        }
    }

    @Test
    void filesFind_findsMatchingFiles() throws IOException {
        writeFile("match.txt", "content");
        writeFile("other.log", "content");
        try (Stream<Path> found = Files.find(tempDir, 1,
                (p, attr) -> p.toString().endsWith(".txt"))) {
            long count = found.count();
            assertEquals(1, count);
        }
    }

    @Test
    void filesList_listsDirectoryEntries() throws IOException {
        writeFile("x.txt", "x");
        writeFile("y.txt", "y");
        try (Stream<Path> list = Files.list(tempDir)) {
            long count = list.count();
            assertEquals(2, count);
        }
    }
}
