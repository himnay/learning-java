package com.org.interview.ch05;

// Q5.6 Write a program to swap odd and even bits in an integer with as few instructions as possible.
// (bit 0 and bit 1 are swapped, bit 2 and bit 3 are swapped, etc.)
public class Q5_6_PairwiseSwap {

    // 0x55555555 = 01010101...01 (even bit positions)
    // 0xAAAAAAAA = 10101010...10 (odd bit positions)
    public static int swapBits(int x) {
        return ((x & 0x55555555) << 1) | ((x >>> 1) & 0x55555555);
    }

    public static String toBinaryString32(int n) {
        char[] bits = new char[32];
        for (int i = 31; i >= 0; i--) {
            bits[31 - i] = ((n >>> i) & 1) == 1 ? '1' : '0';
        }
        return new String(bits);
    }

    public static void main(String[] args) {
        int x = 0b10110010; // 178
        System.out.println("Original: " + toBinaryString32(x));
        System.out.println("Swapped:  " + toBinaryString32(swapBits(x)));

        // Verify: swap should be its own inverse
        System.out.println("Double-swap equals original: " + (swapBits(swapBits(x)) == x));
    }
}
