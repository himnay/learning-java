package com.org.interview;

// Q5.7 An array A[1..n] contains all integers from 0 to n except one missing number.
// We can only access individual bits via fetch(A, i, j).
// Write code to find the missing integer. Can you do it in O(n) time?
public class Q33_MissingNumber {

    // Simulate "fetch the j-th bit of A[i]" - O(1) per call
    private static int fetch(int[] a, int i, int j) {
        return (a[i] >> j) & 1;
    }

    // Reconstruct element from bit fetches
    private static int get(int[] a, int i) {
        int ret = 0;
        for (int j = 30; j >= 0; j--) {
            ret = (ret << 1) | fetch(a, i, j);
        }
        return ret;
    }

    // O(n) approach: for each bit position, count how many numbers have that bit set.
    // Compare with expected count to narrow down the missing number bit by bit.
    /** Finds missing. */
    public static int findMissing(int[] a) {
        return findMissingHelper(a, 0, a.length - 1, 0);
    }

    private static int findMissingHelper(int[] a, int low, int high, int bit) {
        if (bit > 30) return 0;
        // Count elements with bit=0 and bit=1 in current range
        int[] zeros = new int[high - low + 1]; int zLen = 0;
        int[] ones  = new int[high - low + 1]; int oLen = 0;
        for (int i = low; i <= high; i++) {
            if (fetch(a, i, bit) == 0) zeros[zLen++] = a[i];
            else ones[oLen++] = a[i];
        }
        // In a complete sequence 0..n, zeros in bit position 'bit' are ceil((n+1)/2)
        // If zeros count is less than expected, the missing number has this bit = 0
        if (zLen <= oLen) {
            // missing number has bit=0, recurse into zeros group
            return findMissingHelper(zeros, 0, zLen - 1, bit + 1);
        } else {
            // missing number has bit=1, recurse into ones group
            return (1 << bit) | findMissingHelper(ones, 0, oLen - 1, bit + 1);
        }
    }

    /** Application entry point. */
    public static void main(String[] args) {
        // Array contains 0-10, missing 7
        int[] a = {0, 1, 2, 3, 4, 5, 6, 8, 9, 10};
        System.out.println("Missing: " + findMissing(a)); // 7

        // XOR approach (simpler, also O(n))
        int xorAll = 0;
        for (int i = 0; i <= a.length; i++) xorAll ^= i;
        for (int v : a) xorAll ^= v;
        System.out.println("Missing (XOR): " + xorAll); // 7
    }
}
