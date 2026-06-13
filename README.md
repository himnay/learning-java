# Learning Java — Java 8 to Java 26

A comprehensive, test-driven learning repository covering every major Java language and API feature from **Java 8 (2014)** through **Java 26 (2026)**. Each concept is expressed as a JUnit 5 test with meaningful assertions — no bare `System.out.println`.

---

## Project Structure

```
learning-java/
├── pom.xml                         ← Single-module Maven project (Java 26, --enable-preview)
└── src/
    ├── main/java/com/org/java8/    ← Shared model classes (Student, StudentDataBase, etc.)
    └── test/java/com/org/
        ├── java8/                  ← Java 8 features
        ├── java9/                  ← Java 9 features
        ├── java10/                 ← Java 10 features
        ├── java11/                 ← Java 11 features
        ├── java12/                 ← Java 12 features
        ├── java14/                 ← Java 14 features
        ├── java15/                 ← Java 15 features
        ├── java16/                 ← Java 16 features
        ├── java17/                 ← Java 17 features
        ├── java21/                 ← Java 21 (LTS) features
        ├── java22/                 ← Java 22 features
        ├── java23/                 ← Java 23 features
        ├── java24/                 ← Java 24 features
        ├── java25/                 ← Java 25 (LTS) features
        └── java26/                 ← Java 26 features
```

## Build & Run

```bash
# Run all tests
mvn test

# Run tests for a specific Java version
mvn test -Dtest="com.org.java21.*"

# Compile only
mvn compile
```

**Requirements:** JDK 26, Maven 3.8+

---

## Feature Coverage

---

### Java 8 (March 2014) — The Functional Revolution

