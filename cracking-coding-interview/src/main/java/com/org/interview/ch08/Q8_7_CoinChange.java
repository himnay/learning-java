package com.org.interview.ch08;

// Q8.7 Given an infinite number of quarters (25c), dimes (10c), nickels (5c) and pennies (1c),
// write code to calculate the number of ways of representing n cents.
public class Q8_7_CoinChange {

    // Recursive with denominations array - avoids redundant counting via ordering
    public static int makeChange(int n, int denomIdx) {
        int[] denoms = {25, 10, 5, 1};
        if (denomIdx == denoms.length - 1) return 1; // only pennies left
        int ways = 0;
        int denom = denoms[denomIdx];
        for (int i = 0; i * denom <= n; i++) {
            ways += makeChange(n - i * denom, denomIdx + 1);
        }
        return ways;
    }

    // Dynamic programming - O(n * coins) time, O(n) space
    public static int makeChangeDP(int n) {
        int[] denoms = {1, 5, 10, 25};
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for (int denom : denoms) {
            for (int i = denom; i <= n; i++) {
                dp[i] += dp[i - denom];
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        int n = 10;
        System.out.println("Ways to make " + n + "c (recursive): " + makeChange(n, 0));
        System.out.println("Ways to make " + n + "c (DP):        " + makeChangeDP(n));
        System.out.println("Ways to make 100c (DP): " + makeChangeDP(100));
    }
}
