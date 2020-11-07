package com.org.java8.stream;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class StreamNumeric {
    public static void main(String[] args) {
        List<Integer> numeric = asList(1,2,3,4,5);
        // wastage as unboxing(Integer -> int) need to be done during reduce() call
        Integer reduce = numeric.stream()
                .reduce(0, (x, y) -> x + y);
        System.out.println(reduce);

        // 1.
        int sum = IntStream.rangeClosed(1, 6)
                .sum();
        System.out.println(sum);

        // 2. range() vs rangeClosed()
        IntStream.range(1, 50).forEach(value -> System.out.print(value + ","));
        System.out.println();
        IntStream.rangeClosed(1, 50).forEach(value -> System.out.print(value + ","));

        // 3. LongStream
        System.out.println();
        LongStream.range(1, 50).forEach(value -> System.out.print(value + ","));

        // 4 DoubleStream and LongStream from IntStream
        System.out.println();
        IntStream.range(1, 50).asDoubleStream().forEach(value -> System.out.print(value + ","));
        System.out.println();
        IntStream.range(1, 50).asLongStream().forEach(value -> System.out.print(value + ","));

        // 5. min, max, avg & count
        System.out.println();
        System.out.println(IntStream.rangeClosed(1, 50).count());
        System.out.println(IntStream.rangeClosed(1, 50).min().getAsInt());
        System.out.println(IntStream.range(1, 50).max().getAsInt());
        System.out.println(IntStream.rangeClosed(1, 50).average().getAsDouble());

        // 6 boxing and unboxing
        List<Integer> collect = IntStream.range(1, 50)
                .boxed()
                .collect(toList());
        System.out.println(collect);

        // unboxing
        List<Integer> numericList = asList(1,2,3,4,5,6,7,8,9,10);
        numericList.stream()
                .mapToInt(Integer::intValue)
                .sum();

        // 7. mapToObject(), mapToLong() and mapToDouble()
        numericList.stream()
                .mapToInt(Integer::intValue)
                .mapToObj(Dummy::new)
                .collect(toList());

        IntStream.range(1,50)
                .mapToLong((i) -> i)
                .sum();

        IntStream.range(1,50)
                .mapToDouble((i) -> i)
                .sum();
    }

    public static class Dummy {
        int i;

        public Dummy(int i){
            this.i = i;
        }
    }

    public static class DummyLong {
        long i;

        public DummyLong(long i){
            this.i = i;
        }
    }
}
