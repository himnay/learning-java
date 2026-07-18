package com.org.interview.ch01;

// Q1.4 Write a method to decide if two strings are anagrams or not.
public class Q1_4_Anagram {

    private static void insertionSort(char[] a) {
        for (int i = 1; i < a.length; i++) {
            char key = a[i];
            int j = i - 1;
            while (j >= 0 && a[j] > key) { a[j + 1] = a[j]; j--; }
            a[j + 1] = key;
        }
    }

    private static boolean charArrayEquals(char[] a, char[] b) {
        for (int i = 0; i < a.length; i++) if (a[i] != b[i]) return false;
        return true;
    }

    // Sort both strings and compare - O(n log n)
    public static boolean isAnagram1(String s, String t) {
        if (s.isEmpty() || t.isEmpty()) return false;
        if (s.length() != t.length()) return false;
        char[] sc = s.toCharArray();
        char[] tc = t.toCharArray();
        insertionSort(sc);
        insertionSort(tc);
        return charArrayEquals(sc, tc);
    }

    // Character count array - O(n)
    public static boolean isAnagram(String s, String t) {
        if (s.isEmpty() || t.isEmpty()) return false;
        if (s.length() != t.length()) return false;
        int[] c = new int[256];
        for (int i = 0; i < s.length(); i++) {
            c[s.charAt(i)]++;
            c[t.charAt(i)]--;
        }
        for (int count : c) {
            if (count != 0) return false;
        }
        return true;
    }

    /** Application entry point. */
    public static void main(String[] args) {
        System.out.println(isAnagram("aaabbb", "ababab"));  // true
        System.out.println(isAnagram1("aaabbb", "ababab")); // true
        System.out.println(isAnagram("hello", "world"));    // false
    }
}
