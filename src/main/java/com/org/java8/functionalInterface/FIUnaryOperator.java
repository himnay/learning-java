package com.org.java8.functionalInterface;

import java.util.function.UnaryOperator;

public class FIUnaryOperator {
    public static void main(String[] args) {
        UnaryOperator<String> unaryOperator = (name) -> name.concat(" Nayak");
        System.out.println(unaryOperator.apply("Himansu"));
    }
}
