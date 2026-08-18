package com.org.java.misc;

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
    void getAnnotation_returnsNullForRepeatable() {
        // Direct @Hint is not accessible when repeated; container @Hints wraps them
        Hint hint = Person.class.getAnnotation(Hint.class);
        assertNull(hint);
    }

    @Test
    void getAnnotation_containerHoldsAllHints() {
        Hints hints = Person.class.getAnnotation(Hints.class);
        assertNotNull(hints);
        assertEquals(2, hints.value().length);
        assertEquals("hint1", hints.value()[0].value());
        assertEquals("hint2", hints.value()[1].value());
    }

    @Test
    void getAnnotationsByType_returnsAllRepeatableAnnotations() {
        Hint[] hints = Person.class.getAnnotationsByType(Hint.class);
        assertEquals(2, hints.length);
        assertEquals("hint1", hints[0].value());
        assertEquals("hint2", hints[1].value());
    }

    @Test
    void repeatableAnnotation_countMatchesDeclarations() {
        int count = Person.class.getAnnotationsByType(Hint.class).length;
        assertEquals(2, count);
    }
}
