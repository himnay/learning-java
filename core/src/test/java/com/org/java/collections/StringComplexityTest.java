package com.org.java.collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("String - Complexity Reference")
class StringComplexityTest {

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(1) — Constant time operations")
    class ConstantTime {

        @Test
        @DisplayName("Get length  →  s.length()  →  O(1)")
        void getLength() {
            String s = "hello";
            assertEquals(5, s.length());
        }

        @Test
        @DisplayName("Access character  →  s.charAt(i)  →  O(1)")
        void accessChar() {
            String s = "hello";
            assertEquals('e', s.charAt(1));
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(n) — Linear time operations")
    class LinearTime {

        @Test
        @DisplayName("Concatenation  →  s1 + s2  →  O(n) (creates new string)")
        void concatenation() {
            String s1 = "hello";
            String s2 = " world";
            String result = s1 + s2;
            assertEquals("hello world", result);
        }

        @Test
        @DisplayName("StringBuilder append (amortized O(1) per char, O(n) total)  →  use over + in loop")
        void stringBuilderConcat() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 5; i++) sb.append(i);  // O(n) total — no extra copies each iteration
            assertEquals("01234", sb.toString());
        }

        @Test
        @DisplayName("Substring extraction  →  s.substring(i,j)  →  O(j-i)")
        void substring() {
            String s = "HelloWorld";
            assertEquals("World", s.substring(5, 10));
        }

        @Test
        @DisplayName("Convert to char array  →  s.toCharArray()  →  O(n)")
        void toCharArray() {
            String s = "abc";
            char[] chars = s.toCharArray();
            assertArrayEquals(new char[]{'a', 'b', 'c'}, chars);
        }

        @Test
        @DisplayName("Check equality  →  s1.equals(s2)  →  O(n) worst case")
        void checkEquality() {
            String s1 = "hello";
            String s2 = "hello";
            assertTrue(s1.equals(s2));
        }

        @Test
        @DisplayName("Compare strings  →  s1.compareTo(s2)  →  O(n)")
        void compareStrings() {
            assertTrue("apple".compareTo("banana") < 0);
            assertTrue("banana".compareTo("apple") > 0);
            assertEquals(0, "same".compareTo("same"));
        }

        @Test
        @DisplayName("Reverse string  →  StringBuilder.reverse()  →  O(n)")
        void reverseString() {
            String s = "hello";
            String rev = new StringBuilder(s).reverse().toString();
            assertEquals("olleh", rev);
        }

        @Test
        @DisplayName("Change case  →  toUpperCase() / toLowerCase()  →  O(n)")
        void changeCase() {
            assertEquals("HELLO", "hello".toUpperCase());
            assertEquals("hello", "HELLO".toLowerCase());
        }

        @Test
        @DisplayName("Find index of char  →  s.indexOf('a')  →  O(n)")
        void indexOf() {
            String s = "banana";
            assertEquals(1, s.indexOf('a'));
            assertEquals(5, s.lastIndexOf('a'));
        }

        @Test
        @DisplayName("Trim whitespace  →  s.trim()  →  O(n)")
        void trim() {
            assertEquals("hello", "  hello  ".trim());
        }

        @Test
        @DisplayName("Replace characters  →  s.replace('a','b')  →  O(n)")
        void replace() {
            assertEquals("hxllo", "hello".replace('e', 'x'));
        }

        @Test
        @DisplayName("Split string  →  s.split(' ')  →  O(n)")
        void split() {
            String[] parts = "one two three".split(" ");
            assertArrayEquals(new String[]{"one", "two", "three"}, parts);
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(n log n) — Sorting characters")
    class NLogN {

        @Test
        @DisplayName("Sort string chars  →  Arrays.sort(chars)  →  O(n log n)")
        void sortChars() {
            char[] chars = "banana".toCharArray();
            Arrays.sort(chars);
            assertEquals("aaabnn", new String(chars));
        }

        @Test
        @DisplayName("Anagram detection via sorted chars comparison  →  O(n log n)")
        void anagramDetection() {
            String a = "listen", b = "silent";
            char[] ca = a.toCharArray(), cb = b.toCharArray();
            Arrays.sort(ca); Arrays.sort(cb);
            assertTrue(Arrays.equals(ca, cb));
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("String Patterns — demonstrated with complexity")
    class StringPatterns {

        @Test
        @DisplayName("Two-pointer palindrome check  →  O(n)")
        void palindromeCheck() {
            String s = "racecar";
            int lo = 0, hi = s.length() - 1;
            boolean isPalin = true;
            while (lo < hi) {
                if (s.charAt(lo) != s.charAt(hi)) { isPalin = false; break; }
                lo++; hi--;
            }
            assertTrue(isPalin);
        }

        @Test
        @DisplayName("Sliding window — longest substring without repeating chars  →  O(n)")
        void longestUniqueSubstring() {
            String s = "abcabcbb";
            boolean[] seen = new boolean[256];
            int lo = 0, maxLen = 0;
            for (int hi = 0; hi < s.length(); hi++) {
                while (seen[s.charAt(hi)]) { seen[s.charAt(lo++)] = false; }
                seen[s.charAt(hi)] = true;
                maxLen = Math.max(maxLen, hi - lo + 1);
            }
            assertEquals(3, maxLen);  // "abc"
        }

        @Test
        @DisplayName("Character frequency with array (hashing)  →  O(n)")
        void charFrequency() {
            String s = "aabbcc";
            int[] freq = new int[256];
            for (char c : s.toCharArray()) freq[c]++;
            assertEquals(2, freq['a']);
            assertEquals(2, freq['b']);
            assertEquals(2, freq['c']);
        }
    }
}
