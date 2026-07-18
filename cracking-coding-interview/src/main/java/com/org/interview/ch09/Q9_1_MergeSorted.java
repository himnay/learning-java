package com.org.interview.ch09;

// Q9.1 You have two sorted arrays, A and B, and A has a large enough buffer at the end to hold B.
// Write a method to merge B into A in sorted order.
public class Q9_1_MergeSorted {

    // Merge from the end to avoid shifting elements - O(n+m)
    /** Merges. */
    public static void merge(int[] a, int[] b, int n, int m) {
        int i = n - 1, j = m - 1, k = n + m - 1;
        while (i >= 0 && j >= 0) {
            if (a[i] > b[j]) a[k--] = a[i--];
            else a[k--] = b[j--];
        }
        while (j >= 0) a[k--] = b[j--];
    }

    // In-place merge of two sorted halves within a single array - O(n^2) no extra space
    /** Merges in place. */
    public static void mergeInPlace(int[] a, int begin, int mid, int end) {
        for (int i = begin; i <= mid; i++) {
            if (a[i] > a[mid + 1]) {
                // swap a[i] with a[mid+1] and restore order in right half
                int tmp = a[i]; a[i] = a[mid + 1]; a[mid + 1] = tmp;
                for (int j = mid + 1; j < end; j++) {
                    if (a[j] <= a[j + 1]) break;
                    tmp = a[j]; a[j] = a[j + 1]; a[j + 1] = tmp;
                }
            }
        }
    }

    /** Application entry point. */
    public static void main(String[] args) {
        int[] a = new int[10];
        int[] aVals = {1, 3, 7, 8, 9};
        int[] b = {2, 4, 5, 6, 10};
        for (int i = 0; i < aVals.length; i++) a[i] = aVals[i];

        merge(a, b, 5, 5);
        System.out.print("Merged: ");
        for (int v : a) System.out.print(v + " ");
        System.out.println();

        int[] c = {8, 9, 11, 15, 17, 1, 3, 5, 12, 18};
        mergeInPlace(c, 0, 4, 9);
        System.out.print("In-place merged: ");
        for (int v : c) System.out.print(v + " ");
        System.out.println();
    }
}
