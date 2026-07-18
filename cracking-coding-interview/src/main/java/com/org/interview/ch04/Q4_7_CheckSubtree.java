package com.org.interview.ch04;

// Q4.7 You have two very large binary trees T1 and T2.
// Create an algorithm to decide if T2 is a subtree of T1.
public class Q4_7_CheckSubtree {

    static class Node {
        int key;
        Node left, right;
        Node(int k) { key = k; }
    }

    static Node createMinimalTree(int[] a, int start, int end) {
        if (start > end) return null;
        int mid = (start + end) / 2;
        Node node = new Node(a[mid]);
        node.left = createMinimalTree(a, start, mid - 1);
        node.right = createMinimalTree(a, mid + 1, end);
        return node;
    }

    private static boolean match(Node r1, Node r2) {
        if (r1 == null && r2 == null) return true;
        if (r1 == null || r2 == null) return false;
        if (r1.key != r2.key) return false;
        return match(r1.left, r2.left) && match(r1.right, r2.right);
    }

    private static boolean subtree(Node r1, Node r2) {
        if (r1 == null) return false;
        if (r1.key == r2.key && match(r1, r2)) return true;
        return subtree(r1.left, r2) || subtree(r1.right, r2);
    }

    /** Returns the contains tree. */
    public static boolean containsTree(Node r1, Node r2) {
        if (r2 == null) return true;
        return subtree(r1, r2);
    }

    /** Application entry point. */
    public static void main(String[] args) {
        int[] a1 = {0, 1, 2, 3, 4, 5, 6};
        int[] a2 = {0, 1, 2};
        Node r1 = createMinimalTree(a1, 0, a1.length - 1);
        Node r2 = createMinimalTree(a2, 0, a2.length - 1);
        System.out.println("T1 contains T2: " + containsTree(r1, r2)); // true

        int[] a3 = {7, 8, 9};
        Node r3 = createMinimalTree(a3, 0, a3.length - 1);
        System.out.println("T1 contains T3: " + containsTree(r1, r3)); // false
    }
}
