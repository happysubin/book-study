package chapter_16;

import chapter_16.pipeline.Discount;

import java.nio.DoubleBuffer;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Shop {

    private String name;

    public static void main(String[] args) {
        Shop shop = new Shop("n");
        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPriceAsyncV2("my favorite product");
        long invocationTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("invocationTime = " + invocationTime);

        //다른 작업 수행
        doSomethingElse();

        try{
            Double price = futurePrice.get();
            System.out.println("price = " + price);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        long retrievalTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("retrievalTime = " + retrievalTime);
    }

    public Shop(String name) {
        this.name = name;
    }

    public Future<Double> getPriceAsync(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            try{
                double price = calculatePrice(product); //다른 스레드에서 비동기적으로 계산 수행
                futurePrice.complete(price); //오랜 시간이 걸리는 계산이 완료되면 Future에 값 서렂ㅇ
            } catch (Exception ex) {
                futurePrice.completeExceptionally(ex); //도중에 문제가 발생하면 에러를 포함시켜 Future를 종료한다./
            }

        }).start();
        return futurePrice; //계산 결과를 기다리지 않고 return
    }

    public Future<Double> getPriceAsyncV2(String product) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }

    public String getPrice(String product) {
        double price = calculatePrice(product);
        Discount.Code code = Discount.Code.values()[new Random().nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s", name, price, code);
    }

    private double calculatePrice(String product) {
        delay();
        return new Random().nextDouble() * product.charAt(0) + product.charAt(1);
    }

    public static void delay() {
        try {
            Thread.sleep(1000); //1초 블록
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void randomDelay() {
        int delay = 500 + new Random().nextInt(2000);
        try{
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void doSomethingElse() {
    }


    public String getName() {
        return name;
    }
}
