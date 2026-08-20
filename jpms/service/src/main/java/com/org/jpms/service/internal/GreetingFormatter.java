package com.org.jpms.service.internal;

/**
 * Public class in an unexported package. Reachable from inside the
 * {@code com.org.jpms.service} module but inaccessible to any other module,
 * even reflectively, because the package is never opened or exported.
 */
public final class GreetingFormatter {

    private GreetingFormatter() {
    }

    public static String format(String greeting, String name) {
        return greeting + ", " + name + "!";
    }
}
