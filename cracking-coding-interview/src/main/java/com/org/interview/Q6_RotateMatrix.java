package com.org.interview;

// Q1.6 Given an image represented by an N*N matrix, write a method to rotate the image by 90 degrees.
// Can you do this in place?
//
// Rotated clockwise:
//
//   1   2   3   4          13   9   5   1
//   5   6   7   8    -->   14  10   6   2
//   9  10  11  12          15  11   7   3
//  13  14  15  16          16  12   8   4
public class Q6_RotateMatrix {

    // Transpose, then reverse each row: a clockwise turn, in place, O(n^2)
    // (reversing each column instead of each row turns it counter-clockwise)
    /** Handles rotate. */
    public static void rotate(int[][] a, int n) {
        // transpose
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int tmp = a[i][j];
                a[i][j] = a[j][i];
                a[j][i] = tmp;
            }
        }
        // reverse each row
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n / 2; j++) {
                int tmp = a[i][j];
                a[i][j] = a[i][n - 1 - j];
                a[i][n - 1 - j] = tmp;
            }
        }
    }

    private static void print(int[][] a, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%4d", a[i][j]);
            }
            System.out.println();
        }
    }

    /** Application entry point. */
    public static void main(String[] args) {
        int[][] a = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 16}
        };
        System.out.println("Before:");
        print(a, 4);
        rotate(a, 4);
        System.out.println("After 90° rotation:");
        print(a, 4);
    }
}
