package com.org.interview.ch08;

// Q8.1 Write a method to generate the Nth Fibonacci number.
public class Q8_1_Fibonacci {

    // Recursive - O(2^n) time
    public static long fibRecursive(long n) {
        if (n < 1) return -1;
        if (n == 1 || n == 2) return 1;
        return fibRecursive(n - 1) + fibRecursive(n - 2);
    }

    // Iterative - O(n) time, O(1) space
    public static long fibIterative(long n) {
        if (n < 1) return -1;
        if (n == 1 || n == 2) return 1;
        long a = 1, b = 1;
        for (long i = 3; i <= n; i++) {
            long c = a + b;
            a = b;
            b = c;
        }
        return b;
    }

    // Matrix exponentiation - O(log n) time
    // | F(n+1) F(n)   |   | 1 1 |^n
    // | F(n)   F(n-1) | = | 1 0 |
    private static void multiply(long[][] c, long[][] a, long[][] b) {
        long t00 = a[0][0] * b[0][0] + a[0][1] * b[1][0];
        long t01 = a[0][0] * b[0][1] + a[0][1] * b[1][1];
        long t10 = a[1][0] * b[0][0] + a[1][1] * b[1][0];
        long t11 = a[1][0] * b[0][1] + a[1][1] * b[1][1];
        c[0][0] = t00; c[0][1] = t01;
        c[1][0] = t10; c[1][1] = t11;
    }

    public static long fibMatrix(long n) {
        if (n < 1) return -1;
        if (n == 1 || n == 2) return 1;
        long[][] a = {{1, 1}, {1, 0}};
        long[][] s = {{1, 0}, {0, 1}}; // identity
        long[][] tmp = new long[2][2];
        long p = n - 1;
        while (p > 0) {
            if ((p & 1) == 1) { multiply(tmp, s, a); copy(s, tmp); }
            multiply(tmp, a, a); copy(a, tmp);
            p >>= 1;
        }
        return s[0][0];
    }

    private static void copy(long[][] dst, long[][] src) {
        dst[0][0] = src[0][0]; dst[0][1] = src[0][1];
        dst[1][0] = src[1][0]; dst[1][1] = src[1][1];
    }

    public static void main(String[] args) {
        System.out.print("Recursive: ");
        for (int i = 1; i <= 15; i++) System.out.print(fibRecursive(i) + " ");
        System.out.println();
        System.out.print("Iterative: ");
        for (int i = 1; i <= 15; i++) System.out.print(fibIterative(i) + " ");
        System.out.println();
        System.out.print("Matrix:    ");
        for (int i = 1; i <= 15; i++) System.out.print(fibMatrix(i) + " ");
        System.out.println();
    }
}
