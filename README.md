# <span style="color:hsl(224,80%,58%)">Learning Java — Java 8 to Java 26</span>

<img src="image/openjdk-logo.png" alt="OpenJDK" width="240"/>

## <span style="color:hsl(2,80%,58%)">Table of contents</span>

1. 🏗️ [Project Structure](#project-structure)
2. 🔨 [Build & Run](#build--run)
3. 🧪 [Feature Coverage](#feature-coverage)
4. 📚 [Java Version Quick Reference](#java-version-quick-reference)
5. 🏗️ [Design Decisions](#design-decisions)

A comprehensive, test-driven learning repository covering every major Java language and API feature from **Java 8 (2014)** through **Java 26 (2026)**. Each concept is expressed as a JUnit 6 (Jupiter) test with meaningful assertions — no bare [`System.out.println`][System].

---

<a id="project-structure"></a>
## <span style="color:hsl(139,80%,58%)">1. 🏗️ Project Structure</span>

```
learning-java/
├── pom.xml                         ← Aggregator: Java 27, --enable-preview, JUnit 6 (as of 2026)
├── core/                           ← Java 8 → 26 feature tests
│   ├── src/main/java/com/org/java/ ← Shared model classes (Student, StudentDataBase, etc.)
│   └── src/test/java/com/org/
│       ├── java/                   ← Java 8 features
│       ├── java9/ … java17/        ← one package per release (9–12, 14–17)
│       ├── java21/                 ← Java 21 (LTS) features
│       ├── java22/ … java24/
│       ├── java25/                 ← Java 25 (LTS) features
│       └── java26/                 ← Java 26 features
├── cracking-coding-interview/      ← 47 standalone solutions (Q1…Q47, each with a main) + known-answer tests
└── jpms/                           ← JPMS multi-module example (api / service / app) — see jpms/README.md
```

<a id="build--run"></a>
## <span style="color:hsl(277,80%,58%)">2. 🔨 Build & Run</span>

```bash
# Run all tests (all modules)
mvn verify

# Run tests for a specific Java version
mvn test -pl core -Dtest="com.org.java21.*"

# Compile only
mvn compile
```

**Requirements:** JDK 27 (preview features are compiled for exactly 27, so an older or newer JDK won't run them), Maven 3.9+.
The build moved from JDK 26 to 27 in September 2026, with the preview APIs updated to their JDK 27 form (for example
structured concurrency's `allUntil` and `ExecutionException`). JDK 27's new features are not covered by dedicated tests yet.

---

<a id="feature-coverage"></a>
## <span style="color:hsl(54,80%,50%)">3. 🧪 Feature Coverage</span>

---

### Java 8 (March 2014) — The Functional Revolution

| Test Class                                       | Features Covered                                                                                                                                                                           |
|--------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `stream/StreamExampleTest`                       | `filter`, `map`, `flatMap`, `reduce`, `sorted`, `distinct`, `limit`, `skip`, `peek`, `allMatch`, `anyMatch`, `noneMatch`, `findFirst`, `findAny`, `collect(toMap)`                         |
| `stream/StreamCollectTest`                       | `joining`, `counting`, `mapping`, `minBy`, `maxBy`, `summingInt`, `averagingInt`, `groupingBy` (1/2/3-arg), `collectingAndThen`, `partitioningBy`                                          |
| `stream/StreamFactoryTest`                       | [`Stream.of`][Stream], `Stream.iterate`, `Stream.generate`                                                                                                                                 |
| `stream/StreamNumericTest`                       | [`IntStream`][IntStream], [`LongStream`][LongStream], [`DoubleStream`][DoubleStream], `range`, `rangeClosed`, `sum`, `min`, `max`, `average`, `count`, `boxed`, `mapToInt/Long/Double/Obj` |
| `functionalInterface/FunctionsTest`              | [`Function<T,R>`][Function], `andThen`, `compose`, complex function chaining                                                                                                               |
| `functionalInterface/PredicatesTest`             | [`Predicate<T>`][Predicate], `and`, `or`, `negate`, student filtering                                                                                                                      |
| `functionalInterface/PredicatesBiTest`           | [`BiPredicate<T,U>`][BiPredicate], `and`, `or`, `negate`                                                                                                                                   |
| `functionalInterface/ConsumerBiTest`             | [`BiConsumer<T,U>`][BiConsumer], `andThen`                                                                                                                                                 |
| `functionalInterface/SuppliersTest`              | [`Supplier<T>`][Supplier], deferred creation                                                                                                                                               |
| `functionalInterface/FunctionBiTest`             | [`BiFunction<T,U,R>`][BiFunction]                                                                                                                                                          |
| `functionalInterface/FunctionBinaryOperatorTest` | [`BinaryOperator<T>`][BinaryOperator], `maxBy`, `minBy`, `andThen`                                                                                                                         |
| `functionalInterface/FunctionUnaryOperatorTest`  | [`UnaryOperator<T>`][UnaryOperator], `andThen`, `compose`, `identity`                                                                                                                      |
| `optional/OptionalExampleTest`                   | `ofNullable`, `isPresent`, `isEmpty`, `get`, `orElse`, `orElseGet`, `orElseThrow`, `ifPresent`, `filter`, `map`, `flatMap`                                                                 |
| `lambda/LambdaRestrictionTest`                   | Effectively final capture, instance method calls on captured objects                                                                                                                       |
| `methodReference/MethodReferenceTest`            | Instance method ref on type, instance method ref on instance, static method ref, constructor ref                                                                                           |
| `methodReference/StaticMethodReferenceTest`      | Static method ref, isPrime algorithm                                                                                                                                                       |
| `defaultInterface/DefaultInterfaceTest`          | Default methods, Comparator chaining, diamond problem resolution                                                                                                                           |
| `dateTime/LocalDateTest`                         | [`LocalDate.of/now/ofYearDay`][LocalDate], `plus/minus`, `with`, [`TemporalAdjusters`][TemporalAdjusters], `isLeapYear`, `isAfter/isBefore`, `ChronoField/Unit`                            |
| `dateTime/LocalTimeTest`                         | [`LocalTime.of/now`][LocalTime], `getHour/Minute`, `plus/minus`, `with`                                                                                                                    |
| `dateTime/LocalDateTimeTest`                     | [`LocalDateTime.of/now`][LocalDateTime], `get`, `plusHours/Minutes/Weeks`                                                                                                                  |
| `dateTime/DateConversionTest`                    | [`Date`][Date] ↔ `LocalDate`, `LocalDate` ↔ [`java.sql.Date`][Date (java.sql)]                                                                                                             |
| `dateTime/ParallelStreamsTest`                   | `parallel()`, `parallelStream()`, correctness vs sequential                                                                                                                                |
| `misc/MapsTest`                                  | `putIfAbsent`, `computeIfPresent/Absent`, `getOrDefault`, conditional `remove`, `merge`                                                                                                    |
| `misc/StringTest`                                | [`String.join`][String], `chars().distinct()`, [`Pattern.asPredicate`][Pattern], `splitAsStream`                                                                                           |
| `misc/MathTest`                                  | [`Math.addExact`][Math], `Math.toIntExact`, unsigned int arithmetic                                                                                                                        |
| `misc/AnnotationsTest`                           | [`@Repeatable`][Repeatable], [`@Retention(RUNTIME)`][Retention], `getAnnotation`, `getAnnotationsByType`                                                                                   |
| `misc/FilesTest`                                 | [`Files.walk`][Files], `find`, `list`, `lines`, `newBufferedReader/Writer`, `readAllLines`, `write`                                                                                        |
| `misc/ConcurrencyTest`                           | [`ConcurrentHashMap.forEachValue`][ConcurrentHashMap], `forEach`, `search`                                                                                                                 |
| `misc/CheckedFunctionsTest`                      | Wrapping checked exceptions in `Function`, `Predicate`, [`Consumer`][Consumer]                                                                                                             |
| `concurrent/AtomicTest`                          | [`AtomicInteger.incrementAndGet`][AtomicInteger], `accumulateAndGet`, `updateAndGet`, `compareAndSet`                                                                                      |
| `concurrent/LongAdderTest`                       | [`LongAdder.increment`][LongAdder], `add`, `sumThenReset`                                                                                                                                  |
| `concurrent/LongAccumulatorTest`                 | [`LongAccumulator`][LongAccumulator], custom binary operator                                                                                                                               |
| `concurrent/LockTest`                            | [`ReentrantLock`][ReentrantLock], `tryLock`, [`ReadWriteLock`][ReadWriteLock], [`StampedLock`][StampedLock] (read/write/optimistic/convert)                                                |
| `concurrent/SynchronizedTest`                    | `synchronized` method, `synchronized` block                                                                                                                                                |
| `concurrent/SemaphoreTest`                       | [`Semaphore(1)`][Semaphore] for mutual exclusion, `Semaphore(5)` for rate-limiting                                                                                                         |
| `concurrent/ThreadsTest`                         | [`Thread`][Thread], [`Runnable`][Runnable], [`CountDownLatch`][CountDownLatch]                                                                                                             |
| `concurrent/ExecutorsTest`                       | `newSingleThreadExecutor`, [`Future.get`][Future], [`TimeoutException`][TimeoutException], `invokeAll`, `invokeAny`, [`ScheduledExecutorService`][ScheduledExecutorService]                |
| `concurrent/CompletableFutureTest`               | `complete`, `thenAccept`, `supplyAsync`, `thenApply`, `thenCombine`, `exceptionally`                                                                                                       |
| `concurrent/ConcurrentHashMapTest`               | `forEach`, `search`, `searchValues`, `reduce`, `mappingCount`, `putIfAbsent`                                                                                                               |

---

### Java 9 (September 2017) — Modules & Factory Methods

| Test Class                            | Features Covered                                                                                                                      |
|---------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------|
| `java9/Java9CollectionsTest`          | [`List.of`][List], [`Set.of`][Set], [`Map.of`][Map], `Map.ofEntries`, `Map.entry`, `List.copyOf` — all unmodifiable                   |
| `java9/Java9StreamTest`               | [`Stream.takeWhile`][Stream], `Stream.dropWhile`, `Stream.iterate(seed, pred, f)`, `Stream.ofNullable`                                |
| `java9/Java9OptionalAndInterfaceTest` | [`Optional.ifPresentOrElse`][Optional], `Optional.or`, `Optional.stream`; private interface methods, private static interface methods |

**Key concepts:**

<ul>

- Collection factory methods create compact, immutable collections; duplicates or nulls throw immediately
- `takeWhile`/`dropWhile` are lazy and ordered — they short-circuit on the first non-matching element
- `Optional.stream()` enables flat-mapping collections of optionals
- Private interface methods enable code sharing between default/static methods without exposing implementation

</ul>

---

### Java 10 (March 2018) — `var` & Collection Copies

| Test Class                     | Features Covered                                                                                                            |
|--------------------------------|-----------------------------------------------------------------------------------------------------------------------------|
| `java10/Java10VarTest`         | `var` in local declarations, for-each, traditional for, stream pipelines, anonymous classes                                 |
| `java10/Java10CollectionsTest` | `List/Set/Map.copyOf`, [`Collectors.toUnmodifiableList/Set/Map`][Collectors], [`Optional.orElseThrow()`][Optional] (no-arg) |

**Key concepts:**

<ul>

- `var` infers the **static type** at compile time — it is not dynamic typing; the compiler still enforces type safety
- `var` only works for local variables (not fields, method params, or return types)
- `copyOf` methods create a snapshot — mutations to the original are not reflected in the copy

</ul>

---

### Java 11 (September 2018, LTS) — String & Files API

| Test Class                | Features Covered                                                                                                                                                                   |
|---------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `java11/Java11StringTest` | `isBlank`, `strip`/`stripLeading`/`stripTrailing`, `lines()`, `repeat(n)`                                                                                                          |
| `java11/Java11ApiTest`    | [`Files.readString`][Files], `Files.writeString`, [`Path.of`][Path], [`Predicate.not`][Predicate], [`Optional.isEmpty`][Optional], [`Collection.toArray(IntFunction)`][Collection] |

**Key concepts:**

<ul>

- `strip` removes Unicode whitespace as defined by [`Character.isWhitespace`][Character] (e.g., the em space `\u2003`), while `trim` only removes characters ≤ U+0020. Neither removes the no-break space `\u00A0`, which Java does not count as whitespace
- `lines()` returns a [`Stream<String>`][Stream] — lazy and efficient for large files
- `Predicate.not(String::isBlank)` is a cleaner alternative to `s -> !s.isBlank()`
- `Files.readString`/`writeString` eliminate boilerplate for simple file operations

</ul>

---

### Java 12 (March 2019) — Teeing & String Utilities

| Test Class                  | Features Covered                                                                                                                              |
|-----------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------|
| `java12/Java12FeaturesTest` | [`String.indent(n)`][String], `String.transform(fn)`, [`Collectors.teeing`][Collectors], [`Files.mismatch`][Files], switch expression preview |

**Key concepts:**

<ul>

- `Collectors.teeing` processes a stream in two collectors simultaneously then merges results — ideal for computing two aggregates in one pass
- `String.transform` enables fluent pipeline chaining on strings
- `String.indent(n)` always ensures a trailing newline

</ul>

---

### Java 14 (March 2020) — Switch Expressions & Records Preview

| Test Class                             | Features Covered                                                              |
|----------------------------------------|-------------------------------------------------------------------------------|
| `java14/SwitchExpressionsTest`         | Arrow switch `->`, `yield` in blocks, multi-label cases, switch as expression |
| `java14/PatternMatchingInstanceofTest` | `instanceof` pattern variable, negation with `&&`, combined conditions        |
| `java14/StringFormattedTest`           | [`String.formatted(args)`][String] as instance alternative to `String.format` |

**Key concepts:**

<ul>

- Switch expressions eliminate fall-through bugs; arrow branches are exhaustive and use `yield` for multi-statement results
- Pattern variables from `instanceof` are scoped to the branch where they are matched

</ul>

---

### Java 15 (September 2020) — Text Blocks Standard

| Test Class              | Features Covered                                                                                                                                         |
|-------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------|
| `java15/TextBlocksTest` | `"""..."""` multiline strings, incidental whitespace stripping, `\` line continuation, `\s` trailing space marker, `stripIndent()`, `translateEscapes()` |

**Key concepts:**

<ul>

- The compiler determines the common indentation of all non-empty lines and strips it — the closing `"""` position sets the minimum indentation
- `\` at end of line joins lines without a newline in the result; `\s` forces a trailing space to be preserved

</ul>

---

### Java 16 (March 2021) — Records & Pattern Matching Standard

| Test Class                      | Features Covered                                                                                                                                   |
|---------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------|
| `java16/RecordsTest`            | Record declaration, auto-generated accessor/equals/hashCode/toString, compact constructor, canonical constructor override, implementing interfaces |
| `java16/StreamEnhancementsTest` | [`Stream.toList()`][Stream] (unmodifiable), `Stream.mapMulti()`                                                                                    |

**Key concepts:**

<ul>

- Records are **transparent carriers** for immutable data; they cannot extend classes (only implement interfaces)
- The compact constructor validates but does not need to assign — assignment is done implicitly
- `Stream.toList()` is slightly more efficient than `collect(Collectors.toList())` and always returns an unmodifiable list

</ul>

---

### Java 17 (September 2021, LTS) — Sealed Classes & Enhanced Random

| Test Class                   | Features Covered                                                                                                                                                                                     |
|------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `java17/SealedClassesTest`   | `sealed interface`, `permits`, `final`/`non-sealed` subtypes, pattern matching switch with sealed types (exhaustive)                                                                                 |
| `java17/RandomGeneratorTest` | [`RandomGenerator`][RandomGenerator] interface, [`RandomGeneratorFactory`][RandomGeneratorFactory], `nextInt(bound)`, `nextDouble`, `ints()/longs()/doubles()` streams, jumping with `copyAndJump()` |

**Key concepts:**

<ul>

- Sealed types restrict which classes can implement/extend a type — the compiler can verify exhaustiveness in switch expressions
- `RandomGenerator` is an interface; use `RandomGeneratorFactory.of("Xoshiro256PlusPlus")` to select algorithm; legacy [`Random`][Random]/[`ThreadLocalRandom`][ThreadLocalRandom]/[`SecureRandom`][SecureRandom] implement it

</ul>

---

### Java 21 (September 2023, LTS) — Virtual Threads & Pattern Matching Complete

| Test Class                         | Features Covered                                                                                                                                              |
|------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `java21/VirtualThreadsTest`        | [`Thread.ofVirtual().start()`][Thread], `Thread.ofVirtual().factory()`, [`Executors.newVirtualThreadPerTaskExecutor()`][Executors], `Thread.isVirtual()`      |
| `java21/SequencedCollectionsTest`  | [`SequencedCollection.getFirst/getLast`][SequencedCollection], `addFirst/addLast`, `reversed()`, [`SequencedMap.firstEntry/lastEntry/reversed`][SequencedMap] |
| `java21/RecordPatternsTest`        | Deconstructing records in `instanceof`, nested record patterns, record patterns in switch                                                                     |
| `java21/PatternMatchingSwitchTest` | Type patterns in switch, guarded patterns (`when`), `null` in switch, exhaustiveness with sealed types                                                        |

**Key concepts:**

<ul>

- Virtual threads are **lightweight JVM-managed threads** (not OS threads); you can create millions; blocking I/O automatically unmounts without pinning a platform thread
- Sequenced collections add a stable notion of first/last element to [`List`][List], [`Deque`][Deque], [`LinkedHashSet`][LinkedHashSet], [`LinkedHashMap`][LinkedHashMap] etc.
- Record patterns allow destructuring in one step: `if (obj instanceof Point(int x, int y))` extracts both components

</ul>

---

### Java 22 (March 2024) — Unnamed Variables & Foreign Functions

| Test Class                    | Features Covered                                                         |
|-------------------------------|--------------------------------------------------------------------------|
| `java22/UnnamedVariablesTest` | `_` in catch, enhanced-for, try-with-resources, lambda, pattern matching |

**Key concepts:**

<ul>

- `_` signals intentional non-use of a variable — the compiler enforces it cannot be read; improves clarity and eliminates "unused variable" warnings

</ul>

---

### Java 23 (September 2024) — Scoped Values & Structured Concurrency

| Test Class                         | Features Covered                                                                                                                                                                                                  |
|------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `java23/ScopedValuesTest`          | [`ScopedValue.newInstance()`][ScopedValue], `ScopedValue.where(...).run(...)`, nested scopes, inheritance by structured-concurrency subtasks                                                                      |
| `java23/StructuredConcurrencyTest` | [`StructuredTaskScope.open(Joiner)`][StructuredTaskScope] with `allSuccessfulOrThrow`, `anySuccessfulOrThrow`, `awaitAllSuccessfulOrThrow`, `allUntil`; `fork`, `join`, `ExecutionException` (JDK 27 preview API) |

**Key concepts:**

<ul>

- `ScopedValue` is the modern, safe replacement for [`ThreadLocal`][ThreadLocal]: values are bound for a specific scope and are automatically unbound; subtasks forked in a `StructuredTaskScope` inherit them (plain child threads and executor tasks do not); no memory leak risk
- `StructuredTaskScope` ensures all forked subtasks complete (or are cancelled) before the scope closes — structured concurrency makes concurrent code read like sequential code

</ul>

---

### Java 24 (March 2025) — Stream Gatherers Standard

| Test Class                   | Features Covered                                                                                                                                            |
|------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `java24/StreamGatherersTest` | [`Stream.gather()`][Stream], built-in [`Gatherers.windowFixed`][Gatherers], `windowSliding`, `scan`, `fold`, `mapConcurrent`; custom [`Gatherer`][Gatherer] |

**Key concepts:**

<ul>

- Stream gatherers are a flexible intermediate operation beyond what `filter`/`map`/`flatMap` support: sliding windows, running totals, stateful transformations
- `Gatherer` has four parts: initializer (state), integrator (process element), combiner (parallel merge), finisher (emit remaining)

</ul>

---

### Java 25 (September 2025, LTS) — Stable Values & Finalized APIs

| Test Class                  | Features Covered                                                                                                                                                       |
|-----------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `java25/Java25FeaturesTest` | Primitive types in patterns (`instanceof int i`, `switch` on primitive wrappers with type pattern), structured concurrency (still preview) and finalized scoped values |

**Key concepts:**

<ul>

- Primitive type patterns allow matching and binding on unboxed primitives — no NullPointerException risk since primitives cannot be null
- Java 25 is an LTS release: virtual threads, records, sealed classes, pattern matching, text blocks and scoped values are production-stable. Structured concurrency and primitive patterns are still preview features (they need `--enable-preview`, even on JDK 27)

</ul>

---

### Java 26 (March 2026) — Module Imports & Further Refinements

| Test Class                  | Features Covered                                                                                        |
|-----------------------------|---------------------------------------------------------------------------------------------------------|
| `java26/Java26FeaturesTest` | Module import declarations (`import module java.base`), flexible constructor bodies, latest refinements |

**Key concepts:**

<ul>

- Module imports (`import module M`) bulk-import all exported packages of a module — useful for learning/scripting scenarios
- Flexible constructor bodies allow statements before `super()`/`this()` calls as long as they don't reference the instance being initialized
- Both features were already standard in Java 25, and Java 26 left them unchanged. What Java 26 itself added is in the version table below

</ul>

---

<a id="java-version-quick-reference"></a>
## <span style="color:hsl(192,80%,58%)">4. 📚 Java Version Quick Reference</span>

| Version     | Release  | Type    | Key Features                                                                                                                                                    |
|-------------|----------|---------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Java 8**  | Mar 2014 | LTS     | Lambdas, streams, Optional, default methods, date/time API, CompletableFuture                                                                                   |
| **Java 9**  | Sep 2017 |         | Modules (JPMS), collection factory methods, Stream enhancements, private interface methods                                                                      |
| **Java 10** | Mar 2018 |         | `var` (local variable type inference), `copyOf`, `toUnmodifiableList/Set/Map`                                                                                   |
| **Java 11** | Sep 2018 | **LTS** | String methods (`strip`, `isBlank`, `lines`, `repeat`), [`Files.readString/writeString`][Files], [`Predicate.not`][Predicate], HTTP Client                      |
| **Java 12** | Mar 2019 |         | [`Collectors.teeing`][Collectors], [`String.indent/transform`][String], `Files.mismatch`, switch expressions (preview)                                          |
| **Java 13** | Sep 2019 |         | Text blocks (preview), switch expressions (preview 2)                                                                                                           |
| **Java 14** | Mar 2020 |         | Switch expressions (standard), `instanceof` pattern matching (preview), Records (preview), `String.formatted`                                                   |
| **Java 15** | Sep 2020 |         | Text blocks (standard), Sealed classes (preview), `String.stripIndent/translateEscapes`                                                                         |
| **Java 16** | Mar 2021 |         | Records (standard), `instanceof` pattern matching (standard), [`Stream.toList()`][Stream], `Stream.mapMulti()`                                                  |
| **Java 17** | Sep 2021 | **LTS** | Sealed classes (standard), Pattern matching for switch (preview), Enhanced Random generators                                                                    |
| **Java 18** | Mar 2022 |         | UTF-8 by default, Simple web server (`jwebserver`), Code snippets in Javadoc                                                                                    |
| **Java 19** | Sep 2022 |         | Virtual threads (preview), Structured concurrency (incubator), Record patterns (preview)                                                                        |
| **Java 20** | Mar 2023 |         | Scoped values (incubator), Virtual threads (preview 2), Record patterns (preview 2)                                                                             |
| **Java 21** | Sep 2023 | **LTS** | Virtual threads (standard), Sequenced collections, Record patterns (standard), Pattern matching switch (standard)                                               |
| **Java 22** | Mar 2024 |         | Unnamed variables `_` (standard), Stream gatherers (preview), Foreign Function & Memory API (standard)                                                          |
| **Java 23** | Sep 2024 |         | Structured concurrency (preview), Scoped values (preview), Primitive types in patterns (preview), Markdown Javadoc                                              |
| **Java 24** | Mar 2025 |         | Stream gatherers (standard), Class-File API (standard), Scoped values & Structured concurrency (preview 4)                                                      |
| **Java 25** | Sep 2025 | **LTS** | Scoped values, Module import declarations, Flexible constructor bodies (standard); Structured concurrency, Primitive types in patterns, Stable values (preview) |
| **Java 26** | Mar 2026 |         | HTTP/3 in the HTTP client (standard); Lazy constants (preview, formerly Stable values); Structured concurrency and Primitive types in patterns still preview    |

---

<a id="design-decisions"></a>
## <span style="color:hsl(329,80%,58%)">5. 🏗️ Design Decisions</span>

<ul>

- **Tests as documentation**: every concept lives in a [`@Test`][Test] method whose name, and in most classes a [`@DisplayName`][DisplayName], states the rule being demonstrated
- **Assertions over println**: every test asserts a concrete outcome — the test suite is the specification
- **Nested types**: Records, sealed classes, and helper interfaces are defined as `static` nested types inside test classes to keep related code co-located
- **No mocks**: tests use real JDK APIs; for I/O tests, JUnit's [`@TempDir`][TempDir] provides isolated temporary directories
- **Preview features**: the project compiles with `--enable-preview` to cover features in their preview phase alongside finalized ones

</ul>

<!-- Library classes mentioned above, linked to their source at the versions this project builds with. -->

[AtomicInteger]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/concurrent/atomic/AtomicInteger.java
[BiConsumer]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/function/BiConsumer.java
[BiFunction]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/function/BiFunction.java
[BinaryOperator]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/function/BinaryOperator.java
[BiPredicate]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/function/BiPredicate.java
[Character]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/lang/Character.java
[Collection]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/Collection.java
[Collectors]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/stream/Collectors.java
[ConcurrentHashMap]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/concurrent/ConcurrentHashMap.java
[Consumer]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/function/Consumer.java
[CountDownLatch]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/concurrent/CountDownLatch.java
[Date]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/Date.java
[Date (java.sql)]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.sql/share/classes/java/sql/Date.java
[Deque]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/Deque.java
[DisplayName]: https://github.com/junit-team/junit-framework/blob/r6.1.3/junit-jupiter-api/src/main/java/org/junit/jupiter/api/DisplayName.java
[DoubleStream]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/stream/DoubleStream.java
[Executors]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/concurrent/Executors.java
[Files]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/nio/file/Files.java
[Function]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/function/Function.java
[Future]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/concurrent/Future.java
[Gatherer]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/stream/Gatherer.java
[Gatherers]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/stream/Gatherers.java
[IntStream]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/stream/IntStream.java
[LinkedHashMap]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/LinkedHashMap.java
[LinkedHashSet]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/LinkedHashSet.java
[List]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/List.java
[LocalDate]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/time/LocalDate.java
[LocalDateTime]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/time/LocalDateTime.java
[LocalTime]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/time/LocalTime.java
[LongAccumulator]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/concurrent/atomic/LongAccumulator.java
[LongAdder]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/concurrent/atomic/LongAdder.java
[LongStream]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/stream/LongStream.java
[Map]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/Map.java
[Math]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/lang/Math.java
[Optional]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/Optional.java
[Path]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/nio/file/Path.java
[Pattern]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/regex/Pattern.java
[Predicate]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/function/Predicate.java
[Random]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/Random.java
[RandomGenerator]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/random/RandomGenerator.java
[RandomGeneratorFactory]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/random/RandomGeneratorFactory.java
[ReadWriteLock]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/concurrent/locks/ReadWriteLock.java
[ReentrantLock]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/concurrent/locks/ReentrantLock.java
[Repeatable]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/lang/annotation/Repeatable.java
[Retention]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/lang/annotation/Retention.java
[Runnable]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/lang/Runnable.java
[ScheduledExecutorService]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/concurrent/ScheduledExecutorService.java
[ScopedValue]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/lang/ScopedValue.java
[SecureRandom]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/security/SecureRandom.java
[Semaphore]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/concurrent/Semaphore.java
[SequencedCollection]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/SequencedCollection.java
[SequencedMap]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/SequencedMap.java
[Set]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/Set.java
[StampedLock]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/concurrent/locks/StampedLock.java
[Stream]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/stream/Stream.java
[String]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/lang/String.java
[StructuredTaskScope]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/concurrent/StructuredTaskScope.java
[Supplier]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/function/Supplier.java
[System]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/lang/System.java
[TempDir]: https://github.com/junit-team/junit-framework/blob/r6.1.3/junit-jupiter-api/src/main/java/org/junit/jupiter/api/io/TempDir.java
[TemporalAdjusters]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java
[Test]: https://github.com/junit-team/junit-framework/blob/r6.1.3/junit-jupiter-api/src/main/java/org/junit/jupiter/api/Test.java
[Thread]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/lang/Thread.java
[ThreadLocal]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/lang/ThreadLocal.java
[ThreadLocalRandom]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/concurrent/ThreadLocalRandom.java
[TimeoutException]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/concurrent/TimeoutException.java
[UnaryOperator]: https://github.com/openjdk/jdk/blob/jdk-27-ga/src/java.base/share/classes/java/util/function/UnaryOperator.java
