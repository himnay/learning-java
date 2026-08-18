package com.org.interview;

// Q5.1 You have two 32-bit numbers N and M, and two bit positions i and j.
// Set all bits between i and j in N equal to M
// (M becomes a substring of N located at i and starting at j).
public class Q28_Insertion {

    /** Updates bits. */
    public static int updateBits(int n, int m, int i, int j) {
        int allOnes = ~0;
        int left = allOnes << (j + 1);          // 1s before position j+1
        int right = (1 << i) - 1;               // 1s after position i
        int mask = left | right;                 // 0s between i and j
        return (n & mask) | (m << i);
    }

    /** Converts this object to binary string. */
    public static String toBinaryString(int n) {
        if (n == 0) return "0";
        char[] bits = new char[32];
        int pos = 31;
        // handle sign bit separately for negative numbers
        for (int i = 31; i >= 0; i--) {
            bits[pos--] = ((n >>> i) & 1) == 1 ? '1' : '0';
        }
        // trim leading zeros
        int start = 0;
        while (start < 31 && bits[start] == '0') start++;
        return new String(bits, start, 32 - start);
    }

    /** Application entry point. */
    public static void main(String[] args) {
        int n = 1 << 10; // 10000000000
        int m = 21;      // 10101
        int ans = updateBits(n, m, 2, 6);
        System.out.println("N = " + toBinaryString(n));
        System.out.println("M = " + toBinaryString(m));
        System.out.println("Result = " + toBinaryString(ans));
    }
}
