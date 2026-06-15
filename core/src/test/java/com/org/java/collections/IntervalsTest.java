package com.org.java.collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Intervals - Complexity Reference")
class IntervalsTest {

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(n log n) — Sort-based interval operations")
    class SortBased {

        @Test
        @DisplayName("Merge overlapping intervals  →  sort O(n log n) + sweep O(n)")
        void mergeIntervals() {
            int[][] intervals = {{1,3},{2,6},{8,10},{15,18}};
            Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
            List<int[]> merged = new ArrayList<>();
            merged.add(intervals[0]);
            for (int i = 1; i < intervals.length; i++) {
                int[] last = merged.get(merged.size() - 1);
                if (intervals[i][0] <= last[1]) last[1] = Math.max(last[1], intervals[i][1]);
                else merged.add(intervals[i]);
            }
            assertEquals(3, merged.size());
            assertArrayEquals(new int[]{1, 6}, merged.get(0));
            assertArrayEquals(new int[]{8, 10}, merged.get(1));
            assertArrayEquals(new int[]{15, 18}, merged.get(2));
        }

        @Test
        @DisplayName("Insert and merge new interval  →  O(n)")
        void insertInterval() {
            int[][] intervals = {{1,3},{6,9}};
            int[] newInterval = {2,5};
            List<int[]> result = new ArrayList<>();
            int i = 0, n = intervals.length;
            while (i < n && intervals[i][1] < newInterval[0]) result.add(intervals[i++]);
            while (i < n && intervals[i][0] <= newInterval[1]) {
                newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
                newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
                i++;
            }
            result.add(newInterval);
            while (i < n) result.add(intervals[i++]);
            assertEquals(2, result.size());
            assertArrayEquals(new int[]{1, 5}, result.get(0));
            assertArrayEquals(new int[]{6, 9}, result.get(1));
        }

        @Test
        @DisplayName("Minimum rooms for meetings (interval scheduling)  →  O(n log n)")
        void meetingRooms() {
            int[][] meetings = {{0,30},{5,10},{15,20}};
            // sort by start; use min-heap for end times
            Arrays.sort(meetings, Comparator.comparingInt(a -> a[0]));
            PriorityQueue<Integer> endTimes = new PriorityQueue<>();
            for (int[] m : meetings) {
                if (!endTimes.isEmpty() && endTimes.peek() <= m[0]) endTimes.poll();
                endTimes.offer(m[1]);
            }
            assertEquals(2, endTimes.size()); // 2 rooms needed
        }

        @Test
        @DisplayName("Non-overlapping intervals to remove (greedy)  →  O(n log n)")
        void eraseOverlapIntervals() {
            int[][] intervals = {{1,2},{2,3},{3,4},{1,3}};
            Arrays.sort(intervals, Comparator.comparingInt(a -> a[1])); // sort by end
            int removed = 0, end = Integer.MIN_VALUE;
            for (int[] iv : intervals) {
                if (iv[0] >= end) end = iv[1]; // no overlap
                else removed++;                 // overlap: remove current
            }
            assertEquals(1, removed);
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("O(n) — Sweep line / event-based")
    class SweepLine {

        @Test
        @DisplayName("Check if any interval overlaps (after sort)  →  O(n log n)")
        void hasOverlap() {
            int[][] intervals = {{0,30},{5,10},{15,20}};
            Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
            boolean overlaps = false;
            for (int i = 1; i < intervals.length; i++) {
                if (intervals[i][0] < intervals[i - 1][1]) { overlaps = true; break; }
            }
            assertTrue(overlaps);
        }

        @Test
        @DisplayName("Count overlapping intervals at each point  →  O(n log n)")
        void maxOverlapCount() {
            int[][] intervals = {{1,4},{2,5},{3,6}};
            List<int[]> events = new ArrayList<>();
            for (int[] iv : intervals) {
                events.add(new int[]{iv[0], 1});   // start
                events.add(new int[]{iv[1], -1});  // end
            }
            events.sort((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
            int cur = 0, maxOverlap = 0;
            for (int[] e : events) { cur += e[1]; maxOverlap = Math.max(maxOverlap, cur); }
            assertEquals(3, maxOverlap);
        }
    }
}
