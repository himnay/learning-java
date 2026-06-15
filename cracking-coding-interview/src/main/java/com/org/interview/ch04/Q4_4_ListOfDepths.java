package com.org.interview.ch04;

// Q4.4 Given a binary search tree, create a linked list of all nodes at each depth.
// Custom queue and linked list - no library data structures.
public class Q4_4_ListOfDepths {

    static class Node {
        int key;
        Node left, right;
        Node(int k) { key = k; }
    }

    // Singly-linked list node used to represent depth lists
    static class ListNode {
        Node treeNode;
        ListNode next;
        ListNode(Node n) { treeNode = n; }
    }

    static class ListHead {
        ListNode head, tail;
        void add(Node n) {
            ListNode ln = new ListNode(n);
            if (head == null) { head = tail = ln; }
            else { tail.next = ln; tail = ln; }
        }
    }

    // Custom queue for BFS
    static class NodeQueue {
        private final Node[] buf;
        private int head, tail, size;
        NodeQueue(int cap) { buf = new Node[cap]; }
        void offer(Node n) { buf[tail++ % buf.length] = n; size++; }
        Node poll()        { size--; return buf[head++ % buf.length]; }
        boolean isEmpty()  { return size == 0; }
        int size()         { return size; }
    }

    static Node createMinimalTree(int[] a, int start, int end) {
        if (start > end) return null;
        int mid = (start + end) / 2;
        Node node = new Node(a[mid]);
        node.left = createMinimalTree(a, start, mid - 1);
        node.right = createMinimalTree(a, mid + 1, end);
        return node;
    }

    public static ListHead[] findLevelLists(Node root, int maxDepth) {
        ListHead[] result = new ListHead[maxDepth];
        for (int i = 0; i < maxDepth; i++) result[i] = new ListHead();
        NodeQueue queue = new NodeQueue(maxDepth * 2);
        queue.offer(root);
        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node n = queue.poll();
                result[level].add(n);
                if (n.left != null) queue.offer(n.left);
                if (n.right != null) queue.offer(n.right);
            }
            level++;
        }
        return result;
    }

    public static void main(String[] args) {
        int[] a = {0, 1, 2, 3, 4, 5, 6};
        Node root = createMinimalTree(a, 0, a.length - 1);
        ListHead[] lists = findLevelLists(root, 10);
        for (int i = 0; i < lists.length && lists[i].head != null; i++) {
            System.out.print("Level " + i + ": ");
            ListNode curr = lists[i].head;
            while (curr != null) { System.out.print(curr.treeNode.key + " "); curr = curr.next; }
            System.out.println();
        }
    }
}
