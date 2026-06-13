package com.org.java9;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Java 9 – Optional & Private Interface Methods")
class Java9OptionalAndInterfaceTest {

    // Private interface methods (Java 9)
    interface Greeter {
        default String greet(String name) {
            return prefix() + name;
        }

        default String greetFormal(String name) {
            return prefix() + "Mr./Ms. " + name;
        }

        private String prefix() { // private method shared by defaults
            return "Hello, ";
        }

        static String staticGreet(String name) {
            return staticPrefix() + name;
        }

        private static String staticPrefix() { // private static method
            return "Hi, ";
        }
    }

    @Test
    @DisplayName("Private interface default method is shared between defaults")
    void privateInterfaceMethod_sharedBetweenDefaults() {
        Greeter g = new Greeter() {};
        assertEquals("Hello, Alice",         g.greet("Alice"));
        assertEquals("Hello, Mr./Ms. Alice", g.greetFormal("Alice"));
    }

    @Test
    @DisplayName("Private static interface method works in static context")
    void privateStaticInterfaceMethod() {
        assertEquals("Hi, Bob", Greeter.staticGreet("Bob"));
    }

    // Optional improvements
    @Test
    @DisplayName("ifPresentOrElse executes consumer when value present")
    void ifPresentOrElse_presentBranch() {
        StringBuilder sb = new StringBuilder();
        Optional.of("value").ifPresentOrElse(sb::append, () -> sb.append("empty"));
        assertEquals("value", sb.toString());
    }

    @Test
    @DisplayName("ifPresentOrElse executes runnable when empty")
    void ifPresentOrElse_emptyBranch() {
        StringBuilder sb = new StringBuilder();
        Optional.<String>empty().ifPresentOrElse(sb::append, () -> sb.append("empty"));
        assertEquals("empty", sb.toString());
    }

    @Test
    @DisplayName("Optional.or returns fallback Optional when empty")
    void or_returnsFallback() {
        Optional<String> result = Optional.<String>empty()
                .or(() -> Optional.of("fallback"));
        assertEquals("fallback", result.get());
    }

    @Test
    @DisplayName("Optional.or returns original when present")
    void or_returnsOriginalWhenPresent() {
        Optional<String> result = Optional.of("original")
                .or(() -> Optional.of("fallback"));
        assertEquals("original", result.get());
    }

    @Test
    @DisplayName("Optional.stream returns single-element stream when present")
    void optionalStream_singleElement() {
        List<String> list = Optional.of("hello")
                .stream()
                .collect(Collectors.toList());
        assertEquals(List.of("hello"), list);
    }

    @Test
    @DisplayName("Optional.stream returns empty stream when empty")
    void optionalStream_emptyWhenAbsent() {
        long count = Optional.<String>empty().stream().count();
        assertEquals(0, count);
    }

    @Test
    @DisplayName("Optional.stream enables flatMap pattern for collections of optionals")
    void optionalStream_flatMapPattern() {
        List<Optional<String>> opts = List.of(
                Optional.of("a"), Optional.empty(), Optional.of("c"));
        List<String> result = opts.stream()
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
        assertEquals(List.of("a", "c"), result);
    }
}
