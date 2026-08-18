package com.org.interview;

// Q3.4 Towers of Hanoi - move N disks from rod A to rod C using B as auxiliary.
// Iterative implementation using a custom stack (no library).
public class Q17_TowersOfHanoi {

    static class Op {
        int begin, end;
        char src, bri, dst;
        Op(int begin, int end, char src, char bri, char dst) {
            this.begin = begin; this.end = end;
            this.src = src; this.bri = bri; this.dst = dst;
        }
    }

    static class OpStack {
        private final Op[] buf;
        private int top;
        OpStack(int cap) { buf = new Op[cap]; top = -1; }
        void push(Op op) { buf[++top] = op; }
        Op pop()         { return buf[top--]; }
        boolean isEmpty(){ return top == -1; }
    }

    /** Handles hanoi iterative. */
    public static void hanoiIterative(int n, char src, char bri, char dst) {
        OpStack stack = new OpStack(n * 2 + 10);
        stack.push(new Op(1, n, src, bri, dst));
        while (!stack.isEmpty()) {
            Op op = stack.pop();
            if (op.begin == op.end) {
                System.out.println("Move disk " + op.begin + " from " + op.src + " to " + op.dst);
            } else {
                stack.push(new Op(op.begin, op.end - 1, op.bri, op.src, op.dst));
                stack.push(new Op(op.end, op.end, op.src, op.bri, op.dst));
                stack.push(new Op(op.begin, op.end - 1, op.src, op.dst, op.bri));
            }
        }
    }

    /** Handles hanoi recursive. */
    public static void hanoiRecursive(int n, char src, char bri, char dst) {
        if (n == 0) return;
        hanoiRecursive(n - 1, src, dst, bri);
        System.out.println("Move disk " + n + " from " + src + " to " + dst);
        hanoiRecursive(n - 1, bri, src, dst);
    }

    /** Application entry point. */
    public static void main(String[] args) {
        int n = 3;
        System.out.println("=== Iterative ===");
        hanoiIterative(n, 'A', 'B', 'C');
        System.out.println("=== Recursive ===");
        hanoiRecursive(n, 'A', 'B', 'C');
    }
}
