package com.org.interview.ch02;

// Q2.2 Implement an algorithm to find the Nth to last element of a singly linked list.
public class Q2_2_NthToLast {

    static class Node {
        int data;
        Node next;
        Node(int d) { data = d; }
    }

    static Node buildList(int... a) {
        Node head = null, tail = null;
        for (int v : a) {
            Node nd = new Node(v);
            if (head == null) { head = tail = nd; }
            else { tail.next = nd; tail = nd; }
        }
        return head;
    }

    // Two-pointer approach - O(n)
    /** Finds nth to last. */
    public static Node findNthToLast(Node head, int n) {
        if (head == null || n < 1) return null;
        Node fast = head, slow = head;
        for (int i = 0; i < n; i++) {
            if (fast == null) return null; // list shorter than n
            fast = fast.next;
        }
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    // Recursive approach - returns index from end
    private static int[] counter = {0};
    private static Node result = null;

    /** Finds nth to last recursive. */
    public static void findNthToLastRecursive(Node head, int n) {
        if (head == null) return;
        findNthToLastRecursive(head.next, n);
        counter[0]++;
        if (counter[0] == n) result = head;
    }

    /** Application entry point. */
    public static void main(String[] args) {
        Node head = buildList(9, 2, 1, 6, 5, 4, 5, 7, 2, 1);
        Node p = findNthToLast(head, 6);
        System.out.println(p != null ? p.data : "list too short");

        counter[0] = 0;
        result = null;
        findNthToLastRecursive(head, 6);
        System.out.println(result != null ? result.data : "list too short");
    }
}
