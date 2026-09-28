package com.org.interview;

// Q5.3 Given an integer, print the next smallest and next largest number that have
// the same number of 1 bits in their binary representation.
public class Q30_NextNumber {

    /** Counts ones. */
    public static int countOnes(int x) {
        x = (x & 0x55555555) + ((x >> 1) & 0x55555555);
        x = (x & 0x33333333) + ((x >> 2) & 0x33333333);
        x = (x & 0x0f0f0f0f) + ((x >> 4) & 0x0f0f0f0f);
        x = (x & 0x00ff00ff) + ((x >> 8) & 0x00ff00ff);
        x = (x & 0x0000ffff) + ((x >> 16) & 0x0000ffff);
        return x;
    }

    // Next larger number with the same number of 1s, or -1 if there is none (n must be positive).
    // Flip the rightmost non-trailing 0 to 1, then pack the 1s to its right as low as possible.
    // c0 = trailing 0s and c1 = the block of 1s just left of them, so p = c0 + c1 is that 0's position.
    public static int getNext(int n) {
        if (n <= 0) return -1;
        int tmp = n;
        int c0 = 0, c1 = 0;
        // count trailing 0s
        while ((tmp & 1) == 0 && tmp != 0) { c0++; tmp >>= 1; }
        // count the 1s to the left of them
        while ((tmp & 1) == 1) { c1++; tmp >>= 1; }
        int p = c0 + c1; // position of rightmost non-trailing 0
        if (p == 31) return -1;                // the only 0 left is the sign bit
        n |= (1 << p);                         // flip rightmost non-trailing 0
        n &= ~((1 << p) - 1);                  // clear bits to the right of p
        n |= (1 << (c1 - 1)) - 1;              // insert (c1-1) ones on the right
        return n;
    }

    // Previous smaller number with the same number of 1s, or -1 if there is none (n must be positive).
    // Flip the rightmost non-trailing 1 to 0, then pack the 1s to its right as high as possible.
    // c1 = trailing 1s and c0 = the block of 0s just left of them, so p = c0 + c1 is that 1's position.
    public static int getPrev(int n) {
        if (n <= 0) return -1;
        int tmp = n;
        int c0 = 0, c1 = 0;
        // count trailing 1s
        while ((tmp & 1) == 1) { c1++; tmp >>= 1; }
        if (tmp == 0) return -1;               // all the 1s are trailing: nothing smaller has as many
        // count the 0s to the left of them
        while ((tmp & 1) == 0) { c0++; tmp >>= 1; }
        int p = c0 + c1; // position of rightmost non-trailing 1
        n &= ~0 << (p + 1);                    // clear bits from p downward
        int mask = (1 << (c1 + 1)) - 1;        // c1+1 ones
        n |= mask << (c0 - 1);
        return n;
    }

    /** Application entry point. */
    public static void main(String[] args) {
        int a = 948;
        System.out.println("Original:  " + Integer.toBinaryString(a) + " (" + a + ")");
        System.out.println("Next:      " + Integer.toBinaryString(getNext(a)) + " (" + getNext(a) + ")");
        System.out.println("Previous:  " + Integer.toBinaryString(getPrev(a)) + " (" + getPrev(a) + ")");
    }
}
