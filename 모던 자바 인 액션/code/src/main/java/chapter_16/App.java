package chapter_16;

import chapter_16.pipeline.Discount;
import chapter_16.pipeline.ExchangeService;
import chapter_16.pipeline.Money;
import chapter_16.pipeline.Quote;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class App {

    private static final Double DEFAULT_RATE = 1.0;
    private List<Shop> shops;
    private final Executor executor;

    public static void main(String[] args) {
        List<Shop> shops =
                Arrays.asList(new Shop("BestPrice"), new Shop("LetsSaveBig"), new Shop("MyFavoriteShop"), new Shop("BuyAll"));
        App app = new App(shops);

        long start = System.nanoTime();
        System.out.println(app.findPricesV5("myPhone275"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("duration = " + duration);


        long start2 = System.nanoTime();
        System.out.println(app.findPrices("myPhone275"));
        long duration2 = (System.nanoTime() - start2) / 1_000_000;
        System.out.println("duration = " + duration2);


        System.out.println(Runtime.getRuntime().availableProcessors()); //8


        CompletableFuture[] futures = app.findPricesStream("myPhone275")
                .map(f -> f.thenAccept(System.out::println))
                .toArray(size -> new CompletableFuture[size]);
        CompletableFuture.allOf(futures).join();
    }

    public App(List<Shop> shops) {
        this.shops = shops;
        this.executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(); //프로그램 종료를 방해하지 않는 데몬 스레드를 사용
                thread.setDaemon(true);
                System.out.println("thread = " + thread);
                return thread;
            }
        });
    }

    public List<String> findPrices(String product) {
        return shops
                .stream()
                .map(shop -> String.format("%s prices is %s", shop.getName(), shop.getPrice(product)))
                .collect(toList());
    }

    public List<String> findPricesV2(String product) {
        return shops
                .parallelStream()
                .map(shop -> String.format("%s prices is %s", shop.getName(), shop.getPrice(product)))
                .collect(toList());
    }

    public List<String> findPricesV3(String product) {
        List<CompletableFuture<String>> futures = shops
                .stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> String.format("%s prices is %s", shop.getName(), shop.getPrice(product)), executor))
                .collect(toList());
        return futures
                .stream()
                .map(future -> future.join()) //get 메서드와 동일한 의미
                .collect(toList());
    }

    public List<String> findPricesV4(String product) {
        return shops.stream()
                .map(shop -> shop.getPrice(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(toList());
    }

    public List<String> findPricesV5(String product) {
        List<CompletableFuture<String>> futures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)))
                .collect(toList());
        return futures.stream()
                .map(CompletableFuture::join)
                .collect(toList());
    }

    public Future<Double> futurePriceInUSD(String product) {
        Shop shop = shops.get(0);
        return CompletableFuture.supplyAsync(() -> shop.getPrice(product))
                .thenCombine(
                        CompletableFuture.supplyAsync(
                                () -> new ExchangeService().getRate(Money.EUR, Money.USD))
                                .completeOnTimeout(DEFAULT_RATE, 1 , TimeUnit.SECONDS)
                        ,
                        (price, rate) -> Long.valueOf(price) * rate
                )
                .orTimeout(3, TimeUnit.SECONDS);
    }

    public Stream<CompletableFuture<String>> findPricesStream(String product) {
        return shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)));
    }
}
