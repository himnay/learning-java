package com.org.interview.ch02;

// Q2.5 Given a circular linked list, return the node at the beginning of the loop.
// EXAMPLE: A -> B -> C -> D -> E -> C [same C] => Output: C
public class Q2_5_CircularLinkedList {

    static class Node {
        int data;
        Node next;
        Node(int d) { data = d; }
    }

    static Node buildCircularList(int[] a, int loopStart) {
        Node head = null, tail = null, loopNode = null;
        for (int i = 0; i < a.length; i++) {
            Node nd = new Node(a[i]);
            if (i == loopStart) loopNode = nd;
            if (head == null) { head = tail = nd; }
            else { tail.next = nd; tail = nd; }
        }
        if (tail != null) tail.next = loopNode;
        return head;
    }

    // Floyd's cycle detection - O(n) time, O(1) space (no library functions)
    public static Node findLoopStart(Node head) {
        if (head == null) return null;
        Node fast = head, slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) break;
        }
        if (fast == null || fast.next == null) return null;
        slow = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return fast;
    }

    public static void main(String[] args) {
        int[] a = {3, 2, 1, 4, 5, 6, 7, 8, 9, 1};
        Node head = buildCircularList(a, 9);
        Node loopNode = findLoopStart(head);
        System.out.println("Loop start: " + (loopNode != null ? loopNode.data : "none"));
    }
}
