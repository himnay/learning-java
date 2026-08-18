package com.org.java.stream;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

class StreamNumericTest {

    @Test
    void intStreamRangeClosed_sum() {
        int sum = IntStream.rangeClosed(1, 6).sum();
        assertEquals(21, sum); // 1+2+3+4+5+6
    }

    @Test
    void range_excludesUpperBound() {
        long count = IntStream.range(1, 50).count();
        assertEquals(49, count); // 1..49
    }

    @Test
    void rangeClosed_includesUpperBound() {
        long count = IntStream.rangeClosed(1, 50).count();
        assertEquals(50, count); // 1..50
    }

    @Test
    void intStream_minMaxAvgCount() {
        assertEquals(50, IntStream.rangeClosed(1, 50).count());
        assertEquals(1,  IntStream.rangeClosed(1, 50).min().getAsInt());
        assertEquals(49, IntStream.range(1, 50).max().getAsInt());
        assertEquals(25.5, IntStream.rangeClosed(1, 50).average().getAsDouble());
    }

    @Test
    void longStream_range() {
        long count = LongStream.range(1, 50).count();
        assertEquals(49, count);
    }

    @Test
    void boxed_convertsIntStreamToList() {
        List<Integer> list = IntStream.range(1, 50).boxed().collect(toList());
        assertEquals(49, list.size());
        assertEquals(1,  list.get(0));
        assertEquals(49, list.get(48));
    }

    @Test
    void unboxing_mapToIntAndSum() {
        List<Integer> nums = asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        int sum = nums.stream().mapToInt(Integer::intValue).sum();
        assertEquals(55, sum);
    }

    @Test
    void asDoubleStream_convertsElements() {
        double sum = IntStream.rangeClosed(1, 4).asDoubleStream().sum();
        assertEquals(10.0, sum);
    }

    @Test
    void asLongStream_convertsElements() {
        long sum = IntStream.rangeClosed(1, 4).asLongStream().sum();
        assertEquals(10L, sum);
    }

    @Test
    void mapToObj_wrapsEachIntInDummy() {
        List<Object> result = IntStream.range(1, 4)
                .mapToObj(i -> new Object())
                .collect(toList());
        assertEquals(3, result.size());
    }

    @Test
    void reduce_sumOfIntegerListWithBoxing() {
        List<Integer> numeric = asList(1, 2, 3, 4, 5);
        Integer sum = numeric.stream().reduce(0, Integer::sum);
        assertEquals(15, sum);
    }
}
