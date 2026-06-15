package com.org.java.collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Hashing (HashMap / HashSet) - Complexity Reference")
class HashingComplexityTest {

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(1) average — HashMap operations")
    class HashMapOps {

        @Test
        @DisplayName("Insert key-value pair  →  map.put(k,v)  →  O(1) avg")
        void put() {
            Map<String, Integer> map = new HashMap<>();
            map.put("apple", 3);
            assertEquals(3, map.get("apple"));
        }

        @Test
        @DisplayName("Access value by key  →  map.get(key)  →  O(1) avg")
        void get() {
            Map<String, Integer> map = new HashMap<>(Map.of("a", 1, "b", 2, "c", 3));
            assertEquals(2, map.get("b"));
            assertNull(map.get("z"));
        }

        @Test
        @DisplayName("Check if key exists  →  map.containsKey(key)  →  O(1)")
        void containsKey() {
            Map<Integer, String> map = new HashMap<>(Map.of(1, "one", 2, "two"));
            assertTrue(map.containsKey(1));
            assertFalse(map.containsKey(99));
        }

        @Test
        @DisplayName("Remove key  →  map.remove(key)  →  O(1) avg")
        void remove() {
            Map<String, Integer> map = new HashMap<>();
            map.put("x", 10);
            map.remove("x");
            assertFalse(map.containsKey("x"));
        }

        @Test
        @DisplayName("getOrDefault — avoids null check  →  O(1)")
        void getOrDefault() {
            Map<String, Integer> freq = new HashMap<>();
            String[] words = {"a", "b", "a", "c", "b", "a"};
            for (String w : words) freq.put(w, freq.getOrDefault(w, 0) + 1);
            assertEquals(3, freq.get("a"));
            assertEquals(2, freq.get("b"));
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(n) — HashMap iteration operations")
    class HashMapIterationOps {

        @Test
        @DisplayName("Get all keys  →  map.keySet()  →  O(n)")
        void keySet() {
            Map<String, Integer> map = new HashMap<>(Map.of("a", 1, "b", 2, "c", 3));
            Set<String> keys = map.keySet();
            assertEquals(3, keys.size());
        }

        @Test
        @DisplayName("Get all values  →  map.values()  →  O(n)")
        void values() {
            Map<String, Integer> map = new HashMap<>(Map.of("x", 10, "y", 20));
            long sum = map.values().stream().mapToLong(Integer::longValue).sum();
            assertEquals(30, sum);
        }

        @Test
        @DisplayName("Iterate through HashMap  →  map.entrySet()  →  O(n)")
        void entrySet() {
            Map<String, Integer> map = new HashMap<>(Map.of("a", 1, "b", 2));
            int total = 0;
            for (Map.Entry<String, Integer> e : map.entrySet()) total += e.getValue();
            assertEquals(3, total);
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(1) average — HashSet operations")
    class HashSetOps {

        @Test
        @DisplayName("Insert into HashSet  →  set.add(v)  →  O(1) avg")
        void add() {
            Set<Integer> set = new HashSet<>();
            set.add(1); set.add(2); set.add(1); // duplicate ignored
            assertEquals(2, set.size());
        }

        @Test
        @DisplayName("Check presence  →  set.contains(v)  →  O(1) avg")
        void contains() {
            Set<String> set = new HashSet<>(Arrays.asList("a", "b", "c"));
            assertTrue(set.contains("b"));
            assertFalse(set.contains("z"));
        }

        @Test
        @DisplayName("Delete from HashSet  →  set.remove(v)  →  O(1) avg")
        void removeFromSet() {
            Set<Integer> set = new HashSet<>(Arrays.asList(1, 2, 3));
            set.remove(2);
            assertFalse(set.contains(2));
            assertEquals(2, set.size());
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Hashing Patterns")
    class HashingPatterns {

        @Test
        @DisplayName("Frequency count  →  O(n) time, O(n) space")
        void frequencyCount() {
            int[] nums = {1, 2, 3, 2, 1, 1};
            Map<Integer, Integer> freq = new HashMap<>();
            for (int n : nums) freq.put(n, freq.getOrDefault(n, 0) + 1);
            assertEquals(3, freq.get(1));
            assertEquals(2, freq.get(2));
        }

        @Test
        @DisplayName("Two-sum (pair finding)  →  O(n) using HashMap")
        void twoSum() {
            int[] nums = {2, 7, 11, 15};
            int target = 9;
            Map<Integer, Integer> seen = new HashMap<>();
            int[] result = {};
            for (int i = 0; i < nums.length; i++) {
                int complement = target - nums[i];
                if (seen.containsKey(complement)) {
                    result = new int[]{seen.get(complement), i};
                    break;
                }
                seen.put(nums[i], i);
            }
            assertArrayEquals(new int[]{0, 1}, result);
        }

        @Test
        @DisplayName("Anagram detection  →  O(n) using frequency array")
        void anagramDetection() {
            String s = "anagram", t = "nagaram";
            int[] count = new int[26];
            for (char c : s.toCharArray()) count[c - 'a']++;
            for (char c : t.toCharArray()) count[c - 'a']--;
            boolean isAnagram = true;
            for (int c : count) if (c != 0) { isAnagram = false; break; }
            assertTrue(isAnagram);
        }

        @Test
        @DisplayName("Memoization (caching)  →  Fibonacci O(2^n) → O(n) with map")
        void memoization() {
            Map<Integer, Long> memo = new HashMap<>();
            assertEquals(55L, fib(10, memo));
        }

        private long fib(int n, Map<Integer, Long> memo) {
            if (n <= 1) return n;
            if (memo.containsKey(n)) return memo.get(n);
            long result = fib(n - 1, memo) + fib(n - 2, memo);
            memo.put(n, result);
            return result;
        }

        @Test
        @DisplayName("Sort keys of HashMap  →  TreeMap / sort keySet  →  O(n log n)")
        void sortedKeys() {
            Map<String, Integer> map = new HashMap<>(Map.of("banana", 2, "apple", 1, "cherry", 3));
            List<String> sortedKeys = new ArrayList<>(map.keySet());
            Collections.sort(sortedKeys);
            assertEquals("apple", sortedKeys.get(0));
            assertEquals("banana", sortedKeys.get(1));
        }
    }
}
