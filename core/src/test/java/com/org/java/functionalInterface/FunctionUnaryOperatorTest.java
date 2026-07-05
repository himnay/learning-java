package com.org.java.functionalInterface;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.UnaryOperator;

import static org.junit.jupiter.api.Assertions.*;

class FunctionUnaryOperatorTest {

    @Test
    @DisplayName("A UnaryOperator appends a surname onto the given name")
    void apply_appendsSurnameToName() {
        UnaryOperator<String> appendNayak = name -> name.concat(" Nayak");
        assertEquals("Himansu Nayak", appendNayak.apply("Himansu"));
    }

    @Test
    @DisplayName("andThen() appends the surname first, then uppercases the whole result")
    void andThen_chainsUnaryOperators() {
        UnaryOperator<String> appendNayak = name -> name.concat(" Nayak");
        UnaryOperator<String> upper       = String::toUpperCase;
        assertEquals("HIMANSU NAYAK", appendNayak.andThen(upper).apply("Himansu"));
    }

    @Test
    @DisplayName("compose() uppercases the input first, then appends the surname")
    void compose_appliesSecondOperatorFirst() {
        UnaryOperator<String> appendNayak = name -> name.concat(" Nayak");
        UnaryOperator<String> upper       = String::toUpperCase;
        // upper first → "HIMANSU", then appendNayak → "HIMANSU Nayak"
        assertEquals("HIMANSU Nayak", appendNayak.compose(upper).apply("Himansu"));
    }

    @Test
    @DisplayName("UnaryOperator.identity() returns the input value unchanged")
    void identity_returnsInputUnchanged() {
        UnaryOperator<String> id = UnaryOperator.identity();
        assertEquals("hello", id.apply("hello"));
    }
}
