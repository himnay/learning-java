package com.org.interview.ch01;

// Q1.7 Write an algorithm such that if an element in an M*N matrix is 0,
// its entire row and column is set to 0.
public class Q1_7_ZeroMatrix {

    public static void zero(int[][] a, int m, int n) {
        boolean[] row = new boolean[m];
        boolean[] col = new boolean[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (a[i][j] == 0) {
                    row[i] = true;
                    col[j] = true;
                }
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (row[i] || col[j]) a[i][j] = 0;
            }
        }
    }

    private static void print(int[][] a, int m, int n) {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%3d", a[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] a = {
            {1, 2, 3, 4},
            {5, 0, 7, 8},
            {9, 10, 11, 0},
            {13, 14, 15, 16}
        };
        System.out.println("Before:");
        print(a, 4, 4);
        zero(a, 4, 4);
        System.out.println("After:");
        print(a, 4, 4);
    }
}
