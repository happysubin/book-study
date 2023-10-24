package chapter_15.v1;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureStyleExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int x = 1337;

        Future<Integer> y = f(x);
        Future<Integer> z = g(x);

        System.out.println(y.get() + z.get());

    }

    /**
     * 비싼 연산이라고 가정
     */
    static Future<Integer> f(int x) {
        return getIntegerFuture(x);
    }

    static Future<Integer> g(int y) {
        return getIntegerFuture(y);
    }

    private static Future<Integer> getIntegerFuture(int y) {
        return new Future<Integer>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public Integer get() throws InterruptedException, ExecutionException {
                return y;
            }

            @Override
            public Integer get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return y;
            }
        };
    }
}
