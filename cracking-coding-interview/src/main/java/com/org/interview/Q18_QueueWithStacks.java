package com.org.interview;

// Q3.5 Implement a MyQueue (FIFO) class using two stacks (FILO).
// Custom array-based stack - no library data structures.
public class Q18_QueueWithStacks {

    static class IntStack {
        private final int[] buf;
        private int top;
        IntStack(int cap) { buf = new int[cap]; top = -1; }
        void push(int v)  { buf[++top] = v; }
        int pop()         { return buf[top--]; }
        int peek()        { return buf[top]; }
        boolean isEmpty() { return top == -1; }
        int size()        { return top + 1; }
    }

    // Lazy transfer: move sIn to sOut only when sOut is empty
    static class MyQueue {
        private final IntStack sIn, sOut;

        MyQueue(int cap) { sIn = new IntStack(cap); sOut = new IntStack(cap); }

        void push(int val) { sIn.push(val); }

        int pop() {
            transferIfNeeded();
            return sOut.pop();
        }

        int front() {
            transferIfNeeded();
            return sOut.peek();
        }

        int back() {
            return sIn.isEmpty() ? -1 : sIn.peek();
        }

        int size()        { return sIn.size() + sOut.size(); }
        boolean isEmpty() { return sIn.isEmpty() && sOut.isEmpty(); }

        private void transferIfNeeded() {
            if (sOut.isEmpty()) {
                while (!sIn.isEmpty()) sOut.push(sIn.pop());
            }
        }
    }

    /** Application entry point. */
    public static void main(String[] args) {
        MyQueue q = new MyQueue(20);
        for (int i = 0; i < 10; i++) q.push(i);
        System.out.println("front=" + q.front() + " back=" + q.back() + " size=" + q.size());
        q.pop();
        q.push(10);
        System.out.println("front=" + q.front() + " back=" + q.back());
    }
}
