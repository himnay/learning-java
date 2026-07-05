package com.org.java.methodReference;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class StaticMethodReferenceTest {

    public static boolean isPrime(int number) {
        if (number <= 1) return false;
        for (int i = 2; i < number; i++) {
            if (number % i == 0) return false;
        }
        return true;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List findPrimeNumbers(List list, Predicate predicate) {
        List result = new ArrayList();
        list.stream().filter(predicate).forEach(result::add);
        return result;
    }

    @Test
    @DisplayName("isPrime() correctly distinguishes prime numbers from non-prime numbers")
    void isPrime_identifiesPrimes() {
        assertTrue(isPrime(2));
        assertTrue(isPrime(3));
        assertTrue(isPrime(17));
        assertFalse(isPrime(1));
        assertFalse(isPrime(4));
        assertFalse(isPrime(15));
    }

    @Test
    @DisplayName("findPrimeNumbers() filters a list down to just its prime values")
    void findPrimeNumbers_returnsOnlyPrimes() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 16);
        List<Integer> primes = findPrimeNumbers(numbers,
                (Predicate<Integer>) n -> isPrime(n));
        assertEquals(Arrays.asList(2, 3, 5, 7, 11, 13), primes);
    }

    @Test
    @DisplayName("findPrimeNumbers() returns an empty list when given an empty input list")
    void findPrimeNumbers_emptyInputReturnsEmptyList() {
        List<Integer> primes = findPrimeNumbers(List.of(),
                (Predicate<Integer>) n -> isPrime(n));
        assertTrue(primes.isEmpty());
    }
}
