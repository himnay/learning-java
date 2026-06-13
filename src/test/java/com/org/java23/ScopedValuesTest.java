// FILE 14: src/test/java/com/org/java23/ScopedValuesTest.java
package com.org.java23;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.StructuredTaskScope;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ScopedValue (Java 23+)")
class ScopedValuesTest {

    // ScopedValue provides an immutable, thread-local-like value that is
    // accessible only within a bounded dynamic scope established by
    // ScopedValue.where(...).run(...) or .call(...).

    private static final ScopedValue<String> USERNAME    = ScopedValue.newInstance();
    private static final ScopedValue<Integer> REQUEST_ID = ScopedValue.newInstance();
    private static final ScopedValue<String> ROLE        = ScopedValue.newInstance();

    // ---------------------------------------------------------------------------
    // Basic binding and retrieval
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("ScopedValue.get() returns the bound value within the scope")
    void basicBinding() {
        ScopedValue.where(USERNAME, "Alice").run(() ->
                assertEquals("Alice", USERNAME.get())
        );
    }

    @Test
    @DisplayName("ScopedValue is unbound outside the scope")
    void unboundOutsideScope() {
        // Bind and run — after run() returns the binding is gone
        ScopedValue.where(USERNAME, "Bob").run(() -> {
            assertEquals("Bob", USERNAME.get());
        });
        assertFalse(USERNAME.isBound(), "USERNAME must be unbound after the scope exits");
    }

    @Test
    @DisplayName("ScopedValue.isBound() returns false before any binding")
    void notBoundInitially() {
        assertFalse(USERNAME.isBound());
    }

    @Test
    @DisplayName("ScopedValue.isBound() returns true within the scope")
    void isBoundInsideScope() {
        ScopedValue.where(USERNAME, "Carol").run(() ->
                assertTrue(USERNAME.isBound())
        );
    }

    // ---------------------------------------------------------------------------
    // ScopedValue.call() — returns a value from the scope
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("ScopedValue.call() can return a result from inside the scope")
    void callReturnsResult() throws Exception {
        String result = ScopedValue.where(USERNAME, "Dave")
                .call(() -> "Hello, " + USERNAME.get());
        assertEquals("Hello, Dave", result);
    }

    @Test
    @DisplayName("ScopedValue.call() with integer bound value")
    void callWithIntegerBound() throws Exception {
        int result = ScopedValue.where(REQUEST_ID, 42)
                .call(() -> REQUEST_ID.get() * 2);
        assertEquals(84, result);
    }

    // ---------------------------------------------------------------------------
    // Multiple bindings in one scope
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Multiple ScopedValues can be bound simultaneously in one scope")
    void multipleBindings() {
        ScopedValue.where(USERNAME, "Eve")
                .where(REQUEST_ID, 100)
                .run(() -> {
                    assertEquals("Eve", USERNAME.get());
                    assertEquals(100, REQUEST_ID.get());
                });
    }

    @Test
    @DisplayName("Multiple ScopedValues are all accessible inside a combined scope")
    void threeValuesInOneScope() {
        ScopedValue.where(USERNAME, "Frank")
                .where(REQUEST_ID, 7)
                .where(ROLE, "admin")
                .run(() -> {
                    assertEquals("Frank", USERNAME.get());
                    assertEquals(7,       REQUEST_ID.get());
                    assertEquals("admin", ROLE.get());
                });
    }

    // ---------------------------------------------------------------------------
    // Nested scopes — inner binding shadows outer
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Inner scope rebinding shadows the outer binding")
    void nestedScopeShadowing() {
        ScopedValue.where(USERNAME, "Outer").run(() -> {
            assertEquals("Outer", USERNAME.get());

            ScopedValue.where(USERNAME, "Inner").run(() ->
                    assertEquals("Inner", USERNAME.get())
            );

            // After inner scope exits, outer binding is restored
            assertEquals("Outer", USERNAME.get());
        });
    }

    @Test
    @DisplayName("After nested scope exits, outer binding is fully restored")
    void outerBindingRestoredAfterNested() {
        ScopedValue.where(REQUEST_ID, 1).run(() -> {
            ScopedValue.where(REQUEST_ID, 99).run(() ->
                    assertEquals(99, REQUEST_ID.get())
            );
            assertEquals(1, REQUEST_ID.get(), "Outer REQUEST_ID should be restored");
        });
    }

    // ---------------------------------------------------------------------------
    // ScopedValue with StructuredTaskScope (proper propagation mechanism)
    //
    // Note: Thread.ofVirtual().start() does NOT inherit ScopedValue bindings.
    // Bindings are propagated only through StructuredTaskScope.fork(), which
    // is the correct structured-concurrency mechanism for inheriting scope.
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("ScopedValue binding is propagated to subtasks via StructuredTaskScope.fork()")
    void inheritedByStructuredTaskScope() throws Exception {
        String[] captured = {null};

        ScopedValue.where(USERNAME, "Grace").run(() -> {
            try (var scope = StructuredTaskScope.open(
                    StructuredTaskScope.Joiner.<String>allSuccessfulOrThrow())) {
                var task = scope.<String>fork(() -> USERNAME.get()); // binding inherited
                scope.join();
                captured[0] = task.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        assertEquals("Grace", captured[0],
                "StructuredTaskScope subtask should inherit the ScopedValue binding");
    }

    // ---------------------------------------------------------------------------
    // orElse / orElseThrow
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("ScopedValue.orElse() returns the fallback when not bound")
    void orElseWhenUnbound() {
        assertFalse(USERNAME.isBound());
        String value = USERNAME.orElse("default-user");
        assertEquals("default-user", value);
    }

    @Test
    @DisplayName("ScopedValue.orElse() returns the bound value when inside a scope")
    void orElseWhenBound() {
        ScopedValue.where(USERNAME, "Henry").run(() -> {
            String value = USERNAME.orElse("fallback");
            assertEquals("Henry", value);
        });
    }

    @Test
    @DisplayName("ScopedValue.orElseThrow(Supplier) throws when not bound")
    void orElseThrowWhenUnbound() {
        assertFalse(USERNAME.isBound());
        assertThrows(IllegalStateException.class,
                () -> USERNAME.orElseThrow(() -> new IllegalStateException("not bound")));
    }

    @Test
    @DisplayName("ScopedValue.orElseThrow(Supplier) returns the value when bound")
    void orElseThrowWhenBound() {
        ScopedValue.where(USERNAME, "Iris").run(() -> {
            String value = USERNAME.orElseThrow(
                    () -> new IllegalStateException("not bound"));
            assertEquals("Iris", value);
        });
    }
}
