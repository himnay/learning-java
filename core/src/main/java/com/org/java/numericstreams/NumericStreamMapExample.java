package com.org.java.numericstreams;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toList;

public class NumericStreamMapExample {


    /** Maps to obj. */
    public static List<Integer> mapToObj(){

        List<Integer> integerList = IntStream.rangeClosed(1,5)
                .mapToObj((i)-> {
                    return new Integer(i);
                })
                .collect(toList());

        return integerList;

    }

    /** Maps to double. */
    public static double mapToDouble(){

      return   IntStream.rangeClosed(1,5)
                .mapToDouble((i) ->  i)
                .sum();

    }

    /** Maps to long. */
    public static long mapToLong(){

        return   IntStream.rangeClosed(1,5)
                .mapToLong((i) ->  i)
                .sum();

    }

    /** Application entry point. */
    public static void main(String[] args) {

        System.out.println("mapToObj : " + mapToObj());

        System.out.println("mapToDouble() : " + mapToDouble());

        System.out.println("mapToLong() : " + mapToLong());

    }
}
