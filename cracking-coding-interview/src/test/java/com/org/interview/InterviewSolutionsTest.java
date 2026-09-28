package com.org.interview;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Known-answer checks for a sample of the solutions. The module used to carry only an empty
 * {@code @SpringBootTest} whose application class had been deleted, so it tested nothing and
 * failed the build.
 */
class InterviewSolutionsTest {

    @ParameterizedTest
    @CsvSource({"abcdef,true", "hello,false", "'',true"})
    void uniqueCharacters_bothApproachesAgree(String s, boolean expected) {
        assertThat(Q1_UniqueCharacters.isUnique1(s)).isEqualTo(expected);
        assertThat(Q1_UniqueCharacters.isUnique2(s)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"listen,silent,true", "rat,car,false", "ab,abc,false"})
    void anagram_bothApproachesAgree(String s, String t, boolean expected) {
        assertThat(Q4_Anagram.isAnagram(s, t)).isEqualTo(expected);
        assertThat(Q4_Anagram.isAnagram1(s, t)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"waterbottle,erbottlewat,true", "waterbottle,bottlewater,true", "abc,acb,false", "ab,abc,false"})
    void stringRotation(String s1, String s2, boolean expected) {
        assertThat(Q8_StringRotation.isRotation(s1, s2)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"0,-1", "1,1", "2,1", "10,55", "20,6765"})  // 1-indexed, as in the book; n < 1 -> -1
    void fibonacci_allThreeImplementationsAgree(long n, long expected) {
        assertThat(Q34_Fibonacci.fibRecursive(n)).isEqualTo(expected);
        assertThat(Q34_Fibonacci.fibIterative(n)).isEqualTo(expected);
        assertThat(Q34_Fibonacci.fibMatrix(n)).isEqualTo(expected);
    }

    @Test
    void coinChange_recursiveAndDpAgree() {
        assertThat(Q40_CoinChange.makeChange(10, 0)).isEqualTo(4);
        assertThat(Q40_CoinChange.makeChangeDP(10)).isEqualTo(4);
        assertThat(Q40_CoinChange.makeChangeDP(100)).isEqualTo(242);
    }

    // --- Regression tests for solutions that returned wrong results, threw or hung, plus the
    // --- rotation direction that Q6's comment used to get backwards

    @Test
    void rotateMatrix_turnsClockwise() {
        int[][] a = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        Q6_RotateMatrix.rotate(a, 4);
        assertThat(a).isDeepEqualTo(new int[][]{{13, 9, 5, 1}, {14, 10, 6, 2}, {15, 11, 7, 3}, {16, 12, 8, 4}});
    }

    @Test
    void removeDuplicates_handlesNegativeAndLargeValues() {
        var buffered = Q9_RemoveDuplicates.buildList(1500, -3, 1500, 7, -3);
        Q9_RemoveDuplicates.removeDuplicates(buffered);
        assertThat(values(buffered)).containsExactly(1500, -3, 7);

        var noBuffer = Q9_RemoveDuplicates.buildList(1500, -3, 1500, 7, -3);
        Q9_RemoveDuplicates.removeDuplicatesNoBuffer(noBuffer);
        assertThat(values(noBuffer)).containsExactly(1500, -3, 7);
    }

