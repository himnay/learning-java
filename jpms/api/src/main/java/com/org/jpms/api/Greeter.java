package com.org.jpms.api;

/**
 * Service contract implemented by provider modules and looked up at runtime
 * via {@link java.util.ServiceLoader}.
 */
public interface Greeter {

    String language();

    String greet(String name);
}
