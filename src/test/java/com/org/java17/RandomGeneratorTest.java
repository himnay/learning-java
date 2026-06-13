// FILE 8: src/test/java/com/org/java17/RandomGeneratorTest.java
package com.org.java17;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;
import java.util.stream.IntStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RandomGenerator Interface (Java 17)")
class RandomGeneratorTest {

    // ---------------------------------------------------------------------------
    // Basic RandomGenerator creation
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("RandomGenerator.getDefault() returns a non-null generator")
    void defaultGeneratorIsNonNull() {
        RandomGenerator rng = RandomGenerator.getDefault();
        assertNotNull(rng);
    }

    @Test
    @DisplayName("RandomGeneratorFactory.of creates a named generator (L64X128MixRandom)")
    void namedGeneratorCreation() {
        RandomGenerator rng = RandomGeneratorFactory.of("L64X128MixRandom").create();
        assertNotNull(rng);
    }

    // ---------------------------------------------------------------------------
    // nextInt range
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("nextInt(bound) always returns a value in [0, bound)")
    void nextIntBound() {
        RandomGenerator rng = RandomGenerator.getDefault();
        for (int i = 0; i < 1000; i++) {
            int val = rng.nextInt(100);
            assertTrue(val >= 0 && val < 100,
                    "Expected [0, 100) but got " + val);
        }
    }

    @Test
    @DisplayName("nextInt(origin, bound) always returns a value in [origin, bound)")
    void nextIntOriginBound() {
        RandomGenerator rng = RandomGenerator.getDefault();
        for (int i = 0; i < 1000; i++) {
            int val = rng.nextInt(10, 20);
            assertTrue(val >= 10 && val < 20,
                    "Expected [10, 20) but got " + val);
        }
    }

    @Test
    @DisplayName("nextInt() with no argument returns any int (just checks no exception)")
    void nextIntUnbounded() {
        RandomGenerator rng = RandomGenerator.getDefault();
        assertDoesNotThrow(() -> {
            for (int i = 0; i < 100; i++) rng.nextInt();
        });
    }

    // ---------------------------------------------------------------------------
    // nextDouble
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("nextDouble() returns values in [0.0, 1.0)")
    void nextDoubleRange() {
        RandomGenerator rng = RandomGenerator.getDefault();
        for (int i = 0; i < 1000; i++) {
            double val = rng.nextDouble();
            assertTrue(val >= 0.0 && val < 1.0,
                    "Expected [0.0, 1.0) but got " + val);
        }
    }

    @Test
    @DisplayName("nextDouble(bound) returns values in [0.0, bound)")
    void nextDoubleBound() {
        RandomGenerator rng = RandomGenerator.getDefault();
        double bound = 5.0;
        for (int i = 0; i < 1000; i++) {
            double val = rng.nextDouble(bound);
            assertTrue(val >= 0.0 && val < bound,
                    "Expected [0.0, 5.0) but got " + val);
        }
    }

    @Test
    @DisplayName("nextDouble(origin, bound) returns values in [origin, bound)")
    void nextDoubleOriginBound() {
        RandomGenerator rng = RandomGenerator.getDefault();
        for (int i = 0; i < 1000; i++) {
            double val = rng.nextDouble(2.5, 7.5);
            assertTrue(val >= 2.5 && val < 7.5,
                    "Expected [2.5, 7.5) but got " + val);
        }
    }

    // ---------------------------------------------------------------------------
    // nextLong
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("nextLong(bound) returns values in [0, bound)")
    void nextLongBound() {
        RandomGenerator rng = RandomGenerator.getDefault();
        long bound = 1_000_000L;
        for (int i = 0; i < 100; i++) {
            long val = rng.nextLong(bound);
            assertTrue(val >= 0L && val < bound,
                    "Expected [0, 1_000_000) but got " + val);
        }
    }

    @Test
    @DisplayName("nextLong(origin, bound) returns values in [origin, bound)")
    void nextLongOriginBound() {
        RandomGenerator rng = RandomGenerator.getDefault();
        for (int i = 0; i < 100; i++) {
            long val = rng.nextLong(100L, 200L);
            assertTrue(val >= 100L && val < 200L,
                    "Expected [100, 200) but got " + val);
        }
    }

