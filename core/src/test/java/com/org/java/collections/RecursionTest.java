package com.org.java.collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Recursion - Complexity Reference")
class RecursionTest {

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(n) — Linear recursion")
    class LinearRecursion {

        @Test
        @DisplayName("Fibonacci (naive)  →  O(2^n)  (exponential — shown for contrast)")
        void fibNaive() {
            assertEquals(55, fibNaive(10));
        }

        private int fibNaive(int n) {
            if (n <= 1) return n;
            return fibNaive(n - 1) + fibNaive(n - 2);  // O(2^n)
        }

        @Test
        @DisplayName("Factorial  →  O(n) time, O(n) call stack space")
        void factorial() {
            assertEquals(120, factorial(5));
            assertEquals(1, factorial(0));
        }

        private long factorial(int n) {
            if (n == 0) return 1;
            return n * factorial(n - 1);
        }

        @Test
        @DisplayName("Sum of array (recursive)  →  O(n)")
        void arraySum() {
            int[] a = {1, 2, 3, 4, 5};
            assertEquals(15, arraySum(a, 0));
        }

        private int arraySum(int[] a, int i) {
            if (i == a.length) return 0;
            return a[i] + arraySum(a, i + 1);
        }

        @Test
        @DisplayName("Reverse string recursively  →  O(n)")
        void reverseString() {
            assertEquals("olleh", reverse("hello"));
        }

        private String reverse(String s) {
            if (s.length() <= 1) return s;
            return reverse(s.substring(1)) + s.charAt(0);
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(n log n) — Divide and conquer recursion")
    class DivideConquer {

        @Test
        @DisplayName("Merge sort  →  T(n) = 2T(n/2) + O(n)  →  O(n log n)")
        void mergeSort() {
            int[] a = {5, 2, 8, 1, 9, 3};
            mergeSort(a, 0, a.length - 1);
            assertArrayEquals(new int[]{1, 2, 3, 5, 8, 9}, a);
        }

        private void mergeSort(int[] a, int lo, int hi) {
            if (lo >= hi) return;
            int mid = (lo + hi) / 2;
            mergeSort(a, lo, mid);
            mergeSort(a, mid + 1, hi);
            // merge
            int[] tmp = Arrays.copyOfRange(a, lo, hi + 1);
            int i = 0, j = mid - lo + 1, k = lo;
            while (i <= mid - lo && j <= hi - lo)
                a[k++] = tmp[i] <= tmp[j] ? tmp[i++] : tmp[j++];
            while (i <= mid - lo) a[k++] = tmp[i++];
            while (j <= hi - lo)  a[k++] = tmp[j++];
        }

        @Test
        @DisplayName("Binary search recursive  →  T(n) = T(n/2) + O(1)  →  O(log n)")
        void binarySearch() {
            int[] sorted = {1, 3, 5, 7, 9};
            assertEquals(2, binarySearch(sorted, 0, sorted.length - 1, 5));
            assertEquals(-1, binarySearch(sorted, 0, sorted.length - 1, 4));
        }

        private int binarySearch(int[] a, int lo, int hi, int target) {
            if (lo > hi) return -1;
            int mid = lo + (hi - lo) / 2;
            if (a[mid] == target) return mid;
            return a[mid] < target ? binarySearch(a, mid + 1, hi, target)
                                   : binarySearch(a, lo, mid - 1, target);
        }

        @Test
        @DisplayName("Power function (fast exponentiation)  →  O(log n)")
        void fastPower() {
            assertEquals(8L, power(2, 3));
            assertEquals(1024L, power(2, 10));
        }

        private long power(long base, int exp) {
            if (exp == 0) return 1;
            long half = power(base, exp / 2);
            return exp % 2 == 0 ? half * half : base * half * half;
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(2^n) — Exponential recursion (subsets/fibonacci)")
    class ExponentialRecursion {

        @Test
        @DisplayName("All subsets of array  →  O(2^n) time and space")
        void allSubsets() {
            List<List<Integer>> result = new ArrayList<>();
            generateSubsets(new int[]{1, 2, 3}, 0, new ArrayList<>(), result);
            assertEquals(8, result.size()); // 2^3 = 8 subsets
        }

        private void generateSubsets(int[] nums, int idx, List<Integer> current, List<List<Integer>> result) {
            result.add(new ArrayList<>(current));
            for (int i = idx; i < nums.length; i++) {
                current.add(nums[i]);
                generateSubsets(nums, i + 1, current, result);
                current.remove(current.size() - 1);
            }
        }

        @Test
        @DisplayName("Tower of Hanoi  →  O(2^n) moves")
        void towerOfHanoi() {
            List<String> moves = new ArrayList<>();
            hanoi(3, 'A', 'C', 'B', moves);
            assertEquals(7, moves.size()); // 2^n - 1 moves
        }

        private void hanoi(int n, char src, char dest, char aux, List<String> moves) {
            if (n == 0) return;
            hanoi(n - 1, src, aux, dest, moves);
            moves.add(src + "->" + dest);
            hanoi(n - 1, aux, dest, src, moves);
        }
    }
}
