package chapter_15.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CFComplete {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        /**
         * v1
         */
        ExecutorService executorService1 = Executors.newFixedThreadPool(10);
        int x = 1337;

        CompletableFuture<Integer> a = new CompletableFuture<>();
        executorService1.submit(() -> a.complete(f(x)));
        int b = g(x);

        System.out.println(a.get() + b);

        executorService1.shutdown();
    }

    private static Integer f(int x) {
        return x;
    }

    private static Integer g(int x) {
        return x;
    }
}