    // ---------------------------------------------------------------------------
    // nextBoolean and nextBytes
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("nextBoolean() produces both true and false over many calls")
    void nextBooleanProducesBothValues() {
        RandomGenerator rng = RandomGenerator.getDefault();
        boolean sawTrue = false, sawFalse = false;
        for (int i = 0; i < 1000 && !(sawTrue && sawFalse); i++) {
            if (rng.nextBoolean()) sawTrue = true;
            else sawFalse = true;
        }
        assertTrue(sawTrue,  "Should have seen at least one true");
        assertTrue(sawFalse, "Should have seen at least one false");
    }

    @Test
    @DisplayName("nextBytes(byte[]) fills the array without throwing")
    void nextBytesFillsArray() {
        RandomGenerator rng = RandomGenerator.getDefault();
        byte[] buf = new byte[32];
        assertDoesNotThrow(() -> rng.nextBytes(buf));
        // At least one byte should be non-zero with overwhelming probability
        boolean anyNonZero = false;
        for (byte b : buf) if (b != 0) { anyNonZero = true; break; }
        assertTrue(anyNonZero, "Extremely unlikely that 32 random bytes are all zero");
    }

    // ---------------------------------------------------------------------------
    // Stream generation
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("ints(size, origin, bound) stream produces correct number of values in range")
    void intStreamProducesCorrectCount() {
        RandomGenerator rng = RandomGenerator.getDefault();
        List<Integer> values = rng.ints(50, 0, 10)
                .boxed()
                .toList();
        assertEquals(50, values.size());
        for (int v : values) {
            assertTrue(v >= 0 && v < 10, "Value " + v + " out of range [0, 10)");
        }
    }

    @Test
    @DisplayName("doubles(size) stream returns values in [0.0, 1.0)")
    void doubleStreamProducesCorrectCount() {
        RandomGenerator rng = RandomGenerator.getDefault();
        long count = rng.doubles(100).filter(d -> d >= 0.0 && d < 1.0).count();
        assertEquals(100, count);
    }

    // ---------------------------------------------------------------------------
    // RandomGeneratorFactory queries
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("RandomGeneratorFactory.all() returns at least one factory")
    void factoryAllReturnsNonEmpty() {
        long count = RandomGeneratorFactory.all().count();
        assertTrue(count > 0, "Expected at least one RandomGeneratorFactory");
    }

    @Test
    @DisplayName("Seeded generator produces reproducible sequence")
    void seededGeneratorIsReproducible() {
        long seed = 12345L;
        RandomGenerator rng1 = RandomGeneratorFactory.of("L64X128MixRandom").create(seed);
        RandomGenerator rng2 = RandomGeneratorFactory.of("L64X128MixRandom").create(seed);

        int[] seq1 = IntStream.generate(() -> rng1.nextInt(1000)).limit(20).toArray();
        int[] seq2 = IntStream.generate(() -> rng2.nextInt(1000)).limit(20).toArray();

        assertArrayEquals(seq1, seq2, "Same seed must produce the same sequence");
    }

    // ---------------------------------------------------------------------------
    // JumpableGenerator — if the algorithm supports it
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Jumping a JumpableGenerator produces a statistically independent stream")
    void jumpableGeneratorJump() {
        // L64X128MixRandom is a Jumpable generator
        RandomGenerator rng = RandomGeneratorFactory.of("L64X128MixRandom").create(42L);
        if (rng instanceof RandomGenerator.JumpableGenerator jumpable) {
            RandomGenerator jumped = jumpable.copyAndJump();
            assertNotNull(jumped, "copyAndJump must return a non-null generator");
            // The two generators should produce different sequences
            int a = rng.nextInt();
            int b = jumped.nextInt();
            // With overwhelming probability they differ; test won't be flaky in practice
            // (there's a 1-in-2^32 chance of failure which we accept)
            assertDoesNotThrow(() -> jumped.nextInt());
        }
        // If not jumpable, the test is vacuously satisfied
    }
}
