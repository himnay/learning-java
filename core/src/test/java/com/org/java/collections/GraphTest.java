package com.org.java.collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Graph (BFS / DFS) - Complexity Reference  O(V+E)")
class GraphTest {

    private List<List<Integer>> buildGraph(int vertices, int[][] edges) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < vertices; i++) adj.add(new ArrayList<>());
        for (int[] e : edges) { adj.get(e[0]).add(e[1]); adj.get(e[1]).add(e[0]); }
        return adj;
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(V+E) — BFS and DFS traversal")
    class Traversal {

        @Test
        @DisplayName("BFS traversal from source  →  O(V+E)")
        void bfs() {
            List<List<Integer>> adj = buildGraph(6, new int[][]{{0,1},{0,2},{1,3},{2,4},{3,5}});
            boolean[] visited = new boolean[6];
            List<Integer> order = new ArrayList<>();
            Queue<Integer> queue = new ArrayDeque<>();
            queue.offer(0); visited[0] = true;
            while (!queue.isEmpty()) {
                int node = queue.poll();
                order.add(node);
                for (int nb : adj.get(node)) {
                    if (!visited[nb]) { visited[nb] = true; queue.offer(nb); }
                }
            }
            assertEquals(6, order.size());
            assertEquals(0, order.get(0));
        }

        @Test
        @DisplayName("DFS traversal from source (iterative)  →  O(V+E)")
        void dfs() {
            List<List<Integer>> adj = buildGraph(5, new int[][]{{0,1},{0,2},{1,3},{2,4}});
            boolean[] visited = new boolean[5];
            List<Integer> order = new ArrayList<>();
            Deque<Integer> stack = new ArrayDeque<>();
            stack.push(0);
            while (!stack.isEmpty()) {
                int node = stack.pop();
                if (visited[node]) continue;
                visited[node] = true; order.add(node);
                for (int nb : adj.get(node)) if (!visited[nb]) stack.push(nb);
            }
            assertEquals(5, order.size());
        }

        @Test
        @DisplayName("DFS traversal (recursive)  →  O(V+E)")
        void dfsRecursive() {
            List<List<Integer>> adj = buildGraph(4, new int[][]{{0,1},{1,2},{2,3}});
            boolean[] visited = new boolean[4];
            List<Integer> order = new ArrayList<>();
            dfsRec(adj, 0, visited, order);
            assertEquals(Arrays.asList(0, 1, 2, 3), order);
        }

        private void dfsRec(List<List<Integer>> adj, int node, boolean[] visited, List<Integer> order) {
            visited[node] = true; order.add(node);
            for (int nb : adj.get(node)) if (!visited[nb]) dfsRec(adj, nb, visited, order);
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(V+E) — Graph problems")
    class GraphProblems {

        @Test
        @DisplayName("Detect cycle in undirected graph (DFS)  →  O(V+E)")
        void detectCycle() {
            List<List<Integer>> withCycle = buildGraph(4, new int[][]{{0,1},{1,2},{2,0},{0,3}});
            assertTrue(hasCycle(withCycle, 4));
            List<List<Integer>> noCycle = buildGraph(4, new int[][]{{0,1},{1,2},{2,3}});
            assertFalse(hasCycle(noCycle, 4));
        }

        private boolean hasCycle(List<List<Integer>> adj, int n) {
            boolean[] visited = new boolean[n];
            for (int i = 0; i < n; i++) if (!visited[i] && dfsCycle(adj, i, -1, visited)) return true;
            return false;
        }

        private boolean dfsCycle(List<List<Integer>> adj, int node, int parent, boolean[] visited) {
            visited[node] = true;
            for (int nb : adj.get(node)) {
                if (!visited[nb]) { if (dfsCycle(adj, nb, node, visited)) return true; }
                else if (nb != parent) return true;
            }
            return false;
        }

        @Test
        @DisplayName("Connected components count  →  O(V+E)")
        void connectedComponents() {
            List<List<Integer>> adj = buildGraph(7,
                new int[][]{{0,1},{0,2},{3,4}});  // components: {0,1,2}, {3,4}, {5}, {6}
            boolean[] visited = new boolean[7];
            int components = 0;
            for (int i = 0; i < 7; i++) {
                if (!visited[i]) { bfsVisit(adj, i, visited); components++; }
            }
            assertEquals(4, components);
        }

        private void bfsVisit(List<List<Integer>> adj, int src, boolean[] visited) {
            Queue<Integer> q = new ArrayDeque<>();
            q.offer(src); visited[src] = true;
            while (!q.isEmpty()) {
                int node = q.poll();
                for (int nb : adj.get(node)) if (!visited[nb]) { visited[nb] = true; q.offer(nb); }
            }
        }

        @Test
        @DisplayName("Topological sort (DAG, Kahn's BFS)  →  O(V+E)")
        void topologicalSort() {
            int n = 6;
            int[][] edges = {{5,2},{5,0},{4,0},{4,1},{2,3},{3,1}};
            List<List<Integer>> adj = new ArrayList<>();
            for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
            int[] inDegree = new int[n];
            for (int[] e : edges) { adj.get(e[0]).add(e[1]); inDegree[e[1]]++; }
            Queue<Integer> queue = new ArrayDeque<>();
            for (int i = 0; i < n; i++) if (inDegree[i] == 0) queue.offer(i);
            List<Integer> order = new ArrayList<>();
            while (!queue.isEmpty()) {
                int node = queue.poll(); order.add(node);
                for (int nb : adj.get(node)) if (--inDegree[nb] == 0) queue.offer(nb);
            }
            assertEquals(n, order.size()); // all nodes processed = no cycle
        }

        @Test
        @DisplayName("Number of islands (DFS on grid)  →  O(m*n)")
        void numIslands() {
            char[][] grid = {
                {'1','1','0','0','0'},
                {'1','1','0','0','0'},
                {'0','0','1','0','0'},
                {'0','0','0','1','1'}
            };
            assertEquals(3, numIslands(grid));
        }

        private int numIslands(char[][] grid) {
            int count = 0;
            for (int r = 0; r < grid.length; r++)
                for (int c = 0; c < grid[0].length; c++)
                    if (grid[r][c] == '1') { dfsIsland(grid, r, c); count++; }
            return count;
        }

        private void dfsIsland(char[][] grid, int r, int c) {
            if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || grid[r][c] != '1') return;
            grid[r][c] = '0';
            dfsIsland(grid, r+1, c); dfsIsland(grid, r-1, c);
            dfsIsland(grid, r, c+1); dfsIsland(grid, r, c-1);
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O((V+E) log V) — Dijkstra shortest path")
    class WeightedGraph {

        @Test
        @DisplayName("Dijkstra single-source shortest path  →  O((V+E) log V)")
        void dijkstra() {
            int n = 5;
            // adj: {neighbor, weight}
            List<List<int[]>> adj = new ArrayList<>();
            for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
            adj.get(0).add(new int[]{1, 4}); adj.get(0).add(new int[]{2, 1});
            adj.get(2).add(new int[]{1, 2}); adj.get(1).add(new int[]{3, 1});
            adj.get(2).add(new int[]{3, 5}); adj.get(3).add(new int[]{4, 3});
            int[] dist = dijkstra(adj, 0, n);
            assertEquals(0,  dist[0]);
            assertEquals(3,  dist[1]); // 0->2->1 = 1+2
            assertEquals(1,  dist[2]); // 0->2
            assertEquals(4,  dist[3]); // 0->2->1->3
            assertEquals(7,  dist[4]); // 0->2->1->3->4
        }

        private int[] dijkstra(List<List<int[]>> adj, int src, int n) {
            int[] dist = new int[n];
            Arrays.fill(dist, Integer.MAX_VALUE);
            dist[src] = 0;
            PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
            pq.offer(new int[]{src, 0});
            while (!pq.isEmpty()) {
                int[] cur = pq.poll();
                int node = cur[0], d = cur[1];
                if (d > dist[node]) continue;
                for (int[] nb : adj.get(node)) {
                    int newDist = dist[node] + nb[1];
                    if (newDist < dist[nb[0]]) { dist[nb[0]] = newDist; pq.offer(new int[]{nb[0], newDist}); }
                }
            }
            return dist;
        }
    }
}
