package com.org.interview.ch04;

// Q4.5 Write an algorithm to find the 'next' node (in-order successor) of a given node
// in a BST where each node has a link to its parent.
public class Q4_5_InorderSuccessor {

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

    private static Node leftmost(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    // If node has right child: successor = leftmost of right subtree.
    // Otherwise: go up until we're coming from a left child.
    public static Node inorderSuccessor(Node node) {
        if (node == null) return null;
        if (node.right != null) return leftmost(node.right);
        Node current = node;
        Node parent = current.parent;
        while (parent != null && parent.right == current) {
            current = parent;
            parent = parent.parent;
        }
        return parent;
    }

    public static void main(String[] args) {
        int[] a = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Node root = createMinimalTree(a, null, 0, a.length - 1);
        System.out.println("Root: " + root.key);
        Node succ = inorderSuccessor(root);
        System.out.println("In-order successor of root: " + (succ != null ? succ.key : "none"));
        // Also test leaf node
        Node leaf = root.left.left.left; // leftmost
        System.out.println("Leftmost node: " + leaf.key);
        Node leafSucc = inorderSuccessor(leaf);
        System.out.println("Successor of leftmost: " + (leafSucc != null ? leafSucc.key : "none"));
    }
}
