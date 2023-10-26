package chapter_17;

import java.util.concurrent.Flow.Subscriber;

import static java.util.concurrent.Flow.*;

/**
 * 해당 클래스의 목적은 Publisher를 구독한 다음 수신한 데이터를 가공해 다시 제공하는 것이다.
 */

public class
TempProcessor implements Processor<TempInfo, TempInfo> {

    private Subscriber<? super TempInfo> subscriber;

    @Override
    public void subscribe(Subscriber<? super TempInfo> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void onNext(TempInfo temp) {
        subscriber.onNext(new TempInfo(temp.getTown(), (temp.getTemp() - 32 ) * 5 / 9));
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        subscriber.onSubscribe(subscription);
    }

    @Override
    public void onError(Throwable throwable) {
        subscriber.onError(throwable);
    }

    @Override
    public void onComplete() {
        subscriber.onComplete();
    }
}
