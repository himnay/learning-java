package com.org.java.collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Deque;
import java.util.ArrayDeque;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Stack - Complexity Reference")
class StackComplexityTest {

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(1) — Stack core operations")
    class ConstantTime {

        @Test
        @DisplayName("Push element  →  stack.push(v)  →  O(1)")
        void push() {
            Deque<Integer> stack = new ArrayDeque<>();
            stack.push(1);
            stack.push(2);
            stack.push(3);
            assertEquals(3, stack.size());
        }

        @Test
        @DisplayName("Pop element  →  stack.pop()  →  O(1)")
        void pop() {
            Deque<Integer> stack = new ArrayDeque<>();
            stack.push(10); stack.push(20); stack.push(30);
            assertEquals(30, stack.pop());
            assertEquals(2, stack.size());
        }

        @Test
        @DisplayName("Peek top element  →  stack.peek()  →  O(1)")
        void peek() {
            Deque<Integer> stack = new ArrayDeque<>();
            stack.push(5); stack.push(15);
            assertEquals(15, stack.peek()); // does not remove
            assertEquals(2, stack.size());
        }

        @Test
        @DisplayName("Check if empty  →  stack.isEmpty()  →  O(1)")
        void isEmpty() {
            Deque<Integer> stack = new ArrayDeque<>();
            assertTrue(stack.isEmpty());
            stack.push(1);
            assertFalse(stack.isEmpty());
        }

        @Test
        @DisplayName("Get size  →  stack.size()  →  O(1)")
        void size() {
            Deque<Integer> stack = new ArrayDeque<>();
            stack.push(1); stack.push(2);
            assertEquals(2, stack.size());
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(n) — Stack traversal / search")
    class LinearTime {

        @Test
        @DisplayName("Search for element  →  O(n) scan from top")
        void searchElement() {
            Deque<Integer> stack = new ArrayDeque<>();
            for (int i = 1; i <= 5; i++) stack.push(i);
            boolean found = false;
            for (int v : stack) if (v == 3) { found = true; break; }
            assertTrue(found);
        }

        @Test
        @DisplayName("Reverse a stack using auxiliary stack  →  O(n)")
        void reverseStack() {
            Deque<Integer> src = new ArrayDeque<>();
            Deque<Integer> aux = new ArrayDeque<>();
            src.push(1); src.push(2); src.push(3);     // top=3
            while (!src.isEmpty()) aux.push(src.pop()); // aux top=1
            int[] result = new int[3];
            int i = 0;
            while (!aux.isEmpty()) result[i++] = aux.pop();
            assertArrayEquals(new int[]{3, 2, 1}, result);
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Stack Pattern Problems")
    class StackPatterns {

        @Test
        @DisplayName("Valid parentheses check  →  O(n) time, O(n) space")
        void validParentheses() {
            assertTrue(isValid("()[]{}"));
            assertFalse(isValid("(]"));
            assertFalse(isValid("([)]"));
            assertTrue(isValid("{[]}"));
        }

        private boolean isValid(String s) {
            Deque<Character> stack = new ArrayDeque<>();
            for (char c : s.toCharArray()) {
                if (c == '(' || c == '[' || c == '{') { stack.push(c); continue; }
                if (stack.isEmpty()) return false;
                char top = stack.pop();
                if (c == ')' && top != '(') return false;
                if (c == ']' && top != '[') return false;
                if (c == '}' && top != '{') return false;
            }
            return stack.isEmpty();
        }

        @Test
        @DisplayName("Min-stack — track min with O(1) getMin  →  O(1) push/pop/min")
        void minStack() {
            Deque<Integer> stack = new ArrayDeque<>();
            Deque<Integer> minStack = new ArrayDeque<>();
            // push 5
            stack.push(5); minStack.push(5);
            // push 3
            stack.push(3); minStack.push(Math.min(3, minStack.peek()));
            // push 7
            stack.push(7); minStack.push(Math.min(7, minStack.peek()));

            assertEquals(3, (int) minStack.peek()); // min = 3
            stack.pop(); minStack.pop();             // remove 7
            assertEquals(3, (int) minStack.peek()); // min still 3
            stack.pop(); minStack.pop();             // remove 3
            assertEquals(5, (int) minStack.peek()); // min now 5
        }

        @Test
        @DisplayName("Evaluate RPN expression  →  O(n)")
        void evalRPN() {
            // ["2","1","+","3","*"] = (2+1)*3 = 9
            String[] tokens = {"2", "1", "+", "3", "*"};
            Deque<Integer> stack = new ArrayDeque<>();
            for (String t : tokens) {
                if (t.equals("+") || t.equals("-") || t.equals("*") || t.equals("/")) {
                    int b = stack.pop(), a = stack.pop();
                    switch (t) {
                        case "+" -> stack.push(a + b);
                        case "-" -> stack.push(a - b);
                        case "*" -> stack.push(a * b);
                        case "/" -> stack.push(a / b);
                    }
                } else {
                    stack.push(Integer.parseInt(t));
                }
            }
            assertEquals(9, (int) stack.pop());
        }

        @Test
        @DisplayName("Next greater element (monotonic stack)  →  O(n)")
        void nextGreaterElement() {
            int[] nums = {4, 1, 2, 3, 5};
            int[] result = new int[nums.length];
            Deque<Integer> stack = new ArrayDeque<>(); // holds indices
            java.util.Arrays.fill(result, -1);
            for (int i = 0; i < nums.length; i++) {
                while (!stack.isEmpty() && nums[stack.peek()] < nums[i])
                    result[stack.pop()] = nums[i];
                stack.push(i);
            }
            assertArrayEquals(new int[]{5, 2, 3, 5, -1}, result);
        }
    }
}
