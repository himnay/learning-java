package com.org.java.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class CompletableFutureTest {

    @Test
    void complete_setsValueAndMakesItRetrievable() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = new CompletableFuture<>();
        future.complete("42");
        assertEquals("42", future.get());
        assertTrue(future.isDone());
    }

    @Test
    void thenAccept_consumesResultWhenDone() throws ExecutionException, InterruptedException {
        StringBuilder sb = new StringBuilder();
        CompletableFuture<String> future = new CompletableFuture<>();
        future.thenAccept(sb::append);
        future.complete("hello");
        future.get(); // ensure chain is done
        assertEquals("hello", sb.toString());
    }

    @Test
    void supplyAsync_computesResultAsynchronously() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 21 * 2);
        assertEquals(42, future.get());
    }

    @Test
    void thenApply_transformsResult() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture
                .supplyAsync(() -> "hello")
                .thenApply(String::toUpperCase);
        assertEquals("HELLO", future.get());
    }

    @Test
    void thenCombine_combinesTwoFutures() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> 10);
        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> 20);
        CompletableFuture<Integer> combined = f1.thenCombine(f2, Integer::sum);
        assertEquals(30, combined.get());
    }

    @Test
    void exceptionally_handlesFutureFailure() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture
                .<String>supplyAsync(() -> { throw new RuntimeException("oops"); })
                .exceptionally(ex -> "recovered: " + ex.getMessage());
        assertTrue(future.get().contains("recovered"));
    }
}
