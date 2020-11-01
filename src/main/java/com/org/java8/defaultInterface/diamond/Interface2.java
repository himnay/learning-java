package com.org.java8.defaultInterface.diamond;

public interface Interface2 {

    default void methodA() {
        System.out.println("Inside method A" + Interface2.class);
    }
}
