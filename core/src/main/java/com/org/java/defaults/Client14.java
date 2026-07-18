package com.org.java.defaults;

public class Client14 implements Interface1,Interface4{

    /** Handles method a. */
    public void  methodA(){
        System.out.println("Inside method A "+Client14.class);
    }

    /** Application entry point. */
    public static void main(String[] args) {
        Client14 client14 = new Client14();
        client14.methodA();

    }

}
