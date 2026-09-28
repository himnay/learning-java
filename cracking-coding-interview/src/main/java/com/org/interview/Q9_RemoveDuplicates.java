package com.org.interview;

import java.util.HashSet;
import java.util.Set;

// Q2.1 Write code to remove duplicates from an unsorted linked list.
// FOLLOW UP: How would you solve this without a temporary buffer?
public class Q9_RemoveDuplicates {

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

    // With a hash set of the values seen so far - O(n) time, O(n) space.
    // (A boolean[] indexed by value only works for small non-negative values.)
    /** Removes duplicates. */
    public static void removeDuplicates(Node head) {
        if (head == null) return;
        Set<Integer> seen = new HashSet<>();
        seen.add(head.data);
        Node prev = head, curr = head.next;
        while (curr != null) {
            if (!seen.add(curr.data)) {
                prev.next = curr.next;
            } else {
                prev = curr;
            }
            curr = curr.next;
        }
    }

    // Without buffer - O(n^2) time, O(1) space
    /** Removes duplicates no buffer. */
    public static void removeDuplicatesNoBuffer(Node head) {
        Node curr = head;
        while (curr != null) {
            Node runner = curr;
            while (runner.next != null) {
                if (runner.next.data == curr.data) {
                    runner.next = runner.next.next;
                } else {
                    runner = runner.next;
                }
            }
            curr = curr.next;
        }
    }

    static void print(Node head) {
        while (head != null) {
            System.out.print(head.data + " ");
            head = head.next;
        }
        System.out.println();
    }

    /** Application entry point. */
    public static void main(String[] args) {
        Node head1 = buildList(3, 2, 1, 3, 5, 6, 2, 6, 3, 1);
        print(head1);
        removeDuplicates(head1);
        print(head1);

        Node head2 = buildList(3, 2, 1, 3, 5, 6, 2, 6, 3, 1);
        removeDuplicatesNoBuffer(head2);
        print(head2);
    }
}
