package com.org.interview.ch09;

// Q9.3 Given a sorted array rotated an unknown number of times, find an element in O(log n).
// Example: find 5 in [15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14] -> index 8
public class Q9_3_RotatedArraySearch {

    /** Searches. */
    public static int search(int[] a, int low, int high, int k) {
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (a[mid] == k) return mid;
            if (a[mid] >= a[low]) {
                // left half is sorted
                if (k < a[mid] && k >= a[low]) high = mid - 1;
                else low = mid + 1;
            } else {
                // right half is sorted
                if (k > a[mid] && k < a[low]) low = mid + 1;
                else high = mid - 1;
            }
        }
        return -1;
    }

    /** Application entry point. */
    public static void main(String[] args) {
        int[] a = {15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14};
        System.out.println("Index of 5:  " + search(a, 0, a.length - 1, 5));  // 8
        System.out.println("Index of 3:  " + search(a, 0, a.length - 1, 3));  // 6
        System.out.println("Index of 16: " + search(a, 0, a.length - 1, 16)); // 1
        System.out.println("Index of 99: " + search(a, 0, a.length - 1, 99)); // -1
    }
}
