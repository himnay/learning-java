package com.org.interview.ch04;

// Q4.3 Given a sorted (increasing order) array, write an algorithm to create a
// binary search tree with minimal height.
public class Q4_3_MinimalTree {

    static class Node {
        int key;
        Node left, right, parent;
        Node(int k, Node p) { key = k; parent = p; }
    }

    /** Creates minimal tree. */
    public static Node createMinimalTree(int[] a, Node parent, int start, int end) {
        if (start > end) return null;
        int mid = (start + end) / 2;
        Node node = new Node(a[mid], parent);
        node.left = createMinimalTree(a, node, start, mid - 1);
        node.right = createMinimalTree(a, node, mid + 1, end);
        return node;
    }

    /** Returns the height. */
    public static int height(Node node) {
        if (node == null) return 0;
        return Math.max(height(node.left), height(node.right)) + 1;
    }

    /** Application entry point. */
    public static void main(String[] args) {
        int[] a = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        Node root = createMinimalTree(a, null, 0, a.length - 1);
        System.out.println("Height of minimal BST: " + height(root)); // 4
    }
}
