package com.org.java.collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Heap / Priority Queue - Complexity Reference")
class HeapPriorityQueueTest {

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(log n) — PriorityQueue core operations")
    class CoreOps {

        @Test
        @DisplayName("Offer (insert) element  →  O(log n)")
        void offer() {
            PriorityQueue<Integer> minHeap = new PriorityQueue<>();
            minHeap.offer(5); minHeap.offer(1); minHeap.offer(3);
            assertEquals(3, minHeap.size());
            assertEquals(1, minHeap.peek()); // min at top
        }

        @Test
        @DisplayName("Poll (remove top) element  →  O(log n)")
        void poll() {
            PriorityQueue<Integer> minHeap = new PriorityQueue<>(Arrays.asList(5, 1, 3, 2, 4));
            assertEquals(1, minHeap.poll());
            assertEquals(2, minHeap.poll());
        }

        @Test
        @DisplayName("Peek top element (no removal)  →  O(1)")
        void peek() {
            PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
            maxHeap.offer(3); maxHeap.offer(7); maxHeap.offer(1);
            assertEquals(7, maxHeap.peek()); // max at top — O(1)
        }

        @Test
        @DisplayName("Max-heap using reverseOrder comparator  →  O(log n) for offer/poll")
        void maxHeap() {
            PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
            maxHeap.offer(5); maxHeap.offer(1); maxHeap.offer(9); maxHeap.offer(3);
            assertEquals(9, maxHeap.poll()); // largest first
            assertEquals(5, maxHeap.poll());
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(n log n) — Heap sort / drain")
    class HeapSort {

        @Test
        @DisplayName("Sort array using PriorityQueue (heap sort)  →  O(n log n)")
        void heapSort() {
            int[] unsorted = {5, 2, 8, 1, 9, 3};
            PriorityQueue<Integer> minHeap = new PriorityQueue<>();
            for (int v : unsorted) minHeap.offer(v);
            int[] sorted = new int[unsorted.length];
            for (int i = 0; i < sorted.length; i++) sorted[i] = minHeap.poll();
            assertArrayEquals(new int[]{1, 2, 3, 5, 8, 9}, sorted);
        }

        @Test
        @DisplayName("Build heap from n elements  →  O(n) via Collections.heapify pattern")
        void buildHeap() {
            List<Integer> items = Arrays.asList(5, 2, 8, 1, 9, 3, 7);
            PriorityQueue<Integer> heap = new PriorityQueue<>(items); // O(n) heap construction
            assertEquals(7, heap.size());
            assertEquals(1, heap.peek());
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Heap Pattern Problems")
    class HeapPatterns {

        @Test
        @DisplayName("Kth largest element (min-heap of size k)  →  O(n log k)")
        void kthLargest() {
            int[] nums = {3, 2, 1, 5, 6, 4};
            int k = 2;
            PriorityQueue<Integer> minHeap = new PriorityQueue<>();
            for (int n : nums) {
                minHeap.offer(n);
                if (minHeap.size() > k) minHeap.poll(); // evict smallest
            }
            assertEquals(5, minHeap.peek()); // 2nd largest
        }

        @Test
        @DisplayName("K closest numbers to target (max-heap)  →  O(n log k)")
        void kClosest() {
            int[] nums = {1, 2, 3, 4, 5};
            int target = 3, k = 2;
            PriorityQueue<int[]> maxHeap = new PriorityQueue<>(
                (a, b) -> b[0] != a[0] ? b[0] - a[0] : b[1] - a[1]
            );
            for (int n : nums) {
                maxHeap.offer(new int[]{Math.abs(n - target), n});
                if (maxHeap.size() > k) maxHeap.poll();
            }
            assertEquals(2, maxHeap.size());
        }

        @Test
        @DisplayName("Merge K sorted arrays  →  O(n log k)")
        void mergeKSorted() {
            int[][] arrays = {{1, 4, 7}, {2, 5, 8}, {3, 6, 9}};
            PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
            for (int i = 0; i < arrays.length; i++) minHeap.offer(new int[]{arrays[i][0], i, 0});
            List<Integer> result = new ArrayList<>();
            while (!minHeap.isEmpty()) {
                int[] top = minHeap.poll();
                result.add(top[0]);
                int row = top[1], col = top[2];
                if (col + 1 < arrays[row].length)
                    minHeap.offer(new int[]{arrays[row][col + 1], row, col + 1});
            }
            assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9), result);
        }

        @Test
        @DisplayName("Top K frequent elements  →  O(n log k)")
        void topKFrequent() {
            int[] nums = {1, 1, 1, 2, 2, 3};
            int k = 2;
            Map<Integer, Integer> freq = new HashMap<>();
            for (int n : nums) freq.put(n, freq.getOrDefault(n, 0) + 1);
            PriorityQueue<Map.Entry<Integer, Integer>> minHeap =
                new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));
            for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
                minHeap.offer(e);
                if (minHeap.size() > k) minHeap.poll();
            }
            Set<Integer> topK = new HashSet<>();
            while (!minHeap.isEmpty()) topK.add(minHeap.poll().getKey());
            assertTrue(topK.contains(1));
            assertTrue(topK.contains(2));
        }
    }
}
