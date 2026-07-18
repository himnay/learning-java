package com.org.java.functionalInterfaces;

import java.util.function.UnaryOperator;

public class UnaryOperatorExample {

    static UnaryOperator<String> unaryOperator = (s)->s.concat("Default");



    /** Application entry point. */
    public static void main(String[] args) {

        System.out.println(unaryOperator.apply("java8"));


    }
}
