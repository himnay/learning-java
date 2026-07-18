package com.org.java.optional;

import java.util.Optional;

public class OptionalPresentExample {

    /** Application entry point. */
    public static void main(String[] args) {

        Optional<String> stringOptional = Optional.ofNullable("Hello Optional");

        System.out.println(stringOptional.isPresent());

        stringOptional.ifPresent((s -> System.out.println("value is : " + s)));
    }
}
