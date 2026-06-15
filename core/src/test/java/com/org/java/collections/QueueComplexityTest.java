package com.org.java.collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Queue - Complexity Reference")
class QueueComplexityTest {

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(1) — Queue core operations")
    class ConstantTime {

        @Test
        @DisplayName("Enqueue (offer)  →  queue.offer(v)  →  O(1)")
        void offer() {
            Queue<Integer> q = new ArrayDeque<>();
            q.offer(1); q.offer(2); q.offer(3);
            assertEquals(3, q.size());
        }

        @Test
        @DisplayName("Dequeue (poll)  →  queue.poll()  →  O(1)")
        void poll() {
            Queue<Integer> q = new ArrayDeque<>();
            q.offer(10); q.offer(20); q.offer(30);
            assertEquals(10, q.poll()); // FIFO
            assertEquals(2, q.size());
        }

        @Test
        @DisplayName("Peek front  →  queue.peek()  →  O(1)")
        void peek() {
            Queue<Integer> q = new ArrayDeque<>();
            q.offer(5); q.offer(15);
            assertEquals(5, q.peek()); // does not remove
            assertEquals(2, q.size());
        }

        @Test
        @DisplayName("Check empty  →  queue.isEmpty()  →  O(1)")
        void isEmpty() {
            Queue<Integer> q = new ArrayDeque<>();
            assertTrue(q.isEmpty());
            q.offer(1);
            assertFalse(q.isEmpty());
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(n) — Queue traversal / search")
    class LinearTime {

        @Test
        @DisplayName("Search element  →  O(n) scan from front")
        void searchElement() {
            Queue<String> q = new ArrayDeque<>(Arrays.asList("a", "b", "c", "d"));
            boolean found = q.contains("c");
            assertTrue(found);
        }

        @Test
        @DisplayName("BFS level-order traversal of binary tree  →  O(n)")
        void bfsLevelOrder() {
            // Simple array-based tree: index i has children 2i+1 and 2i+2
            int[] tree = {1, 2, 3, 4, 5, 6, 7};
            List<Integer> order = new ArrayList<>();
            Queue<Integer> queue = new ArrayDeque<>();
            queue.offer(0);
            while (!queue.isEmpty()) {
                int idx = queue.poll();
                order.add(tree[idx]);
                int left = 2 * idx + 1, right = 2 * idx + 2;
                if (left < tree.length) queue.offer(left);
                if (right < tree.length) queue.offer(right);
            }
            assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7), order);
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(1) — Deque (double-ended queue)")
    class DequeOps {

        @Test
        @DisplayName("Add to both ends (Deque)  →  addFirst / addLast  →  O(1)")
        void addBothEnds() {
            Deque<Integer> deque = new ArrayDeque<>();
            deque.addFirst(2);
            deque.addFirst(1);
            deque.addLast(3);
            deque.addLast(4);
            assertArrayEquals(new int[]{1, 2, 3, 4},
                    deque.stream().mapToInt(Integer::intValue).toArray());
        }

        @Test
        @DisplayName("Remove from both ends (Deque)  →  pollFirst / pollLast  →  O(1)")
        void removeBothEnds() {
            Deque<Integer> deque = new ArrayDeque<>(Arrays.asList(1, 2, 3, 4, 5));
            assertEquals(1, deque.pollFirst());
            assertEquals(5, deque.pollLast());
            assertEquals(3, deque.size());
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Queue Pattern Problems")
    class QueuePatterns {

        @Test
        @DisplayName("BFS shortest path in grid  →  O(m*n)")
        void bfsShortestPath() {
            int[][] grid = {
                {0, 0, 0},
                {1, 1, 0},
                {0, 0, 0}
            };
            int dist = bfs(grid, 0, 0, 2, 2);
            assertEquals(4, dist); // (0,0) -> (0,1) -> (0,2) -> (1,2) -> (2,2)
        }

        private int bfs(int[][] grid, int sr, int sc, int er, int ec) {
            int rows = grid.length, cols = grid[0].length;
            boolean[][] visited = new boolean[rows][cols];
            Queue<int[]> q = new ArrayDeque<>();
            q.offer(new int[]{sr, sc, 0});
            visited[sr][sc] = true;
            int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
            while (!q.isEmpty()) {
                int[] cur = q.poll();
                if (cur[0] == er && cur[1] == ec) return cur[2];
                for (int[] d : dirs) {
                    int nr = cur[0] + d[0], nc = cur[1] + d[1];
                    if (nr >= 0 && nr < rows && nc >= 0 && nc < cols
                            && grid[nr][nc] == 0 && !visited[nr][nc]) {
                        visited[nr][nc] = true;
                        q.offer(new int[]{nr, nc, cur[2] + 1});
                    }
                }
            }
            return -1;
        }

        @Test
        @DisplayName("Sliding window maximum (monotonic deque)  →  O(n)")
        void slidingWindowMax() {
            int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
            int k = 3;
            int[] result = new int[nums.length - k + 1];
            Deque<Integer> deque = new ArrayDeque<>(); // stores indices
            for (int i = 0; i < nums.length; i++) {
                while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) deque.pollFirst();
                while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) deque.pollLast();
                deque.offerLast(i);
                if (i >= k - 1) result[i - k + 1] = nums[deque.peekFirst()];
            }
            assertArrayEquals(new int[]{3, 3, 5, 5, 6, 7}, result);
        }
    }
}
