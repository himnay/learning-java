package com.org.interview;

// Q1.5 Write a method to replace all spaces in a string with '%20'.
public class Q5_ReplaceSpaces {

    // Using StringBuilder - O(n)
    /** Returns the replace1. */
    public static String replace1(String s) {
        if (s == null || s.isEmpty()) return s;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') sb.append("%20");
            else sb.append(s.charAt(i));
        }
        return sb.toString();
    }

    // In-place with pre-allocated char array (C-style approach) - O(n)
    /** Returns the replace2. */
    public static String replace2(String s) {
        if (s == null || s.isEmpty()) return s;
        int spaces = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') spaces++;
        }
        char[] chars = new char[s.length() + 2 * spaces];
        int p = chars.length - 1;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == ' ') {
                chars[p--] = '0';
                chars[p--] = '2';
                chars[p--] = '%';
            } else {
                chars[p--] = s.charAt(i);
            }
        }
        return new String(chars);
    }

    /** Application entry point. */
    public static void main(String[] args) {
        String s = "Mr John Smith";
        System.out.println(replace1(s));
        System.out.println(replace2(s));
    }
}
