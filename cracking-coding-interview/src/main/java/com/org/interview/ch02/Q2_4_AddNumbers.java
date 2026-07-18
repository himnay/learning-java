package com.org.interview.ch02;

// Q2.4 You have two numbers represented by a linked list, where each node contains a single digit.
// Digits are stored in reverse order (1's digit at head).
// Write a function that adds the two numbers and returns the sum as a linked list.
// Example: (3->1->5) + (5->9->2) = 8->0->8
public class Q2_4_AddNumbers {

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

    /** Adds lists. */
    public static Node addLists(Node p, Node q) {
        if (p == null) return q;
        if (q == null) return p;
        Node result = null, tail = null;
        int carry = 0;
        while (p != null || q != null || carry != 0) {
            int sum = carry;
            if (p != null) { sum += p.data; p = p.next; }
            if (q != null) { sum += q.data; q = q.next; }
            carry = sum / 10;
            Node r = new Node(sum % 10);
            if (result == null) { result = tail = r; }
            else { tail.next = r; tail = r; }
        }
        return result;
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
        Node p = buildList(1, 2, 9, 3); // 3921
        Node q = buildList(8, 8, 4);    // 488
        print(p);
        print(q);
        print(addLists(p, q));          // 9, 0, 4, 4 => 4409
    }
}
