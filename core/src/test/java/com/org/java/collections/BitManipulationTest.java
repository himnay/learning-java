package com.org.java.collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Bit Manipulation - O(1) and O(n) operations")
class BitManipulationTest {

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(1) — Single-number bit tricks")
    class SingleBitOps {

        @Test
        @DisplayName("Check if bit i is set  →  (n >> i) & 1  →  O(1)")
        void checkBit() {
            int n = 0b1010; // 10
            assertTrue(((n >> 1) & 1) == 1);  // bit 1 set
            assertFalse(((n >> 0) & 1) == 1); // bit 0 not set
            assertTrue(((n >> 3) & 1) == 1);  // bit 3 set
        }

        @Test
        @DisplayName("Set bit i  →  n | (1 << i)  →  O(1)")
        void setBit() {
            int n = 0b1000; // 8
            int result = n | (1 << 2); // set bit 2
            assertEquals(0b1100, result); // 12
        }

        @Test
        @DisplayName("Clear bit i  →  n & ~(1 << i)  →  O(1)")
        void clearBit() {
            int n = 0b1111; // 15
            int result = n & ~(1 << 2); // clear bit 2
            assertEquals(0b1011, result); // 11
        }

        @Test
        @DisplayName("Toggle bit i  →  n ^ (1 << i)  →  O(1)")
        void toggleBit() {
            int n = 0b1010;
            assertEquals(0b1110, n ^ (1 << 2)); // toggle bit 2 OFF->ON
            assertEquals(0b1000, n ^ (1 << 1)); // toggle bit 1 ON->OFF
        }

        @Test
        @DisplayName("Check even/odd  →  n & 1  →  O(1)")
        void evenOdd() {
            assertTrue((4 & 1) == 0);  // even
            assertTrue((7 & 1) == 1);  // odd
        }

        @Test
        @DisplayName("Check power of 2  →  n & (n-1) == 0  →  O(1)")
        void powerOfTwo() {
            assertTrue(isPowerOfTwo(16));
            assertTrue(isPowerOfTwo(1));
            assertFalse(isPowerOfTwo(12));
            assertFalse(isPowerOfTwo(0));
        }

        private boolean isPowerOfTwo(int n) {
            return n > 0 && (n & (n - 1)) == 0;
        }

        @Test
        @DisplayName("Multiply/divide by 2  →  << and >>  →  O(1)")
        void shiftOps() {
            assertEquals(20, 5 << 2);  // 5 * 4 = 20
            assertEquals(3, 12 >> 2);  // 12 / 4 = 3
        }

        @Test
        @DisplayName("Swap two numbers without temp  →  XOR swap  →  O(1)")
        void xorSwap() {
            int a = 5, b = 9;
            a ^= b; b ^= a; a ^= b;
            assertEquals(9, a);
            assertEquals(5, b);
        }

        @Test
        @DisplayName("Get lowest set bit  →  n & (-n)  →  O(1)")
        void lowestSetBit() {
            int n = 0b1100; // 12
            assertEquals(0b0100, n & (-n)); // lowest set bit = 4
        }

        @Test
        @DisplayName("Remove lowest set bit  →  n & (n-1)  →  O(1)")
        void removeLowestSetBit() {
            int n = 0b1100; // 12
            assertEquals(0b1000, n & (n - 1)); // removes bit 2, leaves bit 3
        }

        @Test
        @DisplayName("Absolute value without branching  →  O(1)")
        void absoluteValue() {
            int n = -7;
            int mask = n >> 31;         // all 0s for positive, all 1s for negative
            int abs = (n + mask) ^ mask;
            assertEquals(7, abs);
            assertEquals(7, (7 + 0) ^ 0); // positive: unchanged
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(log n) — Bit counting operations")
    class BitCounting {

        @Test
        @DisplayName("Count set bits (Brian Kernighan)  →  O(number of set bits)")
        void countSetBits() {
            assertEquals(3, countBits(7));    // 0b111
            assertEquals(1, countBits(16));   // 0b10000
            assertEquals(4, countBits(15));   // 0b1111
        }

        private int countBits(int n) {
            int count = 0;
            while (n != 0) { n &= (n - 1); count++; } // remove lowest bit each time
            return count;
        }

        @Test
        @DisplayName("Integer bit count  →  Integer.bitCount  →  O(1) hardware)")
        void javaBitCount() {
            assertEquals(3, Integer.bitCount(7));
            assertEquals(4, Integer.bitCount(15));
        }

        @Test
        @DisplayName("Find highest set bit position  →  O(log n)")
        void highestSetBit() {
            assertEquals(3, highestBit(8));  // 2^3 = 8
            assertEquals(4, highestBit(16)); // 2^4 = 16
        }

        private int highestBit(int n) {
            int pos = 0;
            while ((n >>= 1) != 0) pos++;
            return pos;
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(n) — Array-based bit manipulation")
    class ArrayBitOps {

        @Test
        @DisplayName("Single number (XOR all elements, pairs cancel)  →  O(n)")
        void singleNumber() {
            int[] nums = {4, 1, 2, 1, 2};
            int result = 0;
            for (int n : nums) result ^= n; // pairs cancel, unique survives
            assertEquals(4, result);
        }

        @Test
        @DisplayName("Find two non-repeating numbers (XOR partitioning)  →  O(n)")
        void twoSingleNumbers() {
            int[] nums = {1, 2, 1, 3, 2, 5};
            int xor = 0;
            for (int n : nums) xor ^= n;    // xor = 3 ^ 5
            int bit = xor & (-xor);          // rightmost differing bit
            int a = 0, b = 0;
            for (int n : nums) { if ((n & bit) != 0) a ^= n; else b ^= n; }
            assertTrue((a == 3 && b == 5) || (a == 5 && b == 3));
        }

        @Test
        @DisplayName("Count bits for 0..n (DP with bit)  →  O(n)")
        void countBits0toN() {
            int n = 5;
            int[] ans = new int[n + 1];
            for (int i = 1; i <= n; i++) ans[i] = ans[i >> 1] + (i & 1);
            assertArrayEquals(new int[]{0, 1, 1, 2, 1, 2}, ans);
        }

        @Test
        @DisplayName("Reverse bits of 32-bit integer  →  O(1) or O(32)")
        void reverseBits() {
            int n = 0b00000010100101000001111010011100;
            assertEquals(0b00111001011110000010100101000000, reverseBits(n));
        }

        private int reverseBits(int n) {
            int result = 0;
            for (int i = 0; i < 32; i++) { result = (result << 1) | (n & 1); n >>= 1; }
            return result;
        }

        @Test
        @DisplayName("Sum of two integers without + operator  →  O(1)")
        void addWithoutPlus() {
            assertEquals(5, addBits(3, 2));
            assertEquals(0, addBits(-1, 1));
        }

        private int addBits(int a, int b) {
            while (b != 0) { int carry = (a & b) << 1; a ^= b; b = carry; }
            return a;
        }
    }
}
