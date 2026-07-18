package com.org.java.functionalInterfaces;

import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;

public class PrimitivePredicateExample {

   static IntPredicate intPredicate = (a) -> a%2==0;
    static DoublePredicate doublePredicate = (a) -> a%4.0==0;


    /** Application entry point. */
    public static void main(String[] args) {


        System.out.println(intPredicate.test(3));

        System.out.println(intPredicate.test(12));
    }
}
