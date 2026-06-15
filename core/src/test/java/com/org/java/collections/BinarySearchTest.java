package com.org.java.collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Binary Search - O(log n) operations")
class BinarySearchTest {

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Classic Binary Search  →  O(log n)")
    class Classic {

        @Test
        @DisplayName("Search target in sorted array  →  O(log n)")
        void searchTarget() {
            int[] a = {1, 3, 5, 7, 9, 11, 13, 15};
            assertEquals(3, binarySearch(a, 7));
            assertEquals(-1, binarySearch(a, 4));
        }

        private int binarySearch(int[] a, int target) {
            int lo = 0, hi = a.length - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                if (a[mid] == target) return mid;
                else if (a[mid] < target) lo = mid + 1;
                else hi = mid - 1;
            }
            return -1;
        }

        @Test
        @DisplayName("Find insertion position (lower bound)  →  O(log n)")
        void lowerBound() {
            int[] a = {1, 3, 5, 7, 9};
            assertEquals(2, lowerBound(a, 5));  // exact
            assertEquals(2, lowerBound(a, 4));  // insert before 5
        }

        private int lowerBound(int[] a, int target) {
            int lo = 0, hi = a.length;
            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;
                if (a[mid] < target) lo = mid + 1;
                else hi = mid;
            }
            return lo;
        }

        @Test
        @DisplayName("Find last position (upper bound)  →  O(log n)")
        void upperBound() {
            int[] a = {1, 3, 5, 5, 5, 7, 9};
            assertEquals(5, upperBound(a, 5)); // index after last '5'
        }

        private int upperBound(int[] a, int target) {
            int lo = 0, hi = a.length;
            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;
                if (a[mid] <= target) lo = mid + 1;
                else hi = mid;
            }
            return lo;
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Binary Search on Answer  →  O(log(range) * n)")
    class SearchOnAnswer {

        @Test
        @DisplayName("Minimum in rotated sorted array  →  O(log n)")
        void minRotated() {
            int[] a = {4, 5, 6, 7, 0, 1, 2};
            int lo = 0, hi = a.length - 1;
            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;
                if (a[mid] > a[hi]) lo = mid + 1;
                else hi = mid;
            }
            assertEquals(0, a[lo]);
        }

        @Test
        @DisplayName("Search in rotated sorted array  →  O(log n)")
        void searchRotated() {
            int[] a = {4, 5, 6, 7, 0, 1, 2};
            assertEquals(4, searchRotated(a, 0));
            assertEquals(0, searchRotated(a, 4));
            assertEquals(-1, searchRotated(a, 3));
        }

        private int searchRotated(int[] a, int target) {
            int lo = 0, hi = a.length - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                if (a[mid] == target) return mid;
                if (a[lo] <= a[mid]) { // left half sorted
                    if (target >= a[lo] && target < a[mid]) hi = mid - 1;
                    else lo = mid + 1;
                } else { // right half sorted
                    if (target > a[mid] && target <= a[hi]) lo = mid + 1;
                    else hi = mid - 1;
                }
            }
            return -1;
        }

        @Test
        @DisplayName("Sqrt via binary search  →  O(log n)")
        void sqrtBinarySearch() {
            assertEquals(4, sqrtFloor(16));
            assertEquals(5, sqrtFloor(25));
            assertEquals(4, sqrtFloor(20)); // floor(sqrt(20)) = 4
        }

        private int sqrtFloor(int n) {
            if (n < 2) return n;
            int lo = 1, hi = n / 2, result = 1;
            while (lo <= hi) {
                long mid = lo + (hi - lo) / 2;
                if (mid * mid == n) return (int) mid;
                else if (mid * mid < n) { result = (int) mid; lo = (int) mid + 1; }
                else hi = (int) mid - 1;
            }
            return result;
        }

        @Test
        @DisplayName("First bad version (binary search on condition)  →  O(log n)")
        void firstBadVersion() {
            int bad = 4, n = 10;
            int lo = 1, hi = n;
            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;
                if (isBad(mid, bad)) hi = mid;
                else lo = mid + 1;
            }
            assertEquals(4, lo);
        }

        private boolean isBad(int version, int bad) { return version >= bad; }

        @Test
        @DisplayName("Find peak element (peak = greater than neighbors)  →  O(log n)")
        void findPeakElement() {
            int[] nums = {1, 2, 3, 1};
            int lo = 0, hi = nums.length - 1;
            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;
                if (nums[mid] < nums[mid + 1]) lo = mid + 1;
                else hi = mid;
            }
            assertEquals(2, lo); // index 2, value 3
        }
    }
}
