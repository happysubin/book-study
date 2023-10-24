package chapter_15.v1;

import javax.xml.transform.Result;
import java.util.concurrent.*;
import java.util.function.IntConsumer;

public class CallbackStyleExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int x = 1337;

        Result result = new Result();

        f(x, (int y) -> {
            result.left = y;
            System.out.println(result.left + result.right);
        });

        g(x, (int y) -> {
            result.right = y;
            System.out.println(result.left + result.right);
        });


    }

    static class Result {
        private int left;
        private int right;
    }

    /**
     * 비싼 연산이라고 가정
     */
    static void f(int x, IntConsumer dealWithResult) {
        dealWithResult.accept(x);
    }

    static void g(int x, IntConsumer dealWithResult) {
        dealWithResult.accept(x);
    }
}
