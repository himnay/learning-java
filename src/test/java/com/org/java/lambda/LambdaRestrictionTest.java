package com.org.java.lambda;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class LambdaRestrictionTest {

    @Test
    void effectivelyFinal_localVariableCapturedByLambda() {
        int i = 10; // effectively final — never reassigned
        Consumer<Integer> consumer = j -> assertEquals(10, i); // captures i
        consumer.accept(42);
    }

    @Test
    void lambdaCaptures_outerScope() {
        String prefix = "Hello";
        Supplier<String> supplier = () -> prefix + " World";
        assertEquals("Hello World", supplier.get());
    }

    @Test
    void lambdaDoesNotModify_capturedVariable() {
        int[] counter = {0}; // array trick: reference is final, content can change
        Runnable r = () -> counter[0]++;
        r.run();
        r.run();
        assertEquals(2, counter[0]);
    }

    @Test
    void lambda_canCallInstanceMethods_onCapturedObject() {
        StringBuilder sb = new StringBuilder("start");
        Runnable r = () -> sb.append("-end"); // sb reference is effectively final
        r.run();
        assertEquals("start-end", sb.toString());
    }
}
