package com.org.java.functionalInterfaces;

import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;

public class PrimitiveConsumerExample {

    static IntConsumer intc1 = c -> System.out.println(c*c);
    static DoubleConsumer doublec2 = c -> System.out.println(c*c);

    /** Application entry point. */
    public static void main(String[] args) {

        intc1.accept(3);
        doublec2.accept(3.0);
        doublec2.accept(3);
    }
 }
