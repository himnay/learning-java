package com.org.interview.ch03;

// Q3.3 SetOfStacks: composed of several stacks, creates a new stack once previous exceeds capacity.
// push() and pop() should behave identically to a single stack.
// FOLLOW UP: Implement popAt(int index) to pop from a specific sub-stack.
public class Q3_3_SetOfStacks {

    static class SetOfStacks {
        private final int[][] stacks;
        private final int[] tops;
        private int currentStack;
        private final int subCapacity;
        private final int maxStacks;

        SetOfStacks(int subCapacity, int maxStacks) {
            this.subCapacity = subCapacity;
            this.maxStacks = maxStacks;
            stacks = new int[maxStacks][subCapacity];
            tops = new int[maxStacks];
            for (int i = 0; i < maxStacks; i++) tops[i] = -1;
            currentStack = 0;
        }

        void push(int val) {
            if (tops[currentStack] == subCapacity - 1) currentStack++;
            stacks[currentStack][++tops[currentStack]] = val;
        }

        int pop() {
            while (currentStack > 0 && tops[currentStack] == -1) currentStack--;
            return stacks[currentStack][tops[currentStack]--];
        }

        // FOLLOW UP: pop from a specific sub-stack
        int popAt(int idx) {
            if (idx >= maxStacks || tops[idx] == -1) throw new RuntimeException("Invalid index");
            return stacks[idx][tops[idx]--];
        }

        int peek() {
            int s = currentStack;
            while (s > 0 && tops[s] == -1) s--;
            return stacks[s][tops[s]];
        }

        boolean isEmpty() {
            return currentStack == 0 && tops[0] == -1;
        }
    }

    public static void main(String[] args) {
        SetOfStacks ss = new SetOfStacks(100, 10);
        for (int i = 0; i < 301; i++) ss.push(i);
        System.out.println("Top: " + ss.peek());
        ss.popAt(0);
        System.out.println("After popAt(0), popped sub-stack 0 top. Main top: " + ss.peek());
        while (!ss.isEmpty()) ss.pop();
        System.out.println("Empty: " + ss.isEmpty());
    }
}
