package com.org.interview;

// Q9.5 Given a sorted array of strings interspersed with empty strings,
// find the location of a given string.
// Example: find "ball" in ["at","","","","ball","","","car","","","dad","",""] -> 4
public class Q45_SparseSearch {

    /** Searches. */
    public static int search(String[] s, int low, int high, String x) {
        if (x == null || x.isEmpty()) return -1;
        while (low <= high) {
            int mid = (low + high) / 2;
            // scan right to find nearest non-empty string
            int t = mid;
            while (t <= high && s[t].isEmpty()) t++;
            if (t > high) { high = mid - 1; continue; }
            if (s[t].equals(x)) return t;
            else if (s[t].compareTo(x) < 0) low = t + 1;
            else high = mid - 1;
        }
        return -1;
    }

    /** Application entry point. */
    public static void main(String[] args) {
        String[] s = {"at", "", "", "", "ball", "", "", "car", "", "", "dad", "", ""};
        System.out.println("'ball' at index: " + search(s, 0, s.length - 1, "ball")); // 4
        System.out.println("'car' at index:  " + search(s, 0, s.length - 1, "car"));  // 7
        System.out.println("'xyz' at index:  " + search(s, 0, s.length - 1, "xyz"));  // -1
    }
}