| Test Class                                       | Features Covered                                                                                                                                                   |
|--------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `stream/StreamExampleTest`                       | `filter`, `map`, `flatMap`, `reduce`, `sorted`, `distinct`, `limit`, `skip`, `peek`, `allMatch`, `anyMatch`, `noneMatch`, `findFirst`, `findAny`, `collect(toMap)` |
| `stream/StreamCollectTest`                       | `joining`, `counting`, `mapping`, `minBy`, `maxBy`, `summingInt`, `averagingInt`, `groupingBy` (1/2/3-arg), `collectingAndThen`, `partitioningBy`                  |
| `stream/StreamFactoryTest`                       | `Stream.of`, `Stream.iterate`, `Stream.generate`                                                                                                                   |
| `stream/StreamNumericTest`                       | `IntStream`, `LongStream`, `DoubleStream`, `range`, `rangeClosed`, `sum`, `min`, `max`, `average`, `count`, `boxed`, `mapToInt/Long/Double/Obj`                    |
| `functionalInterface/FunctionsTest`              | `Function<T,R>`, `andThen`, `compose`, complex function chaining                                                                                                   |
| `functionalInterface/PredicatesTest`             | `Predicate<T>`, `and`, `or`, `negate`, student filtering                                                                                                           |
| `functionalInterface/PredicatesBiTest`           | `BiPredicate<T,U>`, `and`, `or`, `negate`                                                                                                                          |
| `functionalInterface/ConsumerBiTest`             | `BiConsumer<T,U>`, `andThen`                                                                                                                                       |
| `functionalInterface/SuppliersTest`              | `Supplier<T>`, deferred creation                                                                                                                                   |
| `functionalInterface/FunctionBiTest`             | `BiFunction<T,U,R>`                                                                                                                                                |
| `functionalInterface/FunctionBinaryOperatorTest` | `BinaryOperator<T>`, `maxBy`, `minBy`, `andThen`                                                                                                                   |
| `functionalInterface/FunctionUnaryOperatorTest`  | `UnaryOperator<T>`, `andThen`, `compose`, `identity`                                                                                                               |
| `optional/OptionalExampleTest`                   | `ofNullable`, `isPresent`, `isEmpty`, `get`, `orElse`, `orElseGet`, `orElseThrow`, `ifPresent`, `filter`, `map`, `flatMap`                                         |
| `lambda/LambdaRestrictionTest`                   | Effectively final capture, instance method calls on captured objects                                                                                               |
| `methodReference/MethodReferenceTest`            | Instance method ref on type, instance method ref on instance, static method ref, constructor ref                                                                   |
| `methodReference/StaticMethodReferenceTest`      | Static method ref, isPrime algorithm                                                                                                                               |
| `defaultInterface/DefaultInterfaceTest`          | Default methods, Comparator chaining, diamond problem resolution                                                                                                   |
| `dateTime/LocalDateTest`                         | `LocalDate.of/now/ofYearDay`, `plus/minus`, `with`, `TemporalAdjusters`, `isLeapYear`, `isAfter/isBefore`, `ChronoField/Unit`                                      |
| `dateTime/LocalTimeTest`                         | `LocalTime.of/now`, `getHour/Minute`, `plus/minus`, `with`                                                                                                         |
| `dateTime/LocalDateTimeTest`                     | `LocalDateTime.of/now`, `get`, `plusHours/Minutes/Weeks`                                                                                                           |
| `dateTime/DateConversionTest`                    | `Date` ↔ `LocalDate`, `LocalDate` ↔ `java.sql.Date`                                                                                                                |
| `dateTime/ParallelStreamsTest`                   | `parallel()`, `parallelStream()`, correctness vs sequential                                                                                                        |
| `misc/MapsTest`                                  | `putIfAbsent`, `computeIfPresent/Absent`, `getOrDefault`, conditional `remove`, `merge`                                                                            |
| `misc/StringTest`                                | `String.join`, `chars().distinct()`, `Pattern.asPredicate`, `splitAsStream`                                                                                        |
| `misc/MathTest`                                  | `Math.addExact`, `Math.toIntExact`, unsigned int arithmetic                                                                                                        |
| `misc/AnnotationsTest`                           | `@Repeatable`, `@Retention(RUNTIME)`, `getAnnotation`, `getAnnotationsByType`                                                                                      |
| `misc/FilesTest`                                 | `Files.walk`, `find`, `list`, `lines`, `newBufferedReader/Writer`, `readAllLines`, `write`                                                                         |
| `misc/ConcurrencyTest`                           | `ConcurrentHashMap.forEachValue`, `forEach`, `search`                                                                                                              |
| `misc/CheckedFunctionsTest`                      | Wrapping checked exceptions in `Function`, `Predicate`, `Consumer`                                                                                                 |
| `concurrent/AtomicTest`                          | `AtomicInteger.incrementAndGet`, `accumulateAndGet`, `updateAndGet`, `compareAndSet`                                                                               |
| `concurrent/LongAdderTest`                       | `LongAdder.increment`, `add`, `sumThenReset`                                                                                                                       |
| `concurrent/LongAccumulatorTest`                 | `LongAccumulator`, custom binary operator                                                                                                                          |
| `concurrent/LockTest`                            | `ReentrantLock`, `tryLock`, `ReadWriteLock`, `StampedLock` (read/write/optimistic/convert)                                                                         |
| `concurrent/SynchronizedTest`                    | `synchronized` method, `synchronized` block                                                                                                                        |
| `concurrent/SemaphoreTest`                       | `Semaphore(1)` for mutual exclusion, `Semaphore(5)` for rate-limiting                                                                                              |
| `concurrent/ThreadsTest`                         | `Thread`, `Runnable`, `CountDownLatch`                                                                                                                             |
| `concurrent/ExecutorsTest`                       | `newSingleThreadExecutor`, `Future.get`, `TimeoutException`, `invokeAll`, `invokeAny`, `ScheduledExecutorService`                                                  |
| `concurrent/CompletableFutureTest`               | `complete`, `thenAccept`, `supplyAsync`, `thenApply`, `thenCombine`, `exceptionally`                                                                               |
| `concurrent/ConcurrentHashMapTest`               | `forEach`, `search`, `searchValues`, `reduce`, `mappingCount`, `putIfAbsent`                                                                                       |

---

### Java 9 (September 2017) — Modules & Factory Methods

| Test Class                            | Features Covered                                                                                                          |
|---------------------------------------|---------------------------------------------------------------------------------------------------------------------------|
| `java9/Java9CollectionsTest`          | `List.of`, `Set.of`, `Map.of`, `Map.ofEntries`, `Map.entry`, `List.copyOf` — all unmodifiable                             |
| `java9/Java9StreamTest`               | `Stream.takeWhile`, `Stream.dropWhile`, `Stream.iterate(seed, pred, f)`, `Stream.ofNullable`                              |
| `java9/Java9OptionalAndInterfaceTest` | `Optional.ifPresentOrElse`, `Optional.or`, `Optional.stream`; private interface methods, private static interface methods |

**Key concepts:**
- Collection factory methods create compact, immutable collections; duplicates or nulls throw immediately
- `takeWhile`/`dropWhile` are lazy and ordered — they short-circuit on the first non-matching element
- `Optional.stream()` enables flat-mapping collections of optionals
- Private interface methods enable code sharing between default/static methods without exposing implementation

