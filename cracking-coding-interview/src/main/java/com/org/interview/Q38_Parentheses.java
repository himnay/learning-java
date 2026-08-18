package com.org.interview;

// Q8.5 Implement an algorithm to print all valid combinations of n pairs of parentheses.
// Example: input 3 -> ((())), (()()), (())(), ()(()), ()()()
public class Q38_Parentheses {

    private static char[] buffer;

    /** Prints parentheses. */
    public static void printParentheses(int left, int right, int cnt) {
        if (left < 0 || right < left) return;
        if (left == 0 && right == 0) {
            System.out.println(new String(buffer, 0, cnt));
        } else {
            if (left > 0) {
                buffer[cnt] = '(';
                printParentheses(left - 1, right, cnt + 1);
            }
            if (right > left) {
                buffer[cnt] = ')';
                printParentheses(left, right - 1, cnt + 1);
            }
        }
    }

    /** Application entry point. */
    public static void main(String[] args) {
        int n = 3;
        buffer = new char[n * 2];
        printParentheses(n, n, 0);
    }
}
