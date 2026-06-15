package com.org.java.collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Binary Tree / BST - Complexity Reference")
class TreeComplexityTest {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int v) { val = v; }
    }

    private TreeNode insert(TreeNode root, int v) {
        if (root == null) return new TreeNode(v);
        if (v < root.val) root.left = insert(root.left, v);
        else if (v > root.val) root.right = insert(root.right, v);
        return root;
    }

    private TreeNode buildBST(int... vals) {
        TreeNode root = null;
        for (int v : vals) root = insert(root, v);
        return root;
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(log n) average, O(n) worst — BST operations")
    class BSTOps {

        @Test
        @DisplayName("Search in BST  →  O(log n) average, O(n) worst (unbalanced)")
        void searchBST() {
            TreeNode root = buildBST(5, 3, 7, 1, 4, 6, 8);
            assertTrue(searchBST(root, 4));
            assertFalse(searchBST(root, 9));
        }

        private boolean searchBST(TreeNode root, int target) {
            if (root == null) return false;
            if (root.val == target) return true;
            return target < root.val ? searchBST(root.left, target) : searchBST(root.right, target);
        }

        @Test
        @DisplayName("Insert in BST  →  O(log n) average")
        void insertBST() {
            TreeNode root = buildBST(5, 3, 7);
            root = insert(root, 4);
            assertTrue(searchBST(root, 4));
        }

        @Test
        @DisplayName("In-order traversal gives sorted sequence  →  O(n)")
        void inOrderSorted() {
            TreeNode root = buildBST(5, 3, 7, 1, 4, 6, 8);
            List<Integer> result = new ArrayList<>();
            inOrder(root, result);
            assertEquals(Arrays.asList(1, 3, 4, 5, 6, 7, 8), result);
        }

        private void inOrder(TreeNode node, List<Integer> result) {
            if (node == null) return;
            inOrder(node.left, result);
            result.add(node.val);
            inOrder(node.right, result);
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(n) — Binary tree traversals and operations")
    class BinaryTreeOps {

        @Test
        @DisplayName("Pre-order traversal  →  root, left, right  →  O(n)")
        void preOrder() {
            TreeNode root = buildBST(4, 2, 6, 1, 3, 5, 7);
            List<Integer> result = new ArrayList<>();
            preOrder(root, result);
            assertEquals(4, result.get(0)); // root first
        }

        private void preOrder(TreeNode node, List<Integer> result) {
            if (node == null) return;
            result.add(node.val);
            preOrder(node.left, result);
            preOrder(node.right, result);
        }

        @Test
        @DisplayName("Post-order traversal  →  left, right, root  →  O(n)")
        void postOrder() {
            TreeNode root = buildBST(4, 2, 6, 1, 3, 5, 7);
            List<Integer> result = new ArrayList<>();
            postOrder(root, result);
            assertEquals(4, result.get(result.size() - 1)); // root last
        }

        private void postOrder(TreeNode node, List<Integer> result) {
            if (node == null) return;
            postOrder(node.left, result);
            postOrder(node.right, result);
            result.add(node.val);
        }

        @Test
        @DisplayName("Level-order (BFS) traversal  →  O(n)")
        void levelOrder() {
            TreeNode root = buildBST(4, 2, 6, 1, 3, 5, 7);
            List<Integer> result = new ArrayList<>();
            Queue<TreeNode> q = new ArrayDeque<>();
            q.offer(root);
            while (!q.isEmpty()) {
                TreeNode node = q.poll();
                result.add(node.val);
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
            assertEquals(4, result.get(0)); // level 0: root=4
            assertEquals(7, result.size());
        }

        @Test
        @DisplayName("Get height/depth of tree  →  O(n)")
        void treeHeight() {
            TreeNode root = buildBST(4, 2, 6, 1, 3, 5, 7);
            assertEquals(3, height(root)); // balanced: height=3
        }

        private int height(TreeNode node) {
            if (node == null) return 0;
            return 1 + Math.max(height(node.left), height(node.right));
        }

        @Test
        @DisplayName("Count nodes in tree  →  O(n)")
        void countNodes() {
            TreeNode root = buildBST(4, 2, 6, 1, 3, 5, 7);
            assertEquals(7, countNodes(root));
        }

        private int countNodes(TreeNode node) {
            if (node == null) return 0;
            return 1 + countNodes(node.left) + countNodes(node.right);
        }

        @Test
        @DisplayName("Check if two trees are identical  →  O(n)")
        void identical() {
            TreeNode t1 = buildBST(4, 2, 6);
            TreeNode t2 = buildBST(4, 2, 6);
            assertTrue(isIdentical(t1, t2));
        }

        private boolean isIdentical(TreeNode a, TreeNode b) {
            if (a == null && b == null) return true;
            if (a == null || b == null) return false;
            return a.val == b.val && isIdentical(a.left, b.left) && isIdentical(a.right, b.right);
        }

        @Test
        @DisplayName("Lowest Common Ancestor (LCA)  →  O(n)")
        void lowestCommonAncestor() {
            TreeNode root = buildBST(6, 2, 8, 0, 4, 7, 9, 3, 5);
            TreeNode lca = lcaBST(root, 2, 8);
            assertEquals(6, lca.val);
            lca = lcaBST(root, 2, 4);
            assertEquals(2, lca.val);
        }

        private TreeNode lcaBST(TreeNode root, int p, int q) {
            if (p < root.val && q < root.val) return lcaBST(root.left, p, q);
            if (p > root.val && q > root.val) return lcaBST(root.right, p, q);
            return root;
        }
    }
}
