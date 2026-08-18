package com.org.interview;

// Q4.6 Design an algorithm to find the first common ancestor of two nodes in a binary tree.
// Avoid storing additional nodes in a data structure.
// NOTE: This is not necessarily a BST.
public class Q25_FirstCommonAncestor {

    static class Node {
        int key;
        Node left, right, parent;
        Node(int k, Node p) { key = k; parent = p; }
    }

    static Node createMinimalTree(int[] a, Node parent, int start, int end) {
        if (start > end) return null;
        int mid = (start + end) / 2;
        Node node = new Node(a[mid], parent);
        node.left = createMinimalTree(a, node, start, mid - 1);
        node.right = createMinimalTree(a, node, mid + 1, end);
        return node;
    }

    static Node findNode(Node root, int key) {
        if (root == null) return null;
        if (root.key == key) return root;
        Node found = findNode(root.left, key);
        return found != null ? found : findNode(root.right, key);
    }

    // Approach 1: Walk up both chains, count depth, then advance deeper pointer
    // so both meet at the same level, then walk up together - O(d), no extra structure
    private static int depth(Node n) {
        int d = 0;
        while (n != null) { d++; n = n.parent; }
        return d;
    }

    /** Returns the first ancestor. */
    public static Node firstAncestor(Node n1, Node n2) {
        int d1 = depth(n1), d2 = depth(n2);
        while (d1 > d2) { n1 = n1.parent; d1--; }
        while (d2 > d1) { n2 = n2.parent; d2--; }
        while (n1 != n2) { n1 = n1.parent; n2 = n2.parent; }
        return n1;
    }

    // Approach 2: No parent pointers - covers() check - no extra structure
    private static boolean covers(Node root, Node target) {
        if (root == null) return false;
        if (root == target) return true;
        return covers(root.left, target) || covers(root.right, target);
    }

    /** Returns the first ancestor no buffer. */
    public static Node firstAncestorNoBuffer(Node root, Node n1, Node n2) {
        if (root == null || root == n1 || root == n2) return root;
        boolean n1Left = covers(root.left, n1);
        boolean n2Left = covers(root.left, n2);
        if (n1Left != n2Left) return root;
        Node child = n1Left ? root.left : root.right;
        return firstAncestorNoBuffer(child, n1, n2);
    }

    /** Application entry point. */
    public static void main(String[] args) {
        int[] a = {0, 1, 2, 3, 4, 5, 6};
        Node root = createMinimalTree(a, null, 0, a.length - 1);
        Node n1 = findNode(root, 0);
        Node n2 = findNode(root, 4);
        Node ans = firstAncestor(n1, n2);
        System.out.println("Common ancestor (depth walk): " + (ans != null ? ans.key : "none"));
        Node ans2 = firstAncestorNoBuffer(root, n1, n2);
        System.out.println("Common ancestor (no buffer):  " + (ans2 != null ? ans2.key : "none"));
    }
}
