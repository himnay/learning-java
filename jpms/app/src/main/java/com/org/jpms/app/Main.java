package com.org.jpms.app;

import com.org.jpms.api.Greeter;
import java.util.ServiceLoader;

/**
 * Loads every {@link Greeter} provider found on the module path and prints
 * a greeting from each, without this module ever depending on
 * {@code com.org.jpms.service} at compile time.
 */
public final class Main {

    public static void main(String[] args) {
        String name = args.length > 0 ? args[0] : "World";

        ServiceLoader<Greeter> greeters = ServiceLoader.load(Greeter.class);

        boolean found = false;
        for (Greeter greeter : greeters) {
            found = true;
            System.out.println("[" + greeter.language() + "] " + greeter.greet(name));
        }

        if (!found) {
            System.out.println("No Greeter providers found on the module path.");
        }
    }
}
