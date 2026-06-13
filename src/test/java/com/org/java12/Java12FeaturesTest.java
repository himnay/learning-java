package com.org.java12;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Java 12 – String/Collectors/Switch Enhancements")
class Java12FeaturesTest {

    // ── String.indent & String.transform ──────────────────────────────────────

    @Test
    @DisplayName("String.indent adds n spaces to each line and ensures trailing newline")
    void stringIndent_addsSpacesToEachLine() {
        String result = "hello\nworld".indent(4);
        assertEquals("    hello\n    world\n", result);
    }

    @Test
    @DisplayName("String.indent with negative n strips leading whitespace")
    void stringIndent_negativeStripsWhitespace() {
        String indented = "    hello\n    world\n";
        String result   = indented.indent(-2);
        assertEquals("  hello\n  world\n", result);
    }

    @Test
    @DisplayName("String.transform applies function to string value")
    void stringTransform_appliesFunction() {
        String result = "hello"
                .transform(String::toUpperCase)
                .transform(s -> s + "!")
                .transform(s -> "[" + s + "]");
        assertEquals("[HELLO!]", result);
    }

    @Test
    @DisplayName("String.transform can convert to a non-String type")
    void stringTransform_convertsToInteger() {
        int length = "hello world".transform(String::length);
        assertEquals(11, length);
    }

    // ── Collectors.teeing ─────────────────────────────────────────────────────

    @Test
    @DisplayName("Collectors.teeing combines two collectors into one result")
    void teeing_combinesTwoCollectors() {
        record Stats(long count, double sum) {}
        var numbers = List.of(1, 2, 3, 4, 5);
        Stats stats = numbers.stream()
                .collect(Collectors.teeing(
                        Collectors.counting(),
                        Collectors.summingDouble(Integer::doubleValue),
                        Stats::new));
        assertEquals(5,    stats.count());
        assertEquals(15.0, stats.sum());
    }

    @Test
    @DisplayName("Collectors.teeing computes min and max simultaneously")
    void teeing_minAndMax() {
        record MinMax(int min, int max) {}
        var numbers = List.of(3, 1, 4, 1, 5, 9, 2, 6);
        MinMax result = numbers.stream()
                .collect(Collectors.teeing(
                        Collectors.minBy(Integer::compareTo),
                        Collectors.maxBy(Integer::compareTo),
                        (min, max) -> new MinMax(min.get(), max.get())));
        assertEquals(1, result.min());
        assertEquals(9, result.max());
    }

    @Test
    @DisplayName("Collectors.teeing splits into two separate lists")
    void teeing_partitionIntoTwoLists() {
        record EvenOdd(List<Integer> evens, List<Integer> odds) {}
        var numbers = List.of(1, 2, 3, 4, 5, 6);
        EvenOdd result = numbers.stream()
                .collect(Collectors.teeing(
                        Collectors.filtering(n -> n % 2 == 0, Collectors.toList()),
                        Collectors.filtering(n -> n % 2 != 0, Collectors.toList()),
                        EvenOdd::new));
        assertEquals(List.of(2, 4, 6), result.evens());
        assertEquals(List.of(1, 3, 5), result.odds());
    }

    // ── Switch Expressions (standard in Java 14, preview since 12) ────────────

    @Test
    @DisplayName("Switch expression with arrow syntax (available from Java 14)")
    void switchExpression_arrowSyntax() {
        int day = 3;
        String name = switch (day) {
            case 1 -> "Monday";
            case 2 -> "Tuesday";
            case 3 -> "Wednesday";
            case 4 -> "Thursday";
            case 5 -> "Friday";
            default -> "Weekend";
        };
        assertEquals("Wednesday", name);
    }

    @Test
    @DisplayName("Files.mismatch returns -1 for identical files, first diff position otherwise")
    void filesMismatch() throws Exception {
        var tmp = java.nio.file.Files.createTempDirectory("java12");
        var f1 = tmp.resolve("a.txt"); var f2 = tmp.resolve("b.txt");
        java.nio.file.Files.writeString(f1, "hello");
        java.nio.file.Files.writeString(f2, "hello");
        assertEquals(-1, java.nio.file.Files.mismatch(f1, f2));

        java.nio.file.Files.writeString(f2, "hXllo");
        assertEquals(1, java.nio.file.Files.mismatch(f1, f2));
    }
}
