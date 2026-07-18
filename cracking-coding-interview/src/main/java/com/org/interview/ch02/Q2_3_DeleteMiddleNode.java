package com.org.interview.ch02;

// Q2.3 Implement an algorithm to delete a node in the middle of a single linked list,
// given only access to that node.
// Example: a->b->c->d->e, delete c -> a->b->d->e
public class Q2_3_DeleteMiddleNode {

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

    // Copy next node's data and skip it - O(1). Returns false if node is the last.
    /** Deletes node. */
    public static boolean deleteNode(Node c) {
        if (c == null || c.next == null) return false;
        Node next = c.next;
        c.data = next.data;
        c.next = next.next;
        return true;
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
        Node head = buildList(9, 2, 3, 1, 5, 6, 1, 2, 3, 6);
        // get 3rd node
        Node c = head;
        for (int i = 1; i < 3; i++) c = c.next;
        print(head);
        if (deleteNode(c)) print(head);
        else System.out.println("failure");
    }
}
