package com.org.java.collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Sliding Window Pattern - O(n) vs O(n²) brute force")
class SlidingWindowTest {

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Fixed-size window  →  O(n)")
    class FixedSizeWindow {

        @Test
        @DisplayName("Maximum sum of K consecutive elements  →  O(n)  (brute force: O(n*k))")
        void maxSumKElements() {
            int[] nums = {2, 1, 5, 1, 3, 2};
            int k = 3;
            // build first window
            int windowSum = 0;
            for (int i = 0; i < k; i++) windowSum += nums[i];
            int maxSum = windowSum;
            // slide
            for (int i = k; i < nums.length; i++) {
                windowSum += nums[i] - nums[i - k]; // add right, remove left — O(1) per slide
                maxSum = Math.max(maxSum, windowSum);
            }
            assertEquals(9, maxSum); // {5,1,3}
        }

        @Test
        @DisplayName("Average of all subarrays of size K  →  O(n)")
        void averageKSubarrays() {
            int[] nums = {1, 3, 2, 6, -1, 4, 1, 8, 2};
            int k = 5;
            double[] result = new double[nums.length - k + 1];
            double windowSum = 0;
            for (int i = 0; i < k; i++) windowSum += nums[i];
            result[0] = windowSum / k;
            for (int i = k; i < nums.length; i++) {
                windowSum += nums[i] - nums[i - k];
                result[i - k + 1] = windowSum / k;
            }
            assertEquals(2.2, result[0], 0.001);
            assertEquals(2.8, result[1], 0.001);
        }

        @Test
        @DisplayName("Count occurrences of an anagram in text (fixed window)  →  O(n)")
        void countAnagrams() {
            String text = "cbaebabacd", pattern = "abc";
            int[] pCount = new int[26], wCount = new int[26];
            int k = pattern.length(), count = 0;
            for (char c : pattern.toCharArray()) pCount[c - 'a']++;
            for (int i = 0; i < k; i++) wCount[text.charAt(i) - 'a']++;
            if (java.util.Arrays.equals(pCount, wCount)) count++;
            for (int i = k; i < text.length(); i++) {
                wCount[text.charAt(i) - 'a']++;
                wCount[text.charAt(i - k) - 'a']--;
                if (java.util.Arrays.equals(pCount, wCount)) count++;
            }
            assertEquals(2, count); // "cba" at 0, "bac" at 6
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Variable-size window  →  O(n)")
    class VariableSizeWindow {

        @Test
        @DisplayName("Longest substring with at most K distinct chars  →  O(n)")
        void longestKDistinct() {
            String s = "araaci";
            int k = 2;
            Map<Character, Integer> charCount = new HashMap<>();
            int lo = 0, maxLen = 0;
            for (int hi = 0; hi < s.length(); hi++) {
                char right = s.charAt(hi);
                charCount.put(right, charCount.getOrDefault(right, 0) + 1);
                while (charCount.size() > k) {
                    char left = s.charAt(lo++);
                    charCount.put(left, charCount.get(left) - 1);
                    if (charCount.get(left) == 0) charCount.remove(left);
                }
                maxLen = Math.max(maxLen, hi - lo + 1);
            }
            assertEquals(4, maxLen); // "araa"
        }

        @Test
        @DisplayName("Smallest subarray with sum >= target  →  O(n)")
        void smallestSubarraySum() {
            int[] nums = {2, 1, 5, 2, 3, 2};
            int target = 7;
            int lo = 0, windowSum = 0, minLen = Integer.MAX_VALUE;
            for (int hi = 0; hi < nums.length; hi++) {
                windowSum += nums[hi];
                while (windowSum >= target) {
                    minLen = Math.min(minLen, hi - lo + 1);
                    windowSum -= nums[lo++];
                }
            }
            assertEquals(2, minLen); // {5,2}
        }

        @Test
        @DisplayName("Longest substring without repeating characters  →  O(n)")
        void longestNonRepeating() {
            String s = "abcabcbb";
            boolean[] inWindow = new boolean[256];
            int lo = 0, maxLen = 0;
            for (int hi = 0; hi < s.length(); hi++) {
                while (inWindow[s.charAt(hi)]) inWindow[s.charAt(lo++)] = false;
                inWindow[s.charAt(hi)] = true;
                maxLen = Math.max(maxLen, hi - lo + 1);
            }
            assertEquals(3, maxLen); // "abc"
        }
    }
}
