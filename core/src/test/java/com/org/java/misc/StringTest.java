package com.org.java.misc;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StringTest {

    @Test
    void join_concatenatesWithDelimiter() {
        String result = String.join(":", "foobar", "foo", "bar");
        assertEquals("foobar:foo:bar", result);
    }

    @Test
    void chars_extractsDistinctSortedCharacters() {
        String result = "foobar:foo:bar".chars()
                .distinct()
                .mapToObj(c -> String.valueOf((char) c))
                .sorted()
                .collect(Collectors.joining());
        assertEquals(":abfor", result);
    }

    @Test
    void patternPredicate_countsGmailAddresses() {
        long count = Stream.of("bob@gmail.com", "alice@hotmail.com")
                .filter(Pattern.compile(".*@gmail\\.com").asPredicate())
                .count();
        assertEquals(1, count);
    }

    @Test
    void patternSplitAsStream_filtersAndJoins() {
        String result = Pattern.compile(":").splitAsStream("foobar:foo:bar")
                .filter(s -> s.contains("bar"))
                .sorted()
                .collect(Collectors.joining(":"));
        assertEquals("bar:foobar", result);
    }

    @Test
    void join_emptyParts_producesEmptySegments() {
        String result = String.join("-", "a", "", "b");
        assertEquals("a--b", result);
    }
}
