package com.org.java.functionalInterfaces;

public class FunctionExample1 {

    /** Appends default. */
    public String appendDefault(String input){

        return FunctionExample.addSomeString.apply(input);
    }

    /** Application entry point. */
    public static void main(String[] args) {

    }

}
