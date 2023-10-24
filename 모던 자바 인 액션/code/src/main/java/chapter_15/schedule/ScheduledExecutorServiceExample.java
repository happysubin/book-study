package chapter_15.schedule;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceExample {

    /**
     * 예시 A
     */
    void example() throws InterruptedException {
        work1();
        Thread.sleep(10000);
        work2();
    }

    private static void work1() {
        System.out.println("work1");
    }

    private static void work2() {
        System.out.println("work2");
    }

    /**
     * 예시 B
     */
    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        work1();

        scheduledExecutorService.schedule(ScheduledExecutorServiceExample::work2, 10, TimeUnit.SECONDS);

        scheduledExecutorService.shutdown();

    }
}
