package com.org.interview.ch03;

// Q3.2 How would you design a stack which, in addition to push and pop,
// also has a function min which returns the minimum element?
// Push, pop and min should all operate in O(1) time.
public class Q3_2_StackWithMin {

    // Approach 1: each slot stores its own running minimum
    static class StackWithMin1 {
        private final int[] vals;
        private final int[] mins;
        private int top;

        StackWithMin1(int capacity) {
            vals = new int[capacity];
            mins = new int[capacity];
            top = -1;
        }

        void push(int val) {
            top++;
            vals[top] = val;
            mins[top] = (top == 0) ? val : (val < mins[top - 1] ? val : mins[top - 1]);
        }
        void pop()       { top--; }
        int peek()       { return vals[top]; }
        int min()        { return mins[top]; }
        boolean isEmpty(){ return top == -1; }
    }

    // Approach 2: separate min-tracking stack (saves space when few minimums pushed)
    static class StackWithMin2 {
        private final int[] data;
        private final int[] minStack;
        private int top, minTop;

        StackWithMin2(int capacity) {
            data = new int[capacity];
            minStack = new int[capacity];
            top = -1;
            minTop = -1;
        }

        void push(int val) {
            data[++top] = val;
            if (minTop == -1 || val <= minStack[minTop]) minStack[++minTop] = val;
        }
        void pop() {
            if (data[top] == minStack[minTop]) minTop--;
            top--;
        }
        int peek()        { return data[top]; }
        int min()         { return minTop == -1 ? Integer.MAX_VALUE : minStack[minTop]; }
        boolean isEmpty() { return top == -1; }
    }

    /** Application entry point. */
    public static void main(String[] args) {
        StackWithMin2 myStack = new StackWithMin2(1000);
        for (int i = 0; i < 20; i++) myStack.push(i);
        System.out.println("min=" + myStack.min() + " top=" + myStack.peek());
        myStack.push(-100);
        myStack.push(-100);
        System.out.println("min=" + myStack.min() + " top=" + myStack.peek());
        myStack.pop();
        System.out.println("min=" + myStack.min() + " top=" + myStack.peek());
    }
}
