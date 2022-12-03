package chapter_2.section_4;

import chapter_2.setting.Apple;

import javax.swing.plaf.ButtonUI;
import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static chapter_2.setting.Color.GREEN;
import static chapter_2.setting.Color.RED;

public class Application {
    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(new Apple(GREEN, 80), new Apple(GREEN, 160), new Apple(RED, 120));

        /**
         * Comparator로 정렬하기
         */
        inventory.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight() - o2.getWeight();
            }
        });

        inventory.sort((Apple a1, Apple a2) -> a1.getWeight() - a2.getWeight());

        /**
         * Runnable로 코드 블록 실행하기.
         */

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World");
            }
        });

        Thread t2 = new Thread(() -> System.out.println("Awesome Lambda"));

        /**
         * Callable을 결과로 반환하기
         */

        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> threadName1 = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return Thread.currentThread().getName();
            }
        });

        Future<String> threadName2 = executorService.submit(() -> Thread.currentThread().getName());


    }
}
