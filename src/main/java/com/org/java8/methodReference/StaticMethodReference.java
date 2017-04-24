package com.org.java8.methodReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class StaticMethodReference {

    public static void main(String[] args) {
        List numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 16);
        List primeNumbers = StaticMethodReference.findPrimeNumbers(numbers,
                (number) -> StaticMethodReference.isPrime((int) number));

        System.out.println("Prime Numbers are " + primeNumbers);
    }

    public static boolean isPrime(int number) {
        if (number == 1) {
            return false;
        }
        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

        public static List findPrimeNumbers(List list, Predicate predicate) {
                List sortedNumbers = new ArrayList();
                list.stream().filter(predicate).forEach( sortedNumbers::add );
                return sortedNumbers;
        }
}
