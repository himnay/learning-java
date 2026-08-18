package com.org.interview;

// Q1.3 Design an algorithm to remove duplicate characters in a string without using any additional buffer.
// NOTE: One or two additional variables are fine.
public class Q3_RemoveDuplicates {

    // Using bit vector (works for lowercase a-z only) - O(n)
    /** Removes duplicate1. */
    public static String removeDuplicate1(String s) {
        if (s.length() < 2) return s;
        int check = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            int v = s.charAt(i) - 'a';
            if ((check & (1 << v)) == 0) {
                sb.append(s.charAt(i));
                check |= (1 << v);
            }
        }
        return sb.toString();
    }

    // Using boolean array - O(n), works for full ASCII
    /** Removes duplicate2. */
    public static String removeDuplicate2(String s) {
        if (s.length() < 2) return s;
        boolean[] seen = new boolean[256];
        char[] chars = s.toCharArray();
        int p = 0;
        for (int i = 0; i < chars.length; i++) {
            if (!seen[chars[i]]) {
                chars[p++] = chars[i];
                seen[chars[i]] = true;
            }
        }
        return new String(chars, 0, p);
    }

    // In-place O(n^2) - no extra data structure beyond two indices
    /** Removes duplicate3. */
    public static String removeDuplicate3(String s) {
        if (s.length() < 2) return s;
        char[] chars = s.toCharArray();
        int p = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != '\0') {
                chars[p++] = chars[i];
                for (int j = i + 1; j < chars.length; j++) {
                    if (chars[j] == chars[i]) chars[j] = '\0';
                }
            }
        }
        return new String(chars, 0, p);
    }

    /** Application entry point. */
    public static void main(String[] args) {
        String[] tests = {"abcde", "aaabbb", "", "abababc", "ccccc"};
        for (String t : tests) {
            System.out.println(removeDuplicate1(t) + " " + removeDuplicate2(t) + " " + removeDuplicate3(t));
        }
    }
}
