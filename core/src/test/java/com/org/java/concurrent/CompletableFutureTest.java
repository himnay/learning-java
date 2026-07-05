package com.org.java.concurrent;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class CompletableFutureTest {

    @Test
    @DisplayName("complete() sets the future's value and makes it retrievable via get()")
    void complete_setsValueAndMakesItRetrievable() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = new CompletableFuture<>();
        future.complete("42");
        assertEquals("42", future.get());
        assertTrue(future.isDone());
    }

    @Test
    @DisplayName("thenAccept() consumes the result once the future completes")
    void thenAccept_consumesResultWhenDone() throws ExecutionException, InterruptedException {
        StringBuilder sb = new StringBuilder();
        CompletableFuture<String> future = new CompletableFuture<>();
        future.thenAccept(sb::append);
        future.complete("hello");
        future.get(); // ensure chain is done
        assertEquals("hello", sb.toString());
    }

    @Test
    @DisplayName("supplyAsync() computes and returns the result asynchronously")
    void supplyAsync_computesResultAsynchronously() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 21 * 2);
        assertEquals(42, future.get());
    }

    @Test
    @DisplayName("thenApply() transforms the completed result into a new value")
    void thenApply_transformsResult() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture
                .supplyAsync(() -> "hello")
                .thenApply(String::toUpperCase);
        assertEquals("HELLO", future.get());
    }

    @Test
    @DisplayName("thenCombine() merges the results of two independent futures")
    void thenCombine_combinesTwoFutures() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> 10);
        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> 20);
        CompletableFuture<Integer> combined = f1.thenCombine(f2, Integer::sum);
        assertEquals(30, combined.get());
    }

    @Test
    @DisplayName("exceptionally() recovers from a failed future with a fallback value")
    void exceptionally_handlesFutureFailure() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture
                .<String>supplyAsync(() -> { throw new RuntimeException("oops"); })
                .exceptionally(ex -> "recovered: " + ex.getMessage());
        assertTrue(future.get().contains("recovered"));
    }
}
