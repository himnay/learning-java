package com.org.java.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class LongAccumulatorTest {

    @Test
    void accumulate_withSumOperator_sumsAllValues() {
        LongAccumulator adder = new LongAccumulator(Long::sum, 0L);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        IntStream.range(0, 10).forEach(i -> executor.submit(() -> adder.accumulate(i)));
        ConcurrentUtils.stop(executor);
        // sum of 0..9 = 45
        assertEquals(45L, adder.getThenReset());
    }

    @Test
    void getThenReset_resetsToIdentity() {
        LongAccumulator acc = new LongAccumulator(Long::sum, 0L);
        acc.accumulate(10);
        assertEquals(10, acc.getThenReset());
        assertEquals(0, acc.get());
    }

    @Test
    void accumulate_withMaxOperator_returnsMaxValue() {
        LongAccumulator max = new LongAccumulator(Long::max, Long.MIN_VALUE);
        max.accumulate(5);
        max.accumulate(10);
        max.accumulate(3);
        assertEquals(10, max.get());
    }
}
