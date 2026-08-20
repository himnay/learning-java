package com.org.jpms.service;

import com.org.jpms.api.Greeter;
import com.org.jpms.service.internal.GreetingFormatter;

/**
 * A second provider of {@link Greeter}, to show that ServiceLoader returns
 * every registered implementation, not just one.
 */
public final class SpanishGreeter implements Greeter {

    @Override
    public String language() {
        return "Spanish";
    }

    @Override
    public String greet(String name) {
        return GreetingFormatter.format("Hola", name);
    }
}
