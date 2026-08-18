package com.org.interview;

// Q4.2 Given a directed graph, design an algorithm to find out whether there is a route between two nodes.
// BFS with custom queue - no library data structures.
public class Q21_RouteBetweenNodes {

    static class IntQueue {
        private final int[] buf;
        private int head, tail, size;
        IntQueue(int cap) { buf = new int[cap]; head = tail = size = 0; }
        void offer(int v) { buf[tail++ % buf.length] = v; size++; }
        int poll()        { size--; return buf[head++ % buf.length]; }
        boolean isEmpty() { return size == 0; }
    }

    /** Returns whether route. */
    public static boolean hasRoute(boolean[][] graph, int n, int src, int dst) {
        boolean[] visited = new boolean[n];
        IntQueue queue = new IntQueue(n);
        queue.offer(src);
        visited[src] = true;
        while (!queue.isEmpty()) {
            int t = queue.poll();
            if (t == dst) return true;
            for (int i = 0; i < n; i++) {
                if (graph[t][i] && !visited[i]) {
                    queue.offer(i);
                    visited[i] = true;
                }
            }
        }
        return false;
    }

    /** Application entry point. */
    public static void main(String[] args) {
        int n = 7;
        boolean[][] g = new boolean[n][n];
        int[][] edges = {{0,1},{1,2},{2,3},{3,4},{0,5},{5,6}};
        for (int[] e : edges) g[e[0]][e[1]] = true;

        System.out.println("Route 0->6: " + hasRoute(g, n, 0, 6)); // true
        System.out.println("Route 4->0: " + hasRoute(g, n, 4, 0)); // false
        System.out.println("Route 0->3: " + hasRoute(g, n, 0, 3)); // true
    }
}