---

### Java 10 (March 2018) — `var` & Collection Copies

| Test Class                     | Features Covered                                                                                  |
|--------------------------------|---------------------------------------------------------------------------------------------------|
| `java10/Java10VarTest`         | `var` in local declarations, for-each, traditional for, stream pipelines, anonymous classes       |
| `java10/Java10CollectionsTest` | `List/Set/Map.copyOf`, `Collectors.toUnmodifiableList/Set/Map`, `Optional.orElseThrow()` (no-arg) |

**Key concepts:**
- `var` infers the **static type** at compile time — it is not dynamic typing; the compiler still enforces type safety
- `var` only works for local variables (not fields, method params, or return types)
- `copyOf` methods create a snapshot — mutations to the original are not reflected in the copy

---

### Java 11 (September 2018, LTS) — String & Files API

| Test Class                | Features Covered                                                                                                           |
|---------------------------|----------------------------------------------------------------------------------------------------------------------------|
| `java11/Java11StringTest` | `isBlank`, `strip`/`stripLeading`/`stripTrailing`, `lines()`, `repeat(n)`                                                  |
| `java11/Java11ApiTest`    | `Files.readString`, `Files.writeString`, `Path.of`, `Predicate.not`, `Optional.isEmpty`, `Collection.toArray(IntFunction)` |

**Key concepts:**
- `strip` handles Unicode whitespace (e.g., ` `) while `trim` only handles ASCII ≤ ` `
- `lines()` returns a `Stream<String>` — lazy and efficient for large files
- `Predicate.not(String::isBlank)` is a cleaner alternative to `s -> !s.isBlank()`
- `Files.readString`/`writeString` eliminate boilerplate for simple file operations

---

### Java 12 (March 2019) — Teeing & String Utilities

| Test Class                  | Features Covered                                                                                             |
|-----------------------------|--------------------------------------------------------------------------------------------------------------|
| `java12/Java12FeaturesTest` | `String.indent(n)`, `String.transform(fn)`, `Collectors.teeing`, `Files.mismatch`, switch expression preview |

**Key concepts:**
- `Collectors.teeing` processes a stream in two collectors simultaneously then merges results — ideal for computing two aggregates in one pass
- `String.transform` enables fluent pipeline chaining on strings
- `String.indent(n)` always ensures a trailing newline

---

### Java 14 (March 2020) — Switch Expressions & Records Preview

| Test Class                             | Features Covered                                                              |
|----------------------------------------|-------------------------------------------------------------------------------|
| `java14/SwitchExpressionsTest`         | Arrow switch `->`, `yield` in blocks, multi-label cases, switch as expression |
| `java14/PatternMatchingInstanceofTest` | `instanceof` pattern variable, negation with `&&`, combined conditions        |
| `java14/StringFormattedTest`           | `String.formatted(args)` as instance alternative to `String.format`           |

**Key concepts:**
- Switch expressions eliminate fall-through bugs; arrow branches are exhaustive and use `yield` for multi-statement results
- Pattern variables from `instanceof` are scoped to the branch where they are matched

---

### Java 15 (September 2020) — Text Blocks Standard

