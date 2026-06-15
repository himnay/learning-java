package com.org.interview.ch01;

// Q1.2 Write code to reverse a C-Style String.
public class Q1_2_ReverseString {

    public static String reverse(String s) {
        if (s == null || s.isEmpty()) return s;
        char[] chars = s.toCharArray();
        int left = 0, right = chars.length - 1;
        while (left < right) {
            char tmp = chars[left];
            chars[left] = chars[right];
            chars[right] = tmp;
            left++;
            right--;
        }
        return new String(chars);
    }

    public static void main(String[] args) {
        String s = "1234567890asdfghjkl";
        System.out.println(reverse(s));
    }
}
