package com.org.java.numericstreams;

import java.util.stream.IntStream;

public class NumericStreamAsExample {

    /** Returns the as double stream. */
    public static double asDoubleStream(){

    return  IntStream.rangeClosed(1,5)
                .asDoubleStream()
                .sum();
    }


    /** Returns the as long stream. */
    public static long asLongStream(){
        return  IntStream.rangeClosed(1,5)
                .asLongStream()
                .sum();

    }

    /** Application entry point. */
    public static void main(String[] args) {

        System.out.println("asDoubleStream :" + asDoubleStream());
        System.out.println("asDoubleStream :" + asLongStream());

    }
}
