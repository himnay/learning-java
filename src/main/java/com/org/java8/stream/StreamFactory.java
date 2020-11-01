package com.org.java8.stream;

import java.util.Random;
import java.util.stream.Stream;

public class StreamFactory {
    public static void main(String[] args) {

        // 1. of()
        Stream<String> stringStream = Stream.of("Alpha", "Beta", "Gamma", "Theta");
        stringStream.forEach(System.out::println);

        // 2. iterate()
        Stream.iterate(1, x -> x*2)
                .limit(10)
                .forEach(System.out::println);

        // 3. generate
        Stream.generate(new Random()::nextInt)
                .limit(5)
                .forEach(System.out::println);
    }
}
