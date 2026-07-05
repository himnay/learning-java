package com.org.java.misc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.annotation.*;

import static org.junit.jupiter.api.Assertions.*;

class AnnotationsTest {

    @Retention(RetentionPolicy.RUNTIME)
    @interface Hints {
        Hint[] value();
    }

    @Repeatable(Hints.class)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Hint {
        String value();
    }

    @Hint("hint1")
    @Hint("hint2")
    static class Person {}

    @Test
    @DisplayName("getAnnotation() for a repeatable annotation type returns null when it appears more than once")
    void getAnnotation_returnsNullForRepeatable() {
        // Direct @Hint is not accessible when repeated; container @Hints wraps them
        Hint hint = Person.class.getAnnotation(Hint.class);
        assertNull(hint);
    }

    @Test
    @DisplayName("The generated container annotation holds all repeated @Hint values in declaration order")
    void getAnnotation_containerHoldsAllHints() {
        Hints hints = Person.class.getAnnotation(Hints.class);
        assertNotNull(hints);
        assertEquals(2, hints.value().length);
        assertEquals("hint1", hints.value()[0].value());
        assertEquals("hint2", hints.value()[1].value());
    }

    @Test
    @DisplayName("getAnnotationsByType() returns every repeated @Hint annotation directly")
    void getAnnotationsByType_returnsAllRepeatableAnnotations() {
        Hint[] hints = Person.class.getAnnotationsByType(Hint.class);
        assertEquals(2, hints.length);
        assertEquals("hint1", hints[0].value());
        assertEquals("hint2", hints[1].value());
    }

    @Test
    @DisplayName("The number of repeated annotations returned matches the number of declarations")
    void repeatableAnnotation_countMatchesDeclarations() {
        int count = Person.class.getAnnotationsByType(Hint.class).length;
        assertEquals(2, count);
    }
}
