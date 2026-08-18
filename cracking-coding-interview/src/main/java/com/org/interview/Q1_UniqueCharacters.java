package com.org.interview;

// Q1.1 Implement an algorithm to determine if a string has all unique characters.
// What if you cannot use additional data structures?
public class Q1_UniqueCharacters {

    // Using boolean array - O(n) time, O(1) space
    public static boolean isUnique1(String s) {
        boolean[] a = new boolean[256];
        for (int i = 0; i < s.length(); i++) {
            int v = s.charAt(i);
            if (a[v]) return false;
            a[v] = true;
        }
        return true;
    }

    // Using bit vector - O(n) time, O(1) space, no extra array
    public static boolean isUnique2(String s) {
        int[] a = new int[8];
        for (int i = 0; i < s.length(); i++) {
            int v = s.charAt(i);
            int idx = v / 32, shift = v % 32;
            if ((a[idx] & (1 << shift)) != 0) return false;
            a[idx] |= (1 << shift);
        }
        return true;
    }

    /** Application entry point. */
    public static void main(String[] args) {
        String s1 = "Hello World!";
        String s2 = "asdfghjkl";
        System.out.println(isUnique1(s1) + " " + isUnique1(s2));
        System.out.println(isUnique2(s1) + " " + isUnique2(s2));
    }
}
