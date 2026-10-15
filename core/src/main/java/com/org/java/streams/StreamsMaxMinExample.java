package com.org.java.streams;

import java.util.*;

public class StreamsMaxMinExample {

    private StreamsMaxMinExample() {
    }

    /** Returns the max value. */
    private static Optional<Integer> maxValue(List<Integer> integerList){
        return integerList.stream()
                //.reduce(0,(a,b)->(a>b) ? a : b);
        .reduce(Integer::max);
    }

    /** Returns the min value. */
    private static Optional<Integer> minValue(List<Integer> integerList){
        return integerList.stream()
                .reduce((a,b)->(a<b) ? a : b);
                //.reduce(0,Integer::min);
    }

    /** Application entry point. */
    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(6,7,8,9,10);
        Optional<Integer> maxValue =maxValue(integers);
        int max = maxValue.isPresent() ? maxValue.get():0;
        System.out.println("Max Value is : " + max);

        Optional<Integer> minValue =minValue(integers);
        int min = minValue.isPresent() ? minValue.get():0;
        System.out.println("Min Value is : " + min);
    }
}
