package com.org.jpms.service;

import com.org.jpms.api.Greeter;
import com.org.jpms.service.internal.GreetingFormatter;

/**
 * Registered as a provider of {@link Greeter} in {@code module-info.java}.
 * Must be public with a public no-args constructor for ServiceLoader to
 * instantiate it.
 */
public final class EnglishGreeter implements Greeter {

    @Override
    public String language() {
        return "English";
    }

    @Override
    public String greet(String name) {
        return GreetingFormatter.format("Hello", name);
    }
}
