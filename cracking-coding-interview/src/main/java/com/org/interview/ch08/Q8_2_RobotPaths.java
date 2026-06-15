package com.org.interview.ch08;

// Q8.2 A robot sits on the upper left corner of an N*N grid.
// The robot can only move right and down. Some cells may be blocked.
// How many paths are there? Find one path if it exists.
public class Q8_2_RobotPaths {

    // Count all paths - O(m*n) with memoization
    public static long countPaths(int m, int n) {
        if (m == 1 || n == 1) return 1;
        return countPaths(m - 1, n) + countPaths(m, n - 1);
    }

    // Count paths on grid with obstacles (0=blocked, 1=open)
    private static int M, N;
    private static boolean[][] grid;
    private static int[][] path;
    private static int pathLen;

    public static boolean findPath(int m, int n, int[][] route, int len) {
        if (m <= 0 || n <= 0 || !grid[m - 1][n - 1]) return false;
        if (m == 1 && n == 1) {
            route[len][0] = m; route[len][1] = n;
            pathLen = len + 1;
            return true;
        }
        if (findPath(m - 1, n, route, len) || findPath(m, n - 1, route, len)) {
            route[len][0] = m; route[len][1] = n;
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("Count paths 3x3: " + countPaths(3, 3)); // 6
        System.out.println("Count paths 4x4: " + countPaths(4, 4)); // 20

        // Find one path on a 4x4 grid with obstacles
        M = 4; N = 4;
        grid = new boolean[][]{
            {true,  true,  true,  true},
            {true,  false, true,  true},
            {true,  true,  true,  false},
            {true,  true,  true,  true}
        };
        path = new int[M + N][2];
        pathLen = 0;
        boolean found = findPath(M, N, path, 0);
        if (found) {
            System.out.print("Path found: ");
            for (int i = pathLen - 1; i >= 0; i--)
                System.out.print("(" + path[i][0] + "," + path[i][1] + ") ");
            System.out.println();
        } else {
            System.out.println("No path exists");
        }
    }
}
