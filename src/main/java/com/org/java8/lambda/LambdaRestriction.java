package com.org.java8.lambda;

import java.util.function.Consumer;

public class LambdaRestriction {
    public static void main(String[] args) {
        // 1. Local variable cannot be passed as argument in the lambda
        int i = 10;
//        Consumer<Integer> consumer = (i) -> System.out.println(i);

        // 2. allowed
        Consumer<Integer> consumer2 = (j) -> System.out.println(i);

        // 3. i++ not allowed. also know as "effective final" as the variable is consider as a final variable
//        Consumer<Integer> consumer3 = (j) -> System.out.println(i++);

    }
}
