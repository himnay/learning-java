package com.org.interview;

// Q9.6 Given a matrix where each row and each column is sorted, find an element.
// Start from top-right corner: eliminate a row or column at each step - O(m+n).
public class Q46_SortedMatrixSearch {

    /** Searches. */
    public static int[] search(int[][] d, int m, int n, int x) {
        int r = 0, c = n - 1;
        while (r < m && c >= 0) {
            if (d[r][c] == x) return new int[]{r, c};
            else if (d[r][c] < x) r++;
            else c--;
        }
        return new int[]{-1, -1};
    }

    /** Application entry point. */
    public static void main(String[] args) {
        int[][] d = {
            { 1,  5,  7, 10},
            { 2,  6, 11, 13},
            { 4,  8, 12, 15},
            { 9, 14, 16, 19}
        };
        int[] pos = search(d, 4, 4, 11);
        System.out.println("Found 11 at: [" + pos[0] + "," + pos[1] + "]"); // [1,2]
        pos = search(d, 4, 4, 13);
        System.out.println("Found 13 at: [" + pos[0] + "," + pos[1] + "]"); // [1,3]
        pos = search(d, 4, 4, 99);
        System.out.println("Found 99 at: [" + pos[0] + "," + pos[1] + "]"); // [-1,-1]
    }
}
