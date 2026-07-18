package com.org.interview.ch03;

// Q3.6 Write a program to sort a stack in ascending order (smallest on top).
// Only push, pop, peek, isEmpty are allowed. No library data structures.
public class Q3_6_SortStack {

    static class IntStack {
        private final int[] buf;
        private int top;
        IntStack(int cap) { buf = new int[cap]; top = -1; }
        void push(int v)  { buf[++top] = v; }
        int pop()         { return buf[top--]; }
        int peek()        { return buf[top]; }
        boolean isEmpty() { return top == -1; }
    }

    // Insertion-sort using auxiliary stack - O(n^2) time, O(n) space
    /** Sorts stack. */
    public static IntStack sortStack(IntStack s, int cap) {
        IntStack tmp = new IntStack(cap);
        while (!s.isEmpty()) {
            int data = s.pop();
            while (!tmp.isEmpty() && tmp.peek() > data) {
                s.push(tmp.pop());
            }
            tmp.push(data);
        }
        return tmp;
    }

    /** Application entry point. */
    public static void main(String[] args) {
        IntStack s = new IntStack(20);
        int[] vals = {5, 1, 9, 3, 7, 2, 8, 4, 6, 0};
        for (int v : vals) s.push(v);
        IntStack sorted = sortStack(s, 20);
        System.out.print("Sorted (smallest on top): ");
        while (!sorted.isEmpty()) System.out.print(sorted.pop() + " ");
        System.out.println();
    }
}
