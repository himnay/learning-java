package com.org.interview;

// Q8.8 Eight Queens: print all ways of arranging 8 queens on a chess board so that
// none of them share the same row, column, or diagonal.
public class Q41_EightQueens {

    private static final int N = 8;
    private static int[] col = new int[N]; // col[r] = column of queen in row r
    private static int count = 0;

    private static void print() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(j == col[i] ? "1 " : "0 ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void search(int row) {
        if (row == N) {
            print();
            count++;
            return;
        }
        for (int c = 0; c < N; c++) {
            col[row] = c;
            boolean valid = true;
            for (int r = 0; r < row; r++) {
                // same column or same diagonal
                if (col[r] == c || row - r == c - col[r] || row - r == col[r] - c) {
                    valid = false;
                    break;
                }
            }
            if (valid) search(row + 1);
        }
    }

    /** Application entry point. */
    public static void main(String[] args) {
        search(0);
        System.out.println("Total solutions: " + count); // 92
    }
}
