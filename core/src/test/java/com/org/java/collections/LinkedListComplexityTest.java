package com.org.java.collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Linked List - Complexity Reference")
class LinkedListComplexityTest {

    static class Node {
        int val;
        Node next;
        Node(int v) { val = v; }
    }

    private Node buildList(int... vals) {
        Node dummy = new Node(0);
        Node cur = dummy;
        for (int v : vals) { cur.next = new Node(v); cur = cur.next; }
        return dummy.next;
    }

    private int[] toArray(Node head, int maxLen) {
        int[] buf = new int[maxLen];
        int i = 0;
        while (head != null && i < maxLen) { buf[i++] = head.val; head = head.next; }
        return java.util.Arrays.copyOf(buf, i);
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(1) — Constant time operations")
    class ConstantTime {

        @Test
        @DisplayName("Insert at head  →  newNode.next = head  →  O(1)")
        void insertAtHead() {
            Node head = buildList(2, 3, 4);
            Node newHead = new Node(1);
            newHead.next = head;
            head = newHead;
            assertArrayEquals(new int[]{1, 2, 3, 4}, toArray(head, 10));
        }

        @Test
        @DisplayName("Delete head node  →  head = head.next  →  O(1)")
        void deleteHead() {
            Node head = buildList(1, 2, 3);
            head = head.next;
            assertArrayEquals(new int[]{2, 3}, toArray(head, 10));
        }

        @Test
        @DisplayName("Insert after given node  →  O(1)")
        void insertAfterNode() {
            Node head = buildList(1, 2, 4);
            Node second = head.next; // node with val=2
            Node newNode = new Node(3);
            newNode.next = second.next;
            second.next = newNode;
            assertArrayEquals(new int[]{1, 2, 3, 4}, toArray(head, 10));
        }

        @Test
        @DisplayName("Delete next node after given node  →  O(1)")
        void deleteAfterNode() {
            Node head = buildList(1, 2, 3, 4);
            Node first = head; // node val=1; delete node val=2
            first.next = first.next.next;
            assertArrayEquals(new int[]{1, 3, 4}, toArray(head, 10));
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(n) — Linear time operations")
    class LinearTime {

        @Test
        @DisplayName("Traverse linked list  →  O(n)")
        void traverse() {
            Node head = buildList(1, 2, 3, 4, 5);
            int sum = 0;
            for (Node cur = head; cur != null; cur = cur.next) sum += cur.val;
            assertEquals(15, sum);
        }

        @Test
        @DisplayName("Find node by value  →  O(n)")
        void findNode() {
            Node head = buildList(1, 2, 3, 4, 5);
            Node cur = head;
            while (cur != null && cur.val != 3) cur = cur.next;
            assertNotNull(cur);
            assertEquals(3, cur.val);
        }

        @Test
        @DisplayName("Get length of list  →  O(n)")
        void getLength() {
            Node head = buildList(1, 2, 3, 4, 5);
            int len = 0;
            for (Node cur = head; cur != null; cur = cur.next) len++;
            assertEquals(5, len);
        }

        @Test
        @DisplayName("Reverse linked list (iterative)  →  O(n)")
        void reverseList() {
            Node head = buildList(1, 2, 3, 4, 5);
            Node prev = null, cur = head;
            while (cur != null) {
                Node next = cur.next;
                cur.next = prev;
                prev = cur;
                cur = next;
            }
            head = prev;
            assertArrayEquals(new int[]{5, 4, 3, 2, 1}, toArray(head, 10));
        }

        @Test
        @DisplayName("Insert at tail  →  O(n)  (without tail pointer)")
        void insertAtTail() {
            Node head = buildList(1, 2, 3);
            Node cur = head;
            while (cur.next != null) cur = cur.next;
            cur.next = new Node(4);
            assertArrayEquals(new int[]{1, 2, 3, 4}, toArray(head, 10));
        }

        @Test
        @DisplayName("Delete node by value  →  O(n)")
        void deleteByValue() {
            Node head = buildList(1, 2, 3, 4, 5);
            Node dummy = new Node(0);
            dummy.next = head;
            Node cur = dummy;
            while (cur.next != null) {
                if (cur.next.val == 3) { cur.next = cur.next.next; break; }
                cur = cur.next;
            }
            assertArrayEquals(new int[]{1, 2, 4, 5}, toArray(dummy.next, 10));
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Linked List Pattern Problems")
    class LinkedListPatterns {

        @Test
        @DisplayName("Find middle of linked list (fast/slow)  →  O(n)")
        void findMiddle() {
            Node head = buildList(1, 2, 3, 4, 5);
            Node slow = head, fast = head;
            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
            }
            assertEquals(3, slow.val);
        }

        @Test
        @DisplayName("Detect cycle (Floyd's algorithm)  →  O(n)")
        void detectCycle() {
            Node head = buildList(1, 2, 3, 4);
            // no cycle
            assertFalse(hasCycle(head));
            // create cycle: tail -> node with val=2
            Node node2 = head.next;
            Node tail = head.next.next.next;
            tail.next = node2;
            assertTrue(hasCycle(head));
        }

        private boolean hasCycle(Node head) {
            Node slow = head, fast = head;
            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
                if (slow == fast) return true;
            }
            return false;
        }

        @Test
        @DisplayName("Nth node from end (two-pointer)  →  O(n)")
        void nthFromEnd() {
            Node head = buildList(1, 2, 3, 4, 5);
            int n = 2;
            Node fast = head, slow = head;
            for (int i = 0; i < n; i++) fast = fast.next;
            while (fast != null) { slow = slow.next; fast = fast.next; }
            assertEquals(4, slow.val); // 2nd from end
        }

        @Test
        @DisplayName("Merge two sorted linked lists  →  O(n+m)")
        void mergeSortedLists() {
            Node a = buildList(1, 3, 5);
            Node b = buildList(2, 4, 6);
            Node dummy = new Node(0), cur = dummy;
            while (a != null && b != null) {
                if (a.val <= b.val) { cur.next = a; a = a.next; }
                else { cur.next = b; b = b.next; }
                cur = cur.next;
            }
            cur.next = (a != null) ? a : b;
            assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6}, toArray(dummy.next, 10));
        }
    }
}
