package com.org.interview;

// Q9.7 A circus tower: each person must be strictly shorter AND lighter than the person below.
// Given heights and weights, compute the largest possible number of people in such a tower.
// Example: (65,100)(70,150)(56,90)(75,190)(60,95)(68,110) -> 6 people
public class Q47_CircusTower {

    static class Person {
        int h, w;
        Person(int h, int w) { this.h = h; this.w = w; }
    }

    // Sort by height; for equal height sort by weight descending to avoid
    // incorrectly chaining people of the same height
    private static void sortPeople(Person[] p) {
        // insertion sort
        for (int i = 1; i < p.length; i++) {
            Person key = p[i];
            int j = i - 1;
            while (j >= 0 && (p[j].h > key.h || (p[j].h == key.h && p[j].w < key.w))) {
                p[j + 1] = p[j];
                j--;
            }
            p[j + 1] = key;
        }
    }

    // Longest Increasing Subsequence on weights (patience sort / binary search) - O(n log n)
    /** Returns the longest increasing tower. */
    public static int longestIncreasingTower(Person[] p) {
        sortPeople(p);
        int n = p.length;
        int[] tails = new int[n]; // tails[i] = smallest tail of LIS of length i+1
        int len = 0;
        for (Person person : p) {
            int w = person.w;
            // binary search for first tail >= w
            int lo = 0, hi = len;
            while (lo < hi) {
                int mid = (lo + hi) / 2;
                if (tails[mid] < w) lo = mid + 1;
                else hi = mid;
            }
            tails[lo] = w;
            if (lo == len) len++;
        }
        return len;
    }

    /** Application entry point. */
    public static void main(String[] args) {
        Person[] people = {
            new Person(65, 100), new Person(70, 150), new Person(56, 90),
            new Person(75, 190), new Person(60, 95),  new Person(68, 110)
        };
        System.out.println("Longest tower: " + longestIncreasingTower(people)); // 6
    }
}
