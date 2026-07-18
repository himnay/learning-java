package com.org.interview.ch08;

// Q8.4 Write a method to compute all permutations of a string.
public class Q8_4_Permutations {

    private static String[] result;
    private static int resultLen;

    // Insert character at every position in each existing permutation
    /** Returns the permute. */
    public static String[] permute(String s) {
        if (s.isEmpty()) return new String[]{""};
        char first = s.charAt(0);
        String rest = s.substring(1);
        String[] words = permute(rest);

        // Each word gets first inserted at every position -> word.len+1 new strings per word
        int total = 0;
        for (String w : words) total += w.length() + 1;
        String[] perms = new String[total];
        int idx = 0;
        for (String word : words) {
            for (int j = 0; j <= word.length(); j++) {
                perms[idx++] = word.substring(0, j) + first + word.substring(j);
            }
        }
        return perms;
    }

    // Pick each character as prefix and recurse on the remainder
    /** Returns the permute2. */
    public static String[] permute2(String s) {
        if (s.isEmpty()) return new String[]{""};
        int count = factorial(s.length());
        String[] result = new String[count];
        int idx = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            String remaining = s.substring(0, i) + s.substring(i + 1);
            String[] sub = permute2(remaining);
            for (String p : sub) result[idx++] = c + p;
        }
        return result;
    }

    private static int factorial(int n) {
        int f = 1;
        for (int i = 2; i <= n; i++) f *= i;
        return f;
    }

    /** Application entry point. */
    public static void main(String[] args) {
        String s = "abc";
        String[] perms = permute2(s);
        for (String p : perms) System.out.println(p);
    }
}
