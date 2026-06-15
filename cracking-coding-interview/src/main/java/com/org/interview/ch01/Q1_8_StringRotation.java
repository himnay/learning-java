package com.org.interview.ch01;

// Q1.8 Given two strings s1 and s2, write code to check if s2 is a rotation of s1
// using only one call to isSubstring (e.g., "waterbottle" is a rotation of "erbottlewat").
public class Q1_8_StringRotation {

    public static boolean isSubstring(String s1, String s2) {
        return s1.contains(s2);
    }

    public static boolean isRotation(String s1, String s2) {
        if (s1.length() != s2.length() || s1.isEmpty()) return false;
        return isSubstring(s1 + s1, s2);
    }

    public static void main(String[] args) {
        System.out.println(isRotation("apple", "pleap"));       // true
        System.out.println(isRotation("waterbottle", "erbottlewat")); // true
        System.out.println(isRotation("hello", "world"));       // false
    }
}
