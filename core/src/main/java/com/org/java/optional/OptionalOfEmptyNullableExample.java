package com.org.java.optional;

import java.util.Optional;

public class OptionalOfEmptyNullableExample {

    /** Returns the of nullable. */
    public static Optional<String> ofNullable(){

       return  Optional.ofNullable("Hello");

    }

    /** Returns the of. */
    public static Optional<String> of(){

        return  Optional.of("Hello");
       // return  Optional.of(null);

    }

    /** Returns the empty. */
    public static Optional<String> empty(){

        return  Optional.empty();
        // return  Optional.of(null);

    }
    /** Application entry point. */
    public static void main(String[] args) {

        System.out.println(ofNullable().get());
        System.out.println(of());
        System.out.println(empty().get());
    }
}
