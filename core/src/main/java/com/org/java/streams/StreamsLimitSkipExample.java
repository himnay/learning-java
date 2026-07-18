package com.org.java.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StreamsLimitSkipExample {

    /** Returns the limit. */
    public static Optional<Integer> limit(List<Integer> integers){
        return  integers.stream()
                .limit(2)
                .reduce((a,b)-> a+b);

    }

    /** Returns the skip. */
    public static Optional<Integer> skip(List<Integer> integers){
        return  integers.stream()
                .skip(3)
                .reduce((a,b)-> a+b);
    }
    

    /** Application entry point. */
    public static void main(String[] args) {

        List<Integer> integers = Arrays.asList(6,7,8,9,10);

        Optional<Integer> limitResult = limit(integers);
        int result = limitResult.isPresent() ? limitResult.get() : 0;
        System.out.println("The limit result is : " + result);

        Optional<Integer> skipResult = skip(integers);
        int result1 = skipResult.isPresent() ? skipResult.get() : 0;
        System.out.println("The skip result is : " + result1);

    }
}
