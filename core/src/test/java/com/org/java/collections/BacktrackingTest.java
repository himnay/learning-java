package com.org.java.collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Backtracking - O(n!), O(2^n) complexity patterns")
class BacktrackingTest {

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(n!) — Permutations")
    class Permutations {

        @Test
        @DisplayName("All permutations of array  →  O(n! * n) time")
        void allPermutations() {
            List<List<Integer>> result = new ArrayList<>();
            permute(new int[]{1, 2, 3}, 0, result);
            assertEquals(6, result.size()); // 3! = 6
            assertTrue(result.contains(Arrays.asList(1, 2, 3)));
            assertTrue(result.contains(Arrays.asList(3, 2, 1)));
        }

        private void permute(int[] nums, int start, List<List<Integer>> result) {
            if (start == nums.length) {
                List<Integer> perm = new ArrayList<>();
                for (int n : nums) perm.add(n);
                result.add(perm);
                return;
            }
            for (int i = start; i < nums.length; i++) {
                swap(nums, start, i);
                permute(nums, start + 1, result);
                swap(nums, start, i); // backtrack
            }
        }

        private void swap(int[] a, int i, int j) { int t = a[i]; a[i] = a[j]; a[j] = t; }

        @Test
        @DisplayName("Letter combinations of phone digits  →  O(4^n * n)")
        void letterCombinations() {
            String[] phone = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
            List<String> result = new ArrayList<>();
            if (!("23").isEmpty()) letterCombine("23", 0, "", phone, result);
            assertEquals(9, result.size()); // 3*3 = 9
        }

        private void letterCombine(String digits, int idx, String cur,
                                   String[] phone, List<String> result) {
            if (idx == digits.length()) { result.add(cur); return; }
            String letters = phone[digits.charAt(idx) - '0'];
            for (char c : letters.toCharArray()) letterCombine(digits, idx + 1, cur + c, phone, result);
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(2^n) — Subsets / combinations")
    class SubsetsCombinations {

        @Test
        @DisplayName("All subsets  →  O(2^n) time and space")
        void allSubsets() {
            List<List<Integer>> result = new ArrayList<>();
            subsets(new int[]{1, 2, 3}, 0, new ArrayList<>(), result);
            assertEquals(8, result.size()); // 2^3
        }

        private void subsets(int[] nums, int idx, List<Integer> cur, List<List<Integer>> result) {
            result.add(new ArrayList<>(cur));
            for (int i = idx; i < nums.length; i++) {
                cur.add(nums[i]);
                subsets(nums, i + 1, cur, result);
                cur.remove(cur.size() - 1); // backtrack
            }
        }

        @Test
        @DisplayName("Combination sum (target with repetition)  →  O(2^(target/min))")
        void combinationSum() {
            List<List<Integer>> result = new ArrayList<>();
            combinationSum(new int[]{2, 3, 6, 7}, 7, 0, new ArrayList<>(), result);
            assertEquals(2, result.size()); // [2,2,3], [7]
        }

        private void combinationSum(int[] candidates, int remain, int start,
                                    List<Integer> cur, List<List<Integer>> result) {
            if (remain == 0) { result.add(new ArrayList<>(cur)); return; }
            for (int i = start; i < candidates.length; i++) {
                if (candidates[i] <= remain) {
                    cur.add(candidates[i]);
                    combinationSum(candidates, remain - candidates[i], i, cur, result);
                    cur.remove(cur.size() - 1);
                }
            }
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Backtracking constraint satisfaction")
    class ConstraintProblems {

        @Test
        @DisplayName("N-Queens — place N queens on N×N board  →  O(n!)")
        void nQueens() {
            List<List<String>> result = new ArrayList<>();
            int n = 4;
            int[] queens = new int[n]; // queens[row] = col
            Arrays.fill(queens, -1);
            solveNQueens(queens, 0, n, result);
            assertEquals(2, result.size()); // 4-queens has 2 solutions
        }

        private void solveNQueens(int[] queens, int row, int n, List<List<String>> result) {
            if (row == n) {
                List<String> board = new ArrayList<>();
                for (int r = 0; r < n; r++) {
                    char[] rowChars = new char[n];
                    Arrays.fill(rowChars, '.');
                    rowChars[queens[r]] = 'Q';
                    board.add(new String(rowChars));
                }
                result.add(board);
                return;
            }
            for (int col = 0; col < n; col++) {
                if (isSafe(queens, row, col)) {
                    queens[row] = col;
                    solveNQueens(queens, row + 1, n, result);
                    queens[row] = -1; // backtrack
                }
            }
        }

        private boolean isSafe(int[] queens, int row, int col) {
            for (int r = 0; r < row; r++) {
                if (queens[r] == col || Math.abs(queens[r] - col) == Math.abs(r - row))
                    return false;
            }
            return true;
        }

        @Test
        @DisplayName("Sudoku validator (constraint check)  →  O(9^m) where m=empty cells")
        void sudokuValid() {
            char[][] board = {
                {'5','3','.','.','7','.','.','.','.'},
                {'6','.','.','1','9','5','.','.','.'},
                {'.','9','8','.','.','.','.','6','.'},
                {'8','.','.','.','6','.','.','.','3'},
                {'4','.','.','8','.','3','.','.','1'},
                {'7','.','.','.','2','.','.','.','6'},
                {'.','6','.','.','.','.','2','8','.'},
                {'.','.','.','4','1','9','.','.','5'},
                {'.','.','.','.','8','.','.','7','9'}
            };
            assertTrue(isValidSudoku(board));
        }

        private boolean isValidSudoku(char[][] board) {
            for (int i = 0; i < 9; i++) {
                boolean[] row = new boolean[10], col = new boolean[10], box = new boolean[10];
                for (int j = 0; j < 9; j++) {
                    if (board[i][j] != '.') {
                        int v = board[i][j] - '0';
                        if (row[v]) return false; row[v] = true;
                    }
                    if (board[j][i] != '.') {
                        int v = board[j][i] - '0';
                        if (col[v]) return false; col[v] = true;
                    }
                    int r = 3*(i/3) + j/3, c = 3*(i%3) + j%3;
                    if (board[r][c] != '.') {
                        int v = board[r][c] - '0';
                        if (box[v]) return false; box[v] = true;
                    }
                }
            }
            return true;
        }
    }
}