| Test Class              | Features Covered                                                                                                                                         |
|-------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------|
| `java15/TextBlocksTest` | `"""..."""` multiline strings, incidental whitespace stripping, `\` line continuation, `\s` trailing space marker, `stripIndent()`, `translateEscapes()` |

**Key concepts:**
- The compiler determines the common indentation of all non-empty lines and strips it — the closing `"""` position sets the minimum indentation
- `\` at end of line joins lines without a newline in the result; `\s` forces a trailing space to be preserved

---

### Java 16 (March 2021) — Records & Pattern Matching Standard

| Test Class                      | Features Covered                                                                                                                                   |
|---------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------|
| `java16/RecordsTest`            | Record declaration, auto-generated accessor/equals/hashCode/toString, compact constructor, canonical constructor override, implementing interfaces |
| `java16/StreamEnhancementsTest` | `Stream.toList()` (unmodifiable), `Stream.mapMulti()`                                                                                              |

**Key concepts:**
- Records are **transparent carriers** for immutable data; they cannot extend classes (only implement interfaces)
- The compact constructor validates but does not need to assign — assignment is done implicitly
- `Stream.toList()` is slightly more efficient than `collect(Collectors.toList())` and always returns an unmodifiable list

---

### Java 17 (September 2021, LTS) — Sealed Classes & Enhanced Random

| Test Class                   | Features Covered                                                                                                          |
|------------------------------|---------------------------------------------------------------------------------------------------------------------------|
| `java17/SealedClassesTest`   | `sealed interface`, `permits`, `final`/`non-sealed` subtypes, pattern matching switch with sealed types (exhaustive)      |
| `java17/RandomGeneratorTest` | `RandomGenerator` interface, `RandomGeneratorFactory`, `nextInt(bound)`, `nextDouble`, `ints()/longs()/doubles()` streams |

**Key concepts:**
- Sealed types restrict which classes can implement/extend a type — the compiler can verify exhaustiveness in switch expressions
- `RandomGenerator` is an interface; use `RandomGeneratorFactory.of("Xoshiro256PlusPlus")` to select algorithm; legacy `Random`/`ThreadLocalRandom`/`SecureRandom` implement it

---

### Java 21 (September 2023, LTS) — Virtual Threads & Pattern Matching Complete

| Test Class                         | Features Covered                                                                                                                  |
|------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------|
| `java21/VirtualThreadsTest`        | `Thread.ofVirtual().start()`, `Thread.ofVirtual().factory()`, `Executors.newVirtualThreadPerTaskExecutor()`, `Thread.isVirtual()` |
| `java21/SequencedCollectionsTest`  | `SequencedCollection.getFirst/getLast`, `addFirst/addLast`, `reversed()`, `SequencedMap.firstEntry/lastEntry/reversed`            |
| `java21/RecordPatternsTest`        | Deconstructing records in `instanceof`, nested record patterns, record patterns in switch                                         |
| `java21/PatternMatchingSwitchTest` | Type patterns in switch, guarded patterns (`when`), `null` in switch, exhaustiveness with sealed types                            |

**Key concepts:**
- Virtual threads are **lightweight JVM-managed threads** (not OS threads); you can create millions; blocking I/O automatically unmounts without pinning a platform thread
- Sequenced collections add a stable notion of first/last element to `List`, `Deque`, `LinkedHashSet`, `LinkedHashMap` etc.
- Record patterns allow destructuring in one step: `if (obj instanceof Point(int x, int y))` extracts both components

---

### Java 22 (March 2024) — Unnamed Variables & Foreign Functions

| Test Class                    | Features Covered                                                         |
|-------------------------------|--------------------------------------------------------------------------|
| `java22/UnnamedVariablesTest` | `_` in catch, enhanced-for, try-with-resources, lambda, pattern matching |

**Key concepts:**
- `_` signals intentional non-use of a variable — the compiler enforces it cannot be read; improves clarity and eliminates "unused variable" warnings

---

### Java 23 (September 2024) — Scoped Values & Structured Concurrency

| Test Class                         | Features Covered                                                                                            |
|------------------------------------|-------------------------------------------------------------------------------------------------------------|
| `java23/ScopedValuesTest`          | `ScopedValue.newInstance()`, `ScopedValue.where(...).run(...)`, nested scopes, inheritance by child threads |
| `java23/StructuredConcurrencyTest` | `StructuredTaskScope.ShutdownOnFailure`, `fork`, `join`, `throwIfFailed`, `ShutdownOnSuccess`               |

**Key concepts:**
- `ScopedValue` is the modern, safe replacement for `ThreadLocal`: values are bound for a specific scope and are automatically unbound; child threads inherit values; no memory leak risk
- `StructuredTaskScope` ensures all forked subtasks complete (or are cancelled) before the scope closes — structured concurrency makes concurrent code read like sequential code

---

### Java 24 (March 2025) — Stream Gatherers Standard

| Test Class                   | Features Covered                                                                                                         |
|------------------------------|--------------------------------------------------------------------------------------------------------------------------|
| `java24/StreamGatherersTest` | `Stream.gather()`, built-in `Gatherers.windowFixed`, `windowSliding`, `scan`, `fold`, `mapConcurrent`; custom `Gatherer` |

**Key concepts:**
- Stream gatherers are a flexible intermediate operation beyond what `filter`/`map`/`flatMap` support: sliding windows, running totals, stateful transformations
- `Gatherer` has four parts: initializer (state), integrator (process element), combiner (parallel merge), finisher (emit remaining)

---

### Java 25 (September 2025, LTS) — Stable Values & Finalized APIs

| Test Class                  | Features Covered                                                                                                                                       |
|-----------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------|
| `java25/Java25FeaturesTest` | Primitive types in patterns (`instanceof int i`, `switch` on primitive wrappers with type pattern), finalized structured concurrency and scoped values |

**Key concepts:**
- Primitive type patterns allow matching and binding on unboxed primitives — no NullPointerException risk since primitives cannot be null
- Java 25 is an LTS release: virtual threads, records, sealed classes, pattern matching, text blocks, structured concurrency, scoped values are all production-stable

---

### Java 26 (March 2026) — Module Imports & Further Refinements

| Test Class                  | Features Covered                                                                                        |
|-----------------------------|---------------------------------------------------------------------------------------------------------|
| `java26/Java26FeaturesTest` | Module import declarations (`import module java.base`), flexible constructor bodies, latest refinements |

**Key concepts:**
- Module imports (`import module M`) bulk-import all exported packages of a module — useful for learning/scripting scenarios
- Flexible constructor bodies allow statements before `super()`/`this()` calls as long as they don't reference the instance being initialized

---

## Java Version Quick Reference

| Version     | Release  | Type    | Key Features                                                                                                         |
|-------------|----------|---------|----------------------------------------------------------------------------------------------------------------------|
| **Java 8**  | Mar 2014 | LTS     | Lambdas, streams, Optional, default methods, date/time API, CompletableFuture                                        |
| **Java 9**  | Sep 2017 |         | Modules (JPMS), collection factory methods, Stream enhancements, private interface methods                           |
| **Java 10** | Mar 2018 |         | `var` (local variable type inference), `copyOf`, `toUnmodifiableList/Set/Map`                                        |
| **Java 11** | Sep 2018 | **LTS** | String methods (`strip`, `isBlank`, `lines`, `repeat`), `Files.readString/writeString`, `Predicate.not`, HTTP Client |
| **Java 12** | Mar 2019 |         | `Collectors.teeing`, `String.indent/transform`, `Files.mismatch`, switch expressions (preview)                       |
| **Java 13** | Sep 2019 |         | Text blocks (preview), switch expressions (preview 2)                                                                |
| **Java 14** | Mar 2020 |         | Switch expressions (standard), `instanceof` pattern matching (preview), Records (preview), `String.formatted`        |
| **Java 15** | Sep 2020 |         | Text blocks (standard), Sealed classes (preview), `String.stripIndent/translateEscapes`                              |
| **Java 16** | Mar 2021 |         | Records (standard), `instanceof` pattern matching (standard), `Stream.toList()`, `Stream.mapMulti()`                 |
| **Java 17** | Sep 2021 | **LTS** | Sealed classes (standard), Pattern matching for switch (preview), Enhanced Random generators                         |
| **Java 18** | Mar 2022 |         | UTF-8 by default, Simple web server (`jwebserver`), Code snippets in Javadoc                                         |
| **Java 19** | Sep 2022 |         | Virtual threads (preview), Structured concurrency (incubator), Record patterns (preview)                             |
| **Java 20** | Mar 2023 |         | Scoped values (incubator), Virtual threads (preview 2), Record patterns (preview 2)                                  |
| **Java 21** | Sep 2023 | **LTS** | Virtual threads (standard), Sequenced collections, Record patterns (standard), Pattern matching switch (standard)    |
| **Java 22** | Mar 2024 |         | Unnamed variables `_` (standard), Stream gatherers (preview), Foreign Function & Memory API (standard)               |
| **Java 23** | Sep 2024 |         | Structured concurrency (preview), Scoped values (preview), Primitive types in patterns (preview), Markdown Javadoc   |
| **Java 24** | Mar 2025 |         | Stream gatherers (standard), Class-File API (standard), Scoped values & Structured concurrency (preview 4)           |
| **Java 25** | Sep 2025 | **LTS** | Primitive types in patterns (standard), Structured concurrency (standard), Scoped values (standard), Stable values   |
| **Java 26** | Mar 2026 |         | Module import declarations, Flexible constructor bodies, further refinements                                         |

---

## Design Decisions

- **Tests as documentation**: every concept lives in a `@Test` method with a `@DisplayName` explaining the rule being demonstrated
- **Assertions over println**: every test asserts a concrete outcome — the test suite is the specification
- **Nested types**: Records, sealed classes, and helper interfaces are defined as `static` nested types inside test classes to keep related code co-located
- **No mocks**: tests use real JDK APIs; for I/O tests, JUnit 5's `@TempDir` provides isolated temporary directories
- **Preview features**: the project compiles with `--enable-preview` to cover features in their preview phase alongside finalized ones
