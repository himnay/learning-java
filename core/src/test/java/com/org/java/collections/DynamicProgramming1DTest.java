package com.org.java.collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("1D Dynamic Programming - Complexity Reference")
class DynamicProgramming1DTest {

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(n) — Classic 1D DP")
    class Classic1D {

        @Test
        @DisplayName("Fibonacci (bottom-up DP)  →  O(n) time, O(1) space")
        void fibonacci() {
            assertEquals(55, fib(10));
            assertEquals(1, fib(1));
            assertEquals(0, fib(0));
        }

        private int fib(int n) {
            if (n <= 1) return n;
            int a = 0, b = 1;
            for (int i = 2; i <= n; i++) { int c = a + b; a = b; b = c; }
            return b;
        }

        @Test
        @DisplayName("Climbing stairs (k steps)  →  O(n) time, O(1) space")
        void climbStairs() {
            assertEquals(1, climbStairs(1));
            assertEquals(2, climbStairs(2));
            assertEquals(3, climbStairs(3));
            assertEquals(5, climbStairs(4));
        }

        private int climbStairs(int n) {
            if (n <= 2) return n;
            int a = 1, b = 2;
            for (int i = 3; i <= n; i++) { int c = a + b; a = b; b = c; }
            return b;
        }

        @Test
        @DisplayName("House robber (non-adjacent max sum)  →  O(n) time, O(1) space")
        void houseRobber() {
            assertEquals(4, rob(new int[]{1, 2, 3, 1}));
            assertEquals(12, rob(new int[]{2, 7, 9, 3, 1}));
        }

        private int rob(int[] nums) {
            int prev2 = 0, prev1 = 0;
            for (int n : nums) { int cur = Math.max(prev1, prev2 + n); prev2 = prev1; prev1 = cur; }
            return prev1;
        }

        @Test
        @DisplayName("Min cost climbing stairs  →  O(n) time, O(1) space")
        void minCostClimbing() {
            assertEquals(15, minCostClimbing(new int[]{10, 15, 20}));
            assertEquals(6, minCostClimbing(new int[]{1, 100, 1, 1, 1, 100, 1, 1, 100, 1}));
        }

        private int minCostClimbing(int[] cost) {
            int n = cost.length;
            int prev2 = 0, prev1 = 0;
            for (int i = 2; i <= n; i++) {
                int cur = Math.min(prev1 + cost[i-1], prev2 + cost[i-2]);
                prev2 = prev1; prev1 = cur;
            }
            return prev1;
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(n) — Subarray / prefix DP")
    class SubarrayDP {

        @Test
        @DisplayName("Maximum subarray (Kadane's algorithm)  →  O(n)")
        void maxSubarray() {
            assertEquals(6, maxSubarray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
            assertEquals(1, maxSubarray(new int[]{-1}));
        }

        private int maxSubarray(int[] nums) {
            int maxSum = nums[0], curSum = nums[0];
            for (int i = 1; i < nums.length; i++) {
                curSum = Math.max(nums[i], curSum + nums[i]);
                maxSum = Math.max(maxSum, curSum);
            }
            return maxSum;
        }

        @Test
        @DisplayName("Maximum product subarray  →  O(n)")
        void maxProductSubarray() {
            assertEquals(6, maxProduct(new int[]{2, 3, -2, 4}));
            assertEquals(0, maxProduct(new int[]{-2, 0, -1}));
        }

        private int maxProduct(int[] nums) {
            int maxP = nums[0], minP = nums[0], result = nums[0];
            for (int i = 1; i < nums.length; i++) {
                int[] cands = {nums[i], maxP * nums[i], minP * nums[i]};
                maxP = Arrays.stream(cands).max().getAsInt();
                minP = Arrays.stream(cands).min().getAsInt();
                result = Math.max(result, maxP);
            }
            return result;
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(n²) — Subsequence / partition DP")
    class SubsequenceDP {

        @Test
        @DisplayName("Longest increasing subsequence (LIS)  →  O(n²) DP, O(n log n) patience sort")
        void longestIncreasingSubsequence() {
            assertEquals(4, lis(new int[]{10, 9, 2, 5, 3, 7, 101, 18}));
            assertEquals(1, lis(new int[]{7, 7, 7, 7}));
        }

        private int lis(int[] nums) {
            int n = nums.length;
            int[] dp = new int[n];
            Arrays.fill(dp, 1);
            int maxLen = 1;
            for (int i = 1; i < n; i++) {
                for (int j = 0; j < i; j++) if (nums[j] < nums[i]) dp[i] = Math.max(dp[i], dp[j] + 1);
                maxLen = Math.max(maxLen, dp[i]);
            }
            return maxLen;
        }

        @Test
        @DisplayName("Coin change — minimum coins  →  O(amount * n) where n=coin types")
        void coinChange() {
            assertEquals(3, coinChange(new int[]{1, 5, 6, 9}, 11));
            assertEquals(-1, coinChange(new int[]{2}, 3));
        }

        private int coinChange(int[] coins, int amount) {
            int[] dp = new int[amount + 1];
            Arrays.fill(dp, amount + 1);
            dp[0] = 0;
            for (int a = 1; a <= amount; a++) {
                for (int c : coins) if (c <= a) dp[a] = Math.min(dp[a], dp[a - c] + 1);
            }
            return dp[amount] > amount ? -1 : dp[amount];
        }

        @Test
        @DisplayName("Word break (dictionary)  →  O(n² * m) where m=avg word length")
        void wordBreak() {
            assertTrue(wordBreak("leetcode", new String[]{"leet", "code"}));
            assertFalse(wordBreak("catsandog", new String[]{"cats", "dog", "sand", "and", "cat"}));
        }

        private boolean wordBreak(String s, String[] dict) {
            java.util.Set<String> wordSet = new java.util.HashSet<>(Arrays.asList(dict));
            boolean[] dp = new boolean[s.length() + 1];
            dp[0] = true;
            for (int i = 1; i <= s.length(); i++)
                for (int j = 0; j < i; j++)
                    if (dp[j] && wordSet.contains(s.substring(j, i))) { dp[i] = true; break; }
            return dp[s.length()];
        }

        @Test
        @DisplayName("0/1 Knapsack  →  O(n * W) time and space")
        void knapsack01() {
            int[] weights = {2, 3, 4, 5}, values = {3, 4, 5, 6};
            int capacity = 8;
            assertEquals(10, knapsack(weights, values, capacity));
        }

        private int knapsack(int[] w, int[] v, int W) {
            int n = w.length;
            int[][] dp = new int[n + 1][W + 1];
            for (int i = 1; i <= n; i++) for (int j = 0; j <= W; j++) {
                dp[i][j] = dp[i-1][j];
                if (w[i-1] <= j) dp[i][j] = Math.max(dp[i][j], dp[i-1][j-w[i-1]] + v[i-1]);
            }
            return dp[n][W];
        }
    }
}
