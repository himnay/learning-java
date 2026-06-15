package com.org.java.collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Two Pointer Pattern - O(n) vs O(n²) brute force")
class TwoPointerTest {

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Opposite-direction pointers (start and end)  →  O(n)")
    class OppositeDirection {

        @Test
        @DisplayName("Pair sum in sorted array  →  O(n)  (brute force: O(n²))")
        void pairSum() {
            int[] sorted = {1, 2, 3, 4, 6};
            int target = 6;
            int lo = 0, hi = sorted.length - 1;
            int[] pair = {-1, -1};
            while (lo < hi) {
                int sum = sorted[lo] + sorted[hi];
                if (sum == target) { pair[0] = sorted[lo]; pair[1] = sorted[hi]; break; }
                else if (sum < target) lo++;
                else hi--;
            }
            assertArrayEquals(new int[]{2, 4}, pair);
        }

        @Test
        @DisplayName("Palindrome check with two pointers  →  O(n)")
        void palindromeCheck() {
            String s = "racecar";
            int lo = 0, hi = s.length() - 1;
            boolean isPalin = true;
            while (lo < hi) {
                if (s.charAt(lo++) != s.charAt(hi--)) { isPalin = false; break; }
            }
            assertTrue(isPalin);
            assertFalse(isTwoPointerPalindrome("hello"));
        }

        @Test
        @DisplayName("Reverse array in-place with two pointers  →  O(n)")
        void reverseInPlace() {
            int[] a = {1, 2, 3, 4, 5};
            int lo = 0, hi = a.length - 1;
            while (lo < hi) { int t = a[lo]; a[lo++] = a[hi]; a[hi--] = t; }
            assertArrayEquals(new int[]{5, 4, 3, 2, 1}, a);
        }

        @Test
        @DisplayName("Three-sum (sorted) with two-pointer inner loop  →  O(n²)")
        void threeSum() {
            int[] nums = {-3, -2, -1, 0, 1, 2, 3};
            Arrays.sort(nums);
            int count = 0;
            for (int i = 0; i < nums.length - 2; i++) {
                int lo = i + 1, hi = nums.length - 1;
                while (lo < hi) {
                    int sum = nums[i] + nums[lo] + nums[hi];
                    if (sum == 0) { count++; lo++; hi--; }
                    else if (sum < 0) lo++;
                    else hi--;
                }
            }
            assertTrue(count > 0); // e.g., (-3,0,3), (-2,-1,3), (-2,0,2), (-1,0,1), (-1,1,0)
        }

        private boolean isTwoPointerPalindrome(String s) {
            int lo = 0, hi = s.length() - 1;
            while (lo < hi) if (s.charAt(lo++) != s.charAt(hi--)) return false;
            return true;
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Same-direction pointers (fast & slow)  →  O(n)")
    class SameDirection {

        @Test
        @DisplayName("Remove duplicates from sorted array in-place  →  O(n)")
        void removeDuplicatesSorted() {
            int[] a = {1, 1, 2, 2, 3, 4, 4};
            int slow = 1;
            for (int fast = 1; fast < a.length; fast++) {
                if (a[fast] != a[fast - 1]) a[slow++] = a[fast];
            }
            assertArrayEquals(new int[]{1, 2, 3, 4}, Arrays.copyOf(a, slow));
        }

        @Test
        @DisplayName("Move zeros to end in-place  →  O(n)")
        void moveZeroes() {
            int[] a = {0, 1, 0, 3, 12};
            int slow = 0;
            for (int fast = 0; fast < a.length; fast++) {
                if (a[fast] != 0) a[slow++] = a[fast];
            }
            while (slow < a.length) a[slow++] = 0;
            assertArrayEquals(new int[]{1, 3, 12, 0, 0}, a);
        }

        @Test
        @DisplayName("Merge two sorted arrays  →  O(n+m)")
        void mergeSortedArrays() {
            int[] a = {1, 3, 5, 7};
            int[] b = {2, 4, 6, 8};
            int[] merged = new int[a.length + b.length];
            int i = 0, j = 0, k = 0;
            while (i < a.length && j < b.length)
                merged[k++] = a[i] <= b[j] ? a[i++] : b[j++];
            while (i < a.length) merged[k++] = a[i++];
            while (j < b.length) merged[k++] = b[j++];
            assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8}, merged);
        }

        @Test
        @DisplayName("Cycle detection in array (Floyd fast/slow)  →  O(n)")
        void floydCycleDetection() {
            // Linked-list-style: nums[i] points to next index
            int[] nums = {3, 1, 3, 4, 2}; // has cycle: 3->4->2->3->...
            int slow = nums[0], fast = nums[0];
            do {
                slow = nums[slow];
                fast = nums[nums[fast]];
            } while (slow != fast);
            slow = nums[0];
            while (slow != fast) { slow = nums[slow]; fast = nums[fast]; }
            assertEquals(3, slow); // cycle start
        }
    }
}
