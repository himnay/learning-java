package com.org.java.collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Array - Complexity Reference")
class ArrayComplexityTest {

    private final int[] arr = {5, 2, 8, 1, 9, 3, 7, 4, 6, 0};

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(1) — Constant time operations")
    class ConstantTime {

        @Test
        @DisplayName("Access element by index  →  arr[i]  →  O(1)")
        void accessElement() {
            int[] a = {10, 20, 30, 40, 50};
            assertEquals(30, a[2]);
        }

        @Test
        @DisplayName("Update element by index  →  arr[i] = v  →  O(1)")
        void updateElement() {
            int[] a = {10, 20, 30};
            a[1] = 99;
            assertEquals(99, a[1]);
        }

        @Test
        @DisplayName("Find length  →  arr.length  →  O(1)")
        void findLength() {
            int[] a = {1, 2, 3, 4, 5};
            assertEquals(5, a.length);
        }

        @Test
        @DisplayName("Insert at end (fixed pre-allocated buffer)  →  O(1)")
        void insertAtEnd() {
            int[] buf = new int[5];
            int size = 3;
            buf[0] = 1; buf[1] = 2; buf[2] = 3;
            buf[size++] = 4;                // O(1) — just set the slot
            assertEquals(4, size);
            assertEquals(4, buf[3]);
        }

        @Test
        @DisplayName("Delete from end  →  size--  →  O(1)")
        void deleteFromEnd() {
            int size = 4;
            size--;                          // O(1) — just decrement logical size
            assertEquals(3, size);
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(n) — Linear time operations")
    class LinearTime {

        @Test
        @DisplayName("Traverse array  →  for loop  →  O(n)")
        void traverseArray() {
            int[] a = {1, 2, 3, 4, 5};
            int sum = 0;
            for (int v : a) sum += v;         // visits every element once
            assertEquals(15, sum);
        }

        @Test
        @DisplayName("Linear search  →  O(n) worst case")
        void linearSearch() {
            int[] a = {5, 2, 8, 1, 9};
            int target = 9;
            int idx = -1;
            for (int i = 0; i < a.length; i++) {
                if (a[i] == target) { idx = i; break; }
            }
            assertEquals(4, idx);            // found at last position (worst case)
        }

        @Test
        @DisplayName("Reverse array  →  O(n)")
        void reverseArray() {
            int[] a = {1, 2, 3, 4, 5};
            int lo = 0, hi = a.length - 1;
            while (lo < hi) {
                int tmp = a[lo]; a[lo] = a[hi]; a[hi] = tmp;
                lo++; hi--;
            }
            assertArrayEquals(new int[]{5, 4, 3, 2, 1}, a);
        }

        @Test
        @DisplayName("Copy array  →  Arrays.copyOf  →  O(n)")
        void copyArray() {
            int[] a = {1, 2, 3};
            int[] copy = Arrays.copyOf(a, a.length);
            assertArrayEquals(a, copy);
            assertNotSame(a, copy);
        }

        @Test
        @DisplayName("Insert at middle/start (shift elements)  →  O(n)")
        void insertAtMiddle() {
            int[] buf = new int[6];
            buf[0] = 1; buf[1] = 3; buf[2] = 4;
            int size = 3, insertAt = 1, val = 2;
            // shift right
            for (int i = size; i > insertAt; i--) buf[i] = buf[i - 1];
            buf[insertAt] = val;
            size++;
            assertArrayEquals(new int[]{1, 2, 3, 4, 0, 0}, buf);
        }

        @Test
        @DisplayName("Delete from middle/start (shift elements)  →  O(n)")
        void deleteFromMiddle() {
            int[] a = {1, 2, 3, 4, 5};
            int size = 5, deleteAt = 2;
            for (int i = deleteAt; i < size - 1; i++) a[i] = a[i + 1];
            size--;
            assertArrayEquals(new int[]{1, 2, 4, 5}, Arrays.copyOf(a, size));
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(log n) — Logarithmic time operations")
    class LogarithmicTime {

        @Test
        @DisplayName("Binary search (sorted array)  →  Arrays.binarySearch  →  O(log n)")
        void binarySearchBuiltin() {
            int[] sorted = {1, 3, 5, 7, 9, 11, 13};
            int idx = Arrays.binarySearch(sorted, 7);
            assertEquals(3, idx);
        }

        @Test
        @DisplayName("Binary search (manual iterative)  →  O(log n)")
        void binarySearchManual() {
            int[] a = {2, 5, 8, 12, 16, 23, 38, 56, 72, 91};
            int target = 23, lo = 0, hi = a.length - 1, result = -1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                if (a[mid] == target) { result = mid; break; }
                else if (a[mid] < target) lo = mid + 1;
                else hi = mid - 1;
            }
            assertEquals(5, result);
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(n log n) — Sorting")
    class NLogN {

        @Test
        @DisplayName("Sort array  →  Arrays.sort (dual-pivot quicksort)  →  O(n log n)")
        void sortArray() {
            int[] a = {5, 2, 8, 1, 9, 3, 7, 4, 6, 0};
            Arrays.sort(a);
            assertArrayEquals(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, a);
        }

        @Test
        @DisplayName("Merge sort (manual)  →  O(n log n) time, O(n) space")
        void mergeSort() {
            int[] a = {38, 27, 43, 3, 9, 82, 10};
            mergeSort(a, 0, a.length - 1);
            assertArrayEquals(new int[]{3, 9, 10, 27, 38, 43, 82}, a);
        }

        private void mergeSort(int[] a, int lo, int hi) {
            if (lo >= hi) return;
            int mid = (lo + hi) / 2;
            mergeSort(a, lo, mid);
            mergeSort(a, mid + 1, hi);
            merge(a, lo, mid, hi);
        }

        private void merge(int[] a, int lo, int mid, int hi) {
            int[] tmp = Arrays.copyOfRange(a, lo, hi + 1);
            int i = 0, j = mid - lo + 1, k = lo;
            while (i <= mid - lo && j <= hi - lo)
                a[k++] = tmp[i] <= tmp[j] ? tmp[i++] : tmp[j++];
            while (i <= mid - lo) a[k++] = tmp[i++];
            while (j <= hi - lo)  a[k++] = tmp[j++];
        }
    }
}
