package com.org.interview;

// Q8.3 Write a method that returns all subsets of a set.
public class Q36_Subsets {

    // Bit manipulation approach - iterate all 2^n combinations
    public static int[][] getSubsets(int[] a) {
        int n = a.length;
        int total = 1 << n;
        int[][] subsets = new int[total][];
        for (int i = 0; i < total; i++) {
            int count = 0;
            for (int j = 0; j < n; j++) if ((i >> j & 1) == 1) count++;
            subsets[i] = new int[count];
            int idx = 0;
            for (int j = 0; j < n; j++) {
                if ((i >> j & 1) == 1) subsets[i][idx++] = a[j];
            }
        }
        return subsets;
    }

    // Recursive approach
    public static int[][] getSubsetsRecursive(int[] a, int idx) {
        if (idx == a.length) return new int[][] {new int[0]};
        int[][] rest = getSubsetsRecursive(a, idx + 1);
        int[][] result = new int[rest.length * 2][];
        for (int i = 0; i < rest.length; i++) {
            result[i] = rest[i];
            result[rest.length + i] = new int[rest[i].length + 1];
            result[rest.length + i][0] = a[idx];
            for (int j = 0; j < rest[i].length; j++) {
                result[rest.length + i][j + 1] = rest[i][j];
            }
        }
        return result;
    }

    private static void printSubsets(int[][] subsets) {
        for (int[] subset : subsets) {
            System.out.print("[");
            for (int i = 0; i < subset.length; i++) {
                System.out.print(subset[i]);
                if (i < subset.length - 1) System.out.print(",");
            }
            System.out.println("]");
        }
    }

    /** Application entry point. */
    public static void main(String[] args) {
        int[] a = {1, 2, 3};
        System.out.println("=== Bit manipulation ===");
        printSubsets(getSubsets(a));
        System.out.println("=== Recursive ===");
        printSubsets(getSubsetsRecursive(a, 0));
    }
}
