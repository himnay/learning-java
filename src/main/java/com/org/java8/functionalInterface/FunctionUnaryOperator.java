package com.org.java8.functionalInterface;

import java.util.function.UnaryOperator;

/**
 * special function whose argument and return type are of same type
 */
public class FunctionUnaryOperator {
    public static void main(String[] args) {
        UnaryOperator<String> unaryOperator = (name) -> name.concat(" Nayak");
        System.out.println(unaryOperator.apply("Himansu"));
    }
}
