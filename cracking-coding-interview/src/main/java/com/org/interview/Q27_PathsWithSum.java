package com.org.interview;

import java.util.ArrayList;
import java.util.List;

// Q4.8 You are given a binary tree where each node contains a value.
// Design an algorithm to print all paths which sum up to a given value.
// The path does not have to start at the root.
public class Q27_PathsWithSum {

    static class Node {
        int key;
        Node left, right;
        Node(int k) { key = k; }
    }

    static Node createTree(int[] a, int start, int end) {
        if (start > end) return null;
        int mid = (start + end) / 2;
        Node node = new Node(a[mid]);
        node.left = createTree(a, start, mid - 1);
        node.right = createTree(a, mid + 1, end);
        return node;
    }

    // For each node, check all paths going upward (toward root) starting from that node
    /** Finds sum paths. */
    public static void findSumPaths(Node node, int target) {
        if (node == null) return;
        findPathsFromNode(node, target, new ArrayList<>());
        findSumPaths(node.left, target);
        findSumPaths(node.right, target);
    }

    private static void findPathsFromNode(Node node, int remaining, List<Integer> path) {
        if (node == null) return;
        path.add(node.key);
        if (node.key == remaining) System.out.println(path);
        findPathsFromNode(node.left, remaining - node.key, new ArrayList<>(path));
        findPathsFromNode(node.right, remaining - node.key, new ArrayList<>(path));
    }

    /** Application entry point. */
    public static void main(String[] args) {
        int[] a = {4, 3, 8, 5, 2, 1, 6};
        Node root = createTree(a, 0, a.length - 1);
        System.out.println("Paths summing to 8:");
        findSumPaths(root, 8);
    }
}
