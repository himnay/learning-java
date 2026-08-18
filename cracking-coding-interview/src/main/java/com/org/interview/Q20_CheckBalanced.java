package com.org.interview;

// Q4.1 Implement a function to check if a tree is balanced.
// A balanced tree: no two leaf nodes differ in distance from the root by more than one.
public class Q20_CheckBalanced {

    static class Node {
        int key;
        Node left, right, parent;
        Node(int k, Node p) { key = k; parent = p; }
    }

    static Node insert(Node root, int x, Node parent) {
        if (root == null) return new Node(x, parent);
        if (x < root.key) root.left = insert(root.left, x, root);
        else root.right = insert(root.right, x, root);
        return root;
    }

    private static int minDepth = Integer.MAX_VALUE;
    private static int maxDepth = Integer.MIN_VALUE;

    private static void getLeafDepths(Node node, int depth) {
        if (node == null) return;
        if (node.left == null && node.right == null) {
            minDepth = Math.min(minDepth, depth);
            maxDepth = Math.max(maxDepth, depth);
            return;
        }
        getLeafDepths(node.left, depth + 1);
        getLeafDepths(node.right, depth + 1);
    }

    public static boolean isBalanced(Node root) {
        if (root == null) return true;
        minDepth = Integer.MAX_VALUE;
        maxDepth = Integer.MIN_VALUE;
        getLeafDepths(root, 1);
        return (maxDepth - minDepth) <= 1;
    }

    // Better O(n) approach: returns -1 on unbalanced, else height
    /** Checks height. */
    public static int checkHeight(Node node) {
        if (node == null) return 0;
        int leftH = checkHeight(node.left);
        if (leftH == -1) return -1;
        int rightH = checkHeight(node.right);
        if (rightH == -1) return -1;
        if (Math.abs(leftH - rightH) > 1) return -1;
        return Math.max(leftH, rightH) + 1;
    }

    /** Application entry point. */
    public static void main(String[] args) {
        int[] a = {5, 3, 8, 1, 4, 7, 10, 2, 6, 9, 11, 12};
        Node root = null;
        for (int v : a) root = insert(root, v, null);
        System.out.println("Balanced (leaf depth): " + isBalanced(root));
        System.out.println("Balanced (height):     " + (checkHeight(root) != -1));
    }
}
