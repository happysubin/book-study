package chapter_15.v0;

public class ThreadExample {

    public static void main(String[] args) throws InterruptedException {
        int x = 1337;
        Result result = new Result();

        /**
         * 비싼 연산을 효율적으로 하기 위해 병렬로 실행
         */
        Thread t1 = new Thread(() -> {
            f(x);
        });
        Thread t2 = new Thread(() -> {
            f(x);
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(result.left + result.right);

    }

    static class Result {
        private int left;
        private int right;
    }

    /**
     * 비싼 연산이라고 가정
     */
    static int f(int x) {
        return x;
    }

    static int g(int y) {
        return y;
    }
}
