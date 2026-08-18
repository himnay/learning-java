package com.org.interview;

// Q9.2 Write a method to sort an array of strings so that all anagrams are next to each other.
public class Q43_AnagramSort {

    // Sort characters of a string
    private static String sortChars(String s) {
        char[] chars = s.toCharArray();
        // insertion sort on the chars array
        for (int i = 1; i < chars.length; i++) {
            char key = chars[i];
            int j = i - 1;
            while (j >= 0 && chars[j] > key) {
                chars[j + 1] = chars[j];
                j--;
            }
            chars[j + 1] = key;
        }
        return new String(chars);
    }

    // Compare two strings by their sorted form
    private static int compareAnagram(String s1, String s2) {
        return sortChars(s1).compareTo(sortChars(s2));
    }

    // Sort using insertion sort with anagram comparator
    /** Sorts by anagram. */
    public static void sortByAnagram(String[] arr) {
        for (int i = 1; i < arr.length; i++) {
            String key = arr[i];
            int j = i - 1;
            while (j >= 0 && compareAnagram(arr[j], key) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    /** Application entry point. */
    public static void main(String[] args) {
        String[] s = {"axyz", "zyxa", "evil", "live", "god", "dog"};
        sortByAnagram(s);
        for (String str : s) System.out.println(str);
    }
}
