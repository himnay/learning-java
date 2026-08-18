package com.org.interview;

// Q5.5 Write a function to determine the number of bits required to convert integer A to B.
// Input: 31, 14   Output: 2
public class Q31_BitsToConvert {

    /** Counts ones. */
    public static int countOnes(int x) {
        x = (x & 0x55555555) + ((x >> 1) & 0x55555555);
        x = (x & 0x33333333) + ((x >> 2) & 0x33333333);
        x = (x & 0x0f0f0f0f) + ((x >> 4) & 0x0f0f0f0f);
        x = (x & 0x00ff00ff) + ((x >> 8) & 0x00ff00ff);
        x = (x & 0x0000ffff) + ((x >> 16) & 0x0000ffff);
        return x;
    }

    // Count differing bits = number of 1s in A XOR B
    /** Returns the bits to convert. */
    public static int bitsToConvert(int a, int b) {
        return countOnes(a ^ b);
    }

    /** Application entry point. */
    public static void main(String[] args) {
        System.out.println(bitsToConvert(31, 14)); // 2
        System.out.println(bitsToConvert(7, 14));  // 3 (0111 ^ 1110 = 1001)
    }
}
