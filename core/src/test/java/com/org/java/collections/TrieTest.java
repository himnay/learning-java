package com.org.java.collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Trie - Complexity Reference")
class TrieTest {

    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isEnd;
    }

    static class Trie {
        final TrieNode root = new TrieNode();

        void insert(String word) {          // O(m) where m = word length
            TrieNode cur = root;
            for (char c : word.toCharArray()) {
                int idx = c - 'a';
                if (cur.children[idx] == null) cur.children[idx] = new TrieNode();
                cur = cur.children[idx];
            }
            cur.isEnd = true;
        }

        boolean search(String word) {       // O(m)
            TrieNode cur = root;
            for (char c : word.toCharArray()) {
                int idx = c - 'a';
                if (cur.children[idx] == null) return false;
                cur = cur.children[idx];
            }
            return cur.isEnd;
        }

        boolean startsWith(String prefix) { // O(m)
            TrieNode cur = root;
            for (char c : prefix.toCharArray()) {
                int idx = c - 'a';
                if (cur.children[idx] == null) return false;
                cur = cur.children[idx];
            }
            return true;
        }

        void delete(String word) {          // O(m)
            delete(root, word, 0);
        }

        private boolean delete(TrieNode cur, String word, int depth) {
            if (depth == word.length()) {
                if (!cur.isEnd) return false;
                cur.isEnd = false;
                return isEmpty(cur);
            }
            int idx = word.charAt(depth) - 'a';
            if (cur.children[idx] == null) return false;
            if (delete(cur.children[idx], word, depth + 1))
                cur.children[idx] = null;
            return !cur.isEnd && isEmpty(cur);
        }

        private boolean isEmpty(TrieNode node) {
            for (TrieNode c : node.children) if (c != null) return false;
            return true;
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(m) — Core Trie operations  (m = length of word/prefix)")
    class CoreOps {

        @Test
        @DisplayName("Insert word  →  O(m)  where m = word length")
        void insert() {
            Trie trie = new Trie();
            trie.insert("apple");
            assertTrue(trie.search("apple"));
            assertFalse(trie.search("app"));
        }

        @Test
        @DisplayName("Search exact word  →  O(m)")
        void search() {
            Trie trie = new Trie();
            trie.insert("hello");
            trie.insert("world");
            assertTrue(trie.search("hello"));
            assertTrue(trie.search("world"));
            assertFalse(trie.search("hell"));
            assertFalse(trie.search("word"));
        }

        @Test
        @DisplayName("Check prefix (startsWith)  →  O(m)")
        void startsWith() {
            Trie trie = new Trie();
            trie.insert("apple");
            assertTrue(trie.startsWith("app"));
            assertTrue(trie.startsWith("apple"));
            assertFalse(trie.startsWith("apl"));
        }

        @Test
        @DisplayName("Delete word  →  O(m)")
        void delete() {
            Trie trie = new Trie();
            trie.insert("apple");
            trie.insert("app");
            trie.delete("apple");
            assertFalse(trie.search("apple")); // deleted
            assertTrue(trie.search("app"));    // still present
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(n*m) — Trie build and batch operations")
    class BatchOps {

        @Test
        @DisplayName("Build trie from list of words  →  O(n*m)")
        void buildTrie() {
            String[] words = {"apple", "app", "apply", "apt", "bat", "ball"};
            Trie trie = new Trie();
            for (String w : words) trie.insert(w); // O(n*m) total
            assertTrue(trie.search("apple"));
            assertTrue(trie.search("ball"));
            assertFalse(trie.search("ba"));
        }

        @Test
        @DisplayName("Autocomplete — find all words with prefix  →  O(n*m)")
        void autocomplete() {
            Trie trie = new Trie();
            String[] words = {"apple", "app", "apply", "bat", "ball"};
            for (String w : words) trie.insert(w);
            // collect words with prefix "app"
            int count = countWithPrefix(trie.root, "app", 0);
            assertEquals(3, count); // app, apple, apply
        }

        private int countWithPrefix(TrieNode root, String prefix, int depth) {
            TrieNode cur = root;
            for (char c : prefix.toCharArray()) {
                int idx = c - 'a';
                if (cur.children[idx] == null) return 0;
                cur = cur.children[idx];
            }
            return dfsCount(cur);
        }

        private int dfsCount(TrieNode node) {
            if (node == null) return 0;
            int count = node.isEnd ? 1 : 0;
            for (TrieNode child : node.children) count += dfsCount(child);
            return count;
        }
    }
}
