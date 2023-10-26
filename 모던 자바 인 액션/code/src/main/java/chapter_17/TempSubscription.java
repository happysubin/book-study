package chapter_17;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Subscription;

import static java.util.concurrent.Flow.*;

public class TempSubscription implements Subscription {

    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    private final Subscriber<? super TempInfo> subscriber;
    private final String town;

    public TempSubscription(Subscriber<? super TempInfo> subscriber, String town) {
        this.subscriber = subscriber;
        this.town = town;
    }

    /**
     * 반드시 n보다 낮은 개수를 전달
     * 해당 메서드를 사용해 역압력을 행사할 수 있다.
     */
    @Override
    public void request(long n) {
        executor.submit(() -> {
            for (int i = 0; i < n; i++) {
                try{
                    subscriber.onNext(TempInfo.fetch(town));
                } catch (Exception e) {
                    subscriber.onError(e);
                    break;
                }
            }
        });

    }

    @Override
    public void cancel() {

    }
}
