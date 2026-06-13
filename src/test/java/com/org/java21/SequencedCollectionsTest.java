// FILE 10: src/test/java/com/org/java21/SequencedCollectionsTest.java
package com.org.java21;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Sequenced Collections (Java 21)")
class SequencedCollectionsTest {

    // ---------------------------------------------------------------------------
    // SequencedCollection — LinkedList
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("LinkedList.getFirst() returns the first element")
    void linkedListGetFirst() {
        LinkedList<String> list = new LinkedList<>(List.of("a", "b", "c"));
        assertEquals("a", list.getFirst());
    }

    @Test
    @DisplayName("LinkedList.getLast() returns the last element")
    void linkedListGetLast() {
        LinkedList<String> list = new LinkedList<>(List.of("a", "b", "c"));
        assertEquals("c", list.getLast());
    }

    @Test
    @DisplayName("LinkedList.addFirst() inserts element at the front")
    void linkedListAddFirst() {
        LinkedList<String> list = new LinkedList<>(List.of("b", "c"));
        list.addFirst("a");
        assertEquals("a", list.getFirst());
        assertEquals(3, list.size());
    }

    @Test
    @DisplayName("LinkedList.addLast() inserts element at the end")
    void linkedListAddLast() {
        LinkedList<String> list = new LinkedList<>(List.of("a", "b"));
        list.addLast("c");
        assertEquals("c", list.getLast());
        assertEquals(3, list.size());
    }

    @Test
    @DisplayName("LinkedList.removeFirst() removes and returns the first element")
    void linkedListRemoveFirst() {
        LinkedList<String> list = new LinkedList<>(List.of("x", "y", "z"));
        String removed = list.removeFirst();
        assertEquals("x", removed);
        assertEquals("y", list.getFirst());
        assertEquals(2, list.size());
    }

    @Test
    @DisplayName("LinkedList.removeLast() removes and returns the last element")
    void linkedListRemoveLast() {
        LinkedList<String> list = new LinkedList<>(List.of("x", "y", "z"));
        String removed = list.removeLast();
        assertEquals("z", removed);
        assertEquals("y", list.getLast());
        assertEquals(2, list.size());
    }

    @Test
    @DisplayName("LinkedList.reversed() returns elements in reverse order")
    void linkedListReversed() {
        LinkedList<Integer> list = new LinkedList<>(List.of(1, 2, 3, 4));
        SequencedCollection<Integer> rev = list.reversed();
        List<Integer> revList = new ArrayList<>(rev);
        assertEquals(List.of(4, 3, 2, 1), revList);
    }

    // ---------------------------------------------------------------------------
    // SequencedCollection — ArrayList
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("ArrayList.getFirst() returns the first element")
    void arrayListGetFirst() {
        ArrayList<String> list = new ArrayList<>(List.of("one", "two", "three"));
        assertEquals("one", list.getFirst());
    }

    @Test
    @DisplayName("ArrayList.getLast() returns the last element")
    void arrayListGetLast() {
        ArrayList<String> list = new ArrayList<>(List.of("one", "two", "three"));
        assertEquals("three", list.getLast());
    }

    @Test
    @DisplayName("ArrayList.addFirst() inserts at position 0")
    void arrayListAddFirst() {
        ArrayList<Integer> list = new ArrayList<>(List.of(2, 3, 4));
        list.addFirst(1);
        assertEquals(List.of(1, 2, 3, 4), list);
    }

    @Test
    @DisplayName("ArrayList.addLast() appends to the end")
    void arrayListAddLast() {
        ArrayList<Integer> list = new ArrayList<>(List.of(1, 2, 3));
        list.addLast(4);
        assertEquals(List.of(1, 2, 3, 4), list);
    }

    @Test
    @DisplayName("ArrayList.reversed() provides a reverse view")
    void arrayListReversed() {
        ArrayList<Integer> list = new ArrayList<>(List.of(10, 20, 30));
        SequencedCollection<Integer> rev = list.reversed();
        assertEquals(30, rev.getFirst());
        assertEquals(10, rev.getLast());
    }

    // ---------------------------------------------------------------------------
    // SequencedCollection — ArrayDeque
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("ArrayDeque.getFirst() and getLast() work as SequencedCollection")
    void dequeGetFirstLast() {
        ArrayDeque<String> deque = new ArrayDeque<>(List.of("head", "mid", "tail"));
        assertEquals("head", deque.getFirst());
        assertEquals("tail", deque.getLast());
    }

    @Test
    @DisplayName("ArrayDeque.reversed() iterates from tail to head")
    void dequeReversed() {
        ArrayDeque<Integer> deque = new ArrayDeque<>(List.of(1, 2, 3));
        List<Integer> rev = new ArrayList<>(deque.reversed());
        assertEquals(List.of(3, 2, 1), rev);
    }

    // ---------------------------------------------------------------------------
    // SequencedMap — LinkedHashMap
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("LinkedHashMap.firstEntry() returns the first inserted entry")
    void linkedHashMapFirstEntry() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("alpha", 1);
        map.put("beta", 2);
        map.put("gamma", 3);
        Map.Entry<String, Integer> first = map.firstEntry();
        assertNotNull(first);
        assertEquals("alpha", first.getKey());
        assertEquals(1, first.getValue());
    }

    @Test
    @DisplayName("LinkedHashMap.lastEntry() returns the last inserted entry")
    void linkedHashMapLastEntry() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("alpha", 1);
        map.put("beta", 2);
        map.put("gamma", 3);
        Map.Entry<String, Integer> last = map.lastEntry();
        assertNotNull(last);
        assertEquals("gamma", last.getKey());
        assertEquals(3, last.getValue());
    }

    @Test
    @DisplayName("LinkedHashMap.pollFirstEntry() removes and returns the first entry")
    void linkedHashMapPollFirstEntry() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 10);
        map.put("b", 20);
        Map.Entry<String, Integer> polled = map.pollFirstEntry();
        assertNotNull(polled);
        assertEquals("a", polled.getKey());
        assertEquals(1, map.size());
        assertFalse(map.containsKey("a"));
    }

    @Test
    @DisplayName("LinkedHashMap.pollLastEntry() removes and returns the last entry")
    void linkedHashMapPollLastEntry() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("x", 100);
        map.put("y", 200);
        Map.Entry<String, Integer> polled = map.pollLastEntry();
        assertNotNull(polled);
        assertEquals("y", polled.getKey());
        assertEquals(1, map.size());
    }

    @Test
    @DisplayName("LinkedHashMap.reversed() returns entries in reverse insertion order")
    void linkedHashMapReversed() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("first", 1);
        map.put("second", 2);
        map.put("third", 3);
        SequencedMap<String, Integer> rev = map.reversed();
        Map.Entry<String, Integer> revFirst = rev.firstEntry();
        assertNotNull(revFirst);
        assertEquals("third", revFirst.getKey(), "Reversed map's first entry should be 'third'");
    }

    // ---------------------------------------------------------------------------
    // getFirst() on empty collection throws NoSuchElementException
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("getFirst() on an empty sequenced collection throws NoSuchElementException")
    void getFirstOnEmptyThrows() {
        ArrayList<String> empty = new ArrayList<>();
        assertThrows(NoSuchElementException.class, empty::getFirst);
    }

    @Test
    @DisplayName("getLast() on an empty sequenced collection throws NoSuchElementException")
    void getLastOnEmptyThrows() {
        LinkedList<String> empty = new LinkedList<>();
        assertThrows(NoSuchElementException.class, empty::getLast);
    }
}
