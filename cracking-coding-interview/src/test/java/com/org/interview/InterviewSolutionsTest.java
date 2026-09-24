package com.org.interview;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

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
}
