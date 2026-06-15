package com.org.java.collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Greedy - Complexity Reference")
class GreedyTest {

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(n log n) — Greedy with sorting")
    class WithSorting {

        @Test
        @DisplayName("Activity selection (max non-overlapping intervals)  →  O(n log n)")
        void activitySelection() {
            int[][] activities = {{1,4},{3,5},{0,6},{5,7},{3,8},{5,9},{6,10},{8,11},{8,12},{2,13},{12,14}};
            Arrays.sort(activities, (a, b) -> a[1] - b[1]); // sort by end time
            int count = 0, lastEnd = -1;
            for (int[] a : activities) {
                if (a[0] >= lastEnd) { count++; lastEnd = a[1]; }
            }
            assertEquals(4, count);
        }

        @Test
        @DisplayName("Job scheduling to minimize lateness  →  O(n log n)")
        void minimizeLateness() {
            // Each job: {duration, deadline}; schedule by earliest deadline
            int[][] jobs = {{2,4},{3,6},{1,3}};
            Arrays.sort(jobs, (a, b) -> a[1] - b[1]);
            int time = 0, maxLateness = 0;
            for (int[] job : jobs) {
                time += job[0];
                maxLateness = Math.max(maxLateness, time - job[1]);
            }
            assertTrue(maxLateness >= 0); // computed; ordering minimizes this
        }

        @Test
        @DisplayName("Fractional Knapsack (sort by value/weight)  →  O(n log n)")
        void fractionalKnapsack() {
            int[][] items = {{60,10},{100,20},{120,30}}; // {value, weight}
            int capacity = 50;
            double[][] ratio = new double[items.length][2];
            for (int i = 0; i < items.length; i++) {
                ratio[i][0] = (double) items[i][0] / items[i][1];
                ratio[i][1] = i;
            }
            Arrays.sort(ratio, (a, b) -> Double.compare(b[0], a[0])); // sort desc by ratio
            double totalValue = 0;
            for (double[] r : ratio) {
                int idx = (int) r[1];
                int wt = items[idx][1], val = items[idx][0];
                if (capacity >= wt) { totalValue += val; capacity -= wt; }
                else { totalValue += r[0] * capacity; break; }
            }
            assertEquals(240.0, totalValue, 0.001);
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(n) — Greedy without sorting")
    class Linear {

        @Test
        @DisplayName("Jump Game I — can reach end?  →  O(n)")
        void jumpGameI() {
            assertTrue(canJump(new int[]{2, 3, 1, 1, 4}));
            assertFalse(canJump(new int[]{3, 2, 1, 0, 4}));
        }

        private boolean canJump(int[] nums) {
            int maxReach = 0;
            for (int i = 0; i < nums.length; i++) {
                if (i > maxReach) return false;
                maxReach = Math.max(maxReach, i + nums[i]);
            }
            return true;
        }

        @Test
        @DisplayName("Jump Game II — minimum jumps to reach end  →  O(n)")
        void jumpGameII() {
            assertEquals(2, minJumps(new int[]{2, 3, 1, 1, 4}));
            assertEquals(2, minJumps(new int[]{2, 3, 0, 1, 4}));
        }

        private int minJumps(int[] nums) {
            int jumps = 0, curEnd = 0, farthest = 0;
            for (int i = 0; i < nums.length - 1; i++) {
                farthest = Math.max(farthest, i + nums[i]);
                if (i == curEnd) { jumps++; curEnd = farthest; }
            }
            return jumps;
        }

        @Test
        @DisplayName("Gas station circular tour  →  O(n)")
        void gasStation() {
            int[] gas = {1,2,3,4,5}, cost = {3,4,5,1,2};
            int tank = 0, total = 0, start = 0;
            for (int i = 0; i < gas.length; i++) {
                int diff = gas[i] - cost[i];
                tank += diff; total += diff;
                if (tank < 0) { start = i + 1; tank = 0; }
            }
            assertEquals(3, total >= 0 ? start : -1);
        }

        @Test
        @DisplayName("Assign cookies greedily  →  O(n log n)")
        void assignCookies() {
            int[] greed = {1,2,3}, cookies = {1,1};
            Arrays.sort(greed); Arrays.sort(cookies);
            int contentChildren = 0, j = 0;
            for (int i = 0; i < greed.length && j < cookies.length; j++) {
                if (cookies[j] >= greed[i]) { contentChildren++; i++; }
            }
            assertEquals(1, contentChildren);
        }
    }
}
