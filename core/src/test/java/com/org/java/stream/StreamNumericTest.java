package com.org.java.stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

class StreamNumericTest {

    @Test
    @DisplayName("IntStream.rangeClosed().sum() totals all integers in an inclusive range")
    void intStreamRangeClosed_sum() {
        int sum = IntStream.rangeClosed(1, 6).sum();
        assertEquals(21, sum); // 1+2+3+4+5+6
    }

    @Test
    @DisplayName("IntStream.range() excludes the upper bound from the produced values")
    void range_excludesUpperBound() {
        long count = IntStream.range(1, 50).count();
        assertEquals(49, count); // 1..49
    }

    @Test
    @DisplayName("IntStream.rangeClosed() includes the upper bound in the produced values")
    void rangeClosed_includesUpperBound() {
        long count = IntStream.rangeClosed(1, 50).count();
        assertEquals(50, count); // 1..50
    }

    @Test
    @DisplayName("IntStream min, max, average, and count all report the correct statistics for a range")
    void intStream_minMaxAvgCount() {
        assertEquals(50, IntStream.rangeClosed(1, 50).count());
        assertEquals(1,  IntStream.rangeClosed(1, 50).min().getAsInt());
        assertEquals(49, IntStream.range(1, 50).max().getAsInt());
        assertEquals(25.5, IntStream.rangeClosed(1, 50).average().getAsDouble());
    }

    @Test
    @DisplayName("LongStream.range() excludes the upper bound from the produced values")
    void longStream_range() {
        long count = LongStream.range(1, 50).count();
        assertEquals(49, count);
    }

    @Test
    @DisplayName("boxed() converts an IntStream into a List<Integer> preserving order and size")
    void boxed_convertsIntStreamToList() {
        List<Integer> list = IntStream.range(1, 50).boxed().collect(toList());
        assertEquals(49, list.size());
        assertEquals(1,  list.get(0));
        assertEquals(49, list.get(48));
    }

    @Test
    @DisplayName("mapToInt() unboxes a List<Integer> stream so the elements can be summed")
    void unboxing_mapToIntAndSum() {
        List<Integer> nums = asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        int sum = nums.stream().mapToInt(Integer::intValue).sum();
        assertEquals(55, sum);
    }

    @Test
    @DisplayName("asDoubleStream() converts int elements to doubles before summing")
    void asDoubleStream_convertsElements() {
        double sum = IntStream.rangeClosed(1, 4).asDoubleStream().sum();
        assertEquals(10.0, sum);
    }

    @Test
    @DisplayName("asLongStream() converts int elements to longs before summing")
    void asLongStream_convertsElements() {
        long sum = IntStream.rangeClosed(1, 4).asLongStream().sum();
        assertEquals(10L, sum);
    }

    @Test
    @DisplayName("mapToObj() wraps each int value into an object, producing one object per element")
    void mapToObj_wrapsEachIntInDummy() {
        List<Object> result = IntStream.range(1, 4)
                .mapToObj(i -> new Object())
                .collect(toList());
        assertEquals(3, result.size());
    }

    @Test
    @DisplayName("reduce() with an identity and Integer::sum totals a boxed list of integers")
    void reduce_sumOfIntegerListWithBoxing() {
        List<Integer> numeric = asList(1, 2, 3, 4, 5);
        Integer sum = numeric.stream().reduce(0, Integer::sum);
        assertEquals(15, sum);
    }
}
