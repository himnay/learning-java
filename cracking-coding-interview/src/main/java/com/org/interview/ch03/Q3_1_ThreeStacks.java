package com.org.interview.ch03;

// Q3.1 Describe how you could use a single array to implement three stacks.
public class Q3_1_ThreeStacks {

    // Approach 1: Fixed division - each stack gets equal partition
    static class ThreeStacksFixed {
        private final int[] buf;
        private final int[] top;
        private final int size;

        ThreeStacksFixed(int sizePerStack) {
            this.size = sizePerStack;
            buf = new int[size * 3];
            top = new int[]{-1, -1, -1};
        }

        void push(int stackNum, int val) {
            buf[stackNum * size + top[stackNum] + 1] = val;
            top[stackNum]++;
        }

        void pop(int stackNum) { top[stackNum]--; }

        int peek(int stackNum) { return buf[stackNum * size + top[stackNum]]; }

        boolean isEmpty(int stackNum) { return top[stackNum] == -1; }
    }

    // Approach 2: Flexible space - nodes with back-pointer index
    static class ThreeStacksFlexible {
        private static class Entry {
            int val, prevIdx;
            Entry(int v, int p) { val = v; prevIdx = p; }
        }

        private final Entry[] buf;
        private final int[] top;
        private int cur;

        ThreeStacksFlexible(int totalSize) {
            buf = new Entry[totalSize];
            top = new int[]{-1, -1, -1};
            cur = 0;
        }

        void push(int stackNum, int val) {
            buf[cur] = new Entry(val, top[stackNum]);
            top[stackNum] = cur++;
        }

        void pop(int stackNum) { top[stackNum] = buf[top[stackNum]].prevIdx; }

        int peek(int stackNum) { return buf[top[stackNum]].val; }

        boolean isEmpty(int stackNum) { return top[stackNum] == -1; }
    }

    public static void main(String[] args) {
        ThreeStacksFlexible mystack = new ThreeStacksFlexible(900);
        for (int i = 0; i < 10; i++) mystack.push(0, i);
        for (int i = 0; i < 20; i++) mystack.push(1, i);
        for (int i = 0; i < 110; i++) mystack.push(2, i);

        for (int i = 0; i < 3; i++) System.out.print(mystack.peek(i) + " ");
        System.out.println();

        for (int i = 0; i < 3; i++) { mystack.pop(i); System.out.print(mystack.peek(i) + " "); }
        System.out.println();

        mystack.push(0, 111); mystack.push(1, 222); mystack.push(2, 333);
        for (int i = 0; i < 3; i++) System.out.print(mystack.peek(i) + " ");
        System.out.println();
    }
}
