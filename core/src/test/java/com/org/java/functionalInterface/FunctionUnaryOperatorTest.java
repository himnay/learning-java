package com.org.java.functionalInterface;

import org.junit.jupiter.api.Test;

import java.util.function.UnaryOperator;

import static org.junit.jupiter.api.Assertions.*;

class FunctionUnaryOperatorTest {

    @Test
    void apply_appendsSurnameToName() {
        UnaryOperator<String> appendNayak = name -> name.concat(" Nayak");
        assertEquals("Himansu Nayak", appendNayak.apply("Himansu"));
    }

    @Test
    void andThen_chainsUnaryOperators() {
        UnaryOperator<String> appendNayak = name -> name.concat(" Nayak");
        UnaryOperator<String> upper       = String::toUpperCase;
        assertEquals("HIMANSU NAYAK", appendNayak.andThen(upper).apply("Himansu"));
    }

    @Test
    void compose_appliesSecondOperatorFirst() {
        UnaryOperator<String> appendNayak = name -> name.concat(" Nayak");
        UnaryOperator<String> upper       = String::toUpperCase;
        // upper first → "HIMANSU", then appendNayak → "HIMANSU Nayak"
        assertEquals("HIMANSU Nayak", appendNayak.compose(upper).apply("Himansu"));
    }

    @Test
    void identity_returnsInputUnchanged() {
        UnaryOperator<String> id = UnaryOperator.identity();
        assertEquals("hello", id.apply("hello"));
    }
}