    @Test
    void threeStacksFixed_rejectsOverflowInsteadOfOverwritingTheNextStack() {
        var stacks = new Q14_ThreeStacks.ThreeStacksFixed(2);
        stacks.push(1, 100);
        stacks.push(0, 1);
        stacks.push(0, 2);
        assertThatThrownBy(() -> stacks.push(0, 3)).isInstanceOf(IllegalStateException.class);
        assertThat(stacks.peek(1)).isEqualTo(100);
        assertThatThrownBy(() -> stacks.peek(2)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void setOfStacks_isEmptyOncePopAtHasEmptiedEverySubStack() {
        var stacks = new Q16_SetOfStacks.SetOfStacks(1, 3);
        stacks.push(1);
        stacks.push(2);
        assertThat(stacks.popAt(1)).isEqualTo(2);
        assertThat(stacks.popAt(0)).isEqualTo(1);
        assertThat(stacks.isEmpty()).isTrue();
    }

    @Test
    void queueWithStacks_backIsTheNewestElementAfterTheTransfer() {
        var queue = new Q18_QueueWithStacks.MyQueue(20);
        for (int i = 0; i < 10; i++) queue.push(i);
        assertThat(queue.front()).isZero(); // moves every element to the out-stack
        assertThat(queue.back()).isEqualTo(9);
        queue.pop();
        queue.push(10);
        assertThat(queue.front()).isEqualTo(1);
        assertThat(queue.back()).isEqualTo(10);
    }

    @Test
    void sortStack_leavesTheSmallestElementOnTop() {
        var stack = new Q19_SortStack.IntStack(10);
        for (int v : new int[]{5, 1, 9, 3, 7, 2, 8, 4, 6, 0}) stack.push(v);
        var sorted = Q19_SortStack.sortStack(stack, 10);
        List<Integer> popped = new ArrayList<>();
        while (!sorted.isEmpty()) popped.add(sorted.pop());
        assertThat(popped).containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    void listOfDepths_keepsEveryLevelOfAWideTreeIntact() {
        var root = Q23_ListOfDepths.createMinimalTree(IntStream.range(0, 31).toArray(), 0, 30);
        var levels = Q23_ListOfDepths.findLevelLists(root, 5);
        assertThat(keys(levels[3])).containsExactly(1, 5, 9, 13, 17, 21, 25, 29);
        assertThat(keys(levels[4])).containsExactly(0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30);
        assertThat(Q23_ListOfDepths.findLevelLists(null, 2)[0].head).isNull();
    }

    @Test
    void inorderSuccessor_visitsEveryKeyInSortedOrder() {
        var node = Q24_InorderSuccessor.createMinimalTree(IntStream.range(0, 10).toArray(), null, 0, 9);
        while (node.left != null) node = node.left;
        List<Integer> visited = new ArrayList<>();
        for (; node != null; node = Q24_InorderSuccessor.inorderSuccessor(node)) visited.add(node.key);
        assertThat(visited).containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        assertThatCode(() -> Q24_InorderSuccessor.main(new String[0])).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource({"19.25,10011.01", "3.75,11.11", "123.625,1111011.101", "5.375,101.011", "0.5,0.1",
            "19,10011.", "3.72,ERROR", "0.1,ERROR"})
    void binaryToString(String decimal, String expected) {
        assertThat(Q29_BinaryToString.printBinary(decimal)).isEqualTo(expected);
    }

    @Test
    void nextNumber_matchesABruteForceSearch() {
        for (int n = 1; n <= 5000; n++) {
            assertThat(Q30_NextNumber.getNext(n)).as("next after %d", n).isEqualTo(bruteForceNext(n));
            assertThat(Q30_NextNumber.getPrev(n)).as("previous before %d", n).isEqualTo(bruteForcePrev(n));
        }
        assertThat(Q30_NextNumber.getNext(948)).isEqualTo(952);
        assertThat(Q30_NextNumber.getPrev(948)).isEqualTo(946);
        assertThat(Q30_NextNumber.getPrev(0)).isEqualTo(-1); // used to loop forever
    }

    @Test
    void robotPaths_findPathRecordsEveryCellFromStartToEnd() {
        assertThat(Q35_RobotPaths.countPaths(3, 3)).isEqualTo(6);
        assertThat(Q35_RobotPaths.countPaths(4, 4)).isEqualTo(20);

        Q35_RobotPaths.grid = new boolean[][]{
                {true, true, true, true},
                {true, false, true, true},
                {true, true, true, false},
                {true, true, true, true}};
        int[][] route = new int[8][2];
        assertThat(Q35_RobotPaths.findPath(4, 4, route, 0)).isTrue();
        int cells = Q35_RobotPaths.pathLen;
        assertThat(cells).isEqualTo(7); // 4 + 4 - 1
        assertThat(route[cells - 1]).containsExactly(1, 1);
        assertThat(route[0]).containsExactly(4, 4);
        for (int i = cells - 1; i > 0; i--) { // walk from (1,1) to (4,4)
            int[] from = route[i], to = route[i - 1];
            assertThat(to[0] - from[0] + to[1] - from[1]).as("one step down or right").isEqualTo(1);
            assertThat(Q35_RobotPaths.grid[to[0] - 1][to[1] - 1]).as("open cell").isTrue();
        }
    }

    private static List<Integer> values(Q9_RemoveDuplicates.Node head) {
        List<Integer> values = new ArrayList<>();
        for (var n = head; n != null; n = n.next) values.add(n.data);
        return values;
    }

    private static List<Integer> keys(Q23_ListOfDepths.ListHead level) {
        List<Integer> keys = new ArrayList<>();
        for (var n = level.head; n != null; n = n.next) keys.add(n.treeNode.key);
        return keys;
    }

    private static int bruteForceNext(int n) {
        for (long k = n + 1L; k <= Integer.MAX_VALUE; k++) {
            if (Long.bitCount(k) == Integer.bitCount(n)) return (int) k;
        }
        return -1;
    }

    private static int bruteForcePrev(int n) {
        for (int k = n - 1; k > 0; k--) {
            if (Integer.bitCount(k) == Integer.bitCount(n)) return k;
        }
        return -1;
    }
}
