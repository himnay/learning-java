package com.org.java.collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("2D Dynamic Programming - Complexity Reference")
class DynamicProgramming2DTest {

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(m*n) — Grid DP")
    class GridDP {

        @Test
        @DisplayName("Unique paths on grid  →  O(m*n) time and space")
        void uniquePaths() {
            assertEquals(28, uniquePaths(3, 7));
            assertEquals(6, uniquePaths(3, 3));
            assertEquals(1, uniquePaths(1, 1));
        }

        private int uniquePaths(int m, int n) {
            int[][] dp = new int[m][n];
            for (int i = 0; i < m; i++) dp[i][0] = 1;
            for (int j = 0; j < n; j++) dp[0][j] = 1;
            for (int i = 1; i < m; i++)
                for (int j = 1; j < n; j++)
                    dp[i][j] = dp[i-1][j] + dp[i][j-1];
            return dp[m-1][n-1];
        }

        @Test
        @DisplayName("Minimum path sum in grid  →  O(m*n)")
        void minPathSum() {
            int[][] grid = {{1,3,1},{1,5,1},{4,2,1}};
            assertEquals(7, minPathSum(grid));
        }

        private int minPathSum(int[][] grid) {
            int m = grid.length, n = grid[0].length;
            int[][] dp = new int[m][n];
            dp[0][0] = grid[0][0];
            for (int i = 1; i < m; i++) dp[i][0] = dp[i-1][0] + grid[i][0];
            for (int j = 1; j < n; j++) dp[0][j] = dp[0][j-1] + grid[0][j];
            for (int i = 1; i < m; i++)
                for (int j = 1; j < n; j++)
                    dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]) + grid[i][j];
            return dp[m-1][n-1];
        }

        @Test
        @DisplayName("Triangle minimum path top to bottom  →  O(n²)")
        void triangleMinPath() {
            int[][] triangle = {{2},{3,4},{6,5,7},{4,1,8,3}};
            int n = triangle.length;
            int[] dp = triangle[n-1].clone();
            for (int i = n-2; i >= 0; i--)
                for (int j = 0; j <= i; j++)
                    dp[j] = triangle[i][j] + Math.min(dp[j], dp[j+1]);
            assertEquals(11, dp[0]);
        }

        @Test
        @DisplayName("Maximal square of 1s in binary matrix  →  O(m*n)")
        void maximalSquare() {
            char[][] matrix = {
                {'1','0','1','0','0'},
                {'1','0','1','1','1'},
                {'1','1','1','1','1'},
                {'1','0','0','1','0'}
            };
            assertEquals(4, maximalSquare(matrix));
        }

        private int maximalSquare(char[][] matrix) {
            int m = matrix.length, n = matrix[0].length, maxSide = 0;
            int[][] dp = new int[m][n];
            for (int i = 0; i < m; i++) for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    dp[i][j] = (i == 0 || j == 0) ? 1
                        : Math.min(dp[i-1][j], Math.min(dp[i][j-1], dp[i-1][j-1])) + 1;
                    maxSide = Math.max(maxSide, dp[i][j]);
                }
            }
            return maxSide * maxSide;
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(m*n) — String DP")
    class StringDP {

        @Test
        @DisplayName("Longest Common Subsequence (LCS)  →  O(m*n) time and space")
        void lcs() {
            assertEquals(4, lcs("ABCBDAB", "BDCABA"));
            assertEquals(3, lcs("abcde", "ace"));
            assertEquals(0, lcs("abc", "xyz"));
        }

        private int lcs(String s1, String s2) {
            int m = s1.length(), n = s2.length();
            int[][] dp = new int[m+1][n+1];
            for (int i = 1; i <= m; i++) for (int j = 1; j <= n; j++) {
                if (s1.charAt(i-1) == s2.charAt(j-1)) dp[i][j] = dp[i-1][j-1] + 1;
                else dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
            }
            return dp[m][n];
        }

        @Test
        @DisplayName("Edit distance (Levenshtein)  →  O(m*n) time and space")
        void editDistance() {
            assertEquals(3, editDistance("horse", "ros"));
            assertEquals(5, editDistance("intention", "execution"));
            assertEquals(0, editDistance("abc", "abc"));
        }

        private int editDistance(String word1, String word2) {
            int m = word1.length(), n = word2.length();
            int[][] dp = new int[m+1][n+1];
            for (int i = 0; i <= m; i++) dp[i][0] = i;
            for (int j = 0; j <= n; j++) dp[0][j] = j;
            for (int i = 1; i <= m; i++) for (int j = 1; j <= n; j++) {
                if (word1.charAt(i-1) == word2.charAt(j-1)) dp[i][j] = dp[i-1][j-1];
                else dp[i][j] = 1 + Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1]));
            }
            return dp[m][n];
        }

        @Test
        @DisplayName("Longest palindromic subsequence  →  O(n²)")
        void longestPalindromicSubsequence() {
            assertEquals(4, lpSubseq("bbbab")); // "bbbb"
            assertEquals(2, lpSubseq("cbbd"));
        }

        private int lpSubseq(String s) {
            int n = s.length();
            int[][] dp = new int[n][n];
            for (int i = 0; i < n; i++) dp[i][i] = 1;
            for (int len = 2; len <= n; len++) for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                if (s.charAt(i) == s.charAt(j)) dp[i][j] = dp[i+1][j-1] + 2;
                else dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1]);
            }
            return dp[0][n-1];
        }

        @Test
        @DisplayName("Regular expression matching  →  O(m*n)")
        void regexMatch() {
            assertTrue(isMatch("aa", "a*"));
            assertTrue(isMatch("aab", "c*a*b"));
            assertFalse(isMatch("mississippi", "mis*is*p*."));
        }

        private boolean isMatch(String s, String p) {
            int m = s.length(), n = p.length();
            boolean[][] dp = new boolean[m+1][n+1];
            dp[0][0] = true;
            for (int j = 1; j <= n; j++) if (p.charAt(j-1) == '*') dp[0][j] = dp[0][j-2];
            for (int i = 1; i <= m; i++) for (int j = 1; j <= n; j++) {
                char pc = p.charAt(j-1), sc = s.charAt(i-1);
                if (pc == '*') {
                    dp[i][j] = dp[i][j-2]; // zero occurrences
                    if (p.charAt(j-2) == '.' || p.charAt(j-2) == sc) dp[i][j] |= dp[i-1][j];
                } else {
                    dp[i][j] = dp[i-1][j-1] && (pc == '.' || pc == sc);
                }
            }
            return dp[m][n];
        }
    }
}
