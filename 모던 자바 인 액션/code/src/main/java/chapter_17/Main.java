package chapter_17;

import io.reactivex.rxjava3.core.Observable;

import java.util.Arrays;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        getCelsiusTemperatures("New York").subscribe(new TempSubscriber());

        Observable<TempInfo> observable = getTemperaturesV2("New York");
        observable.blockingSubscribe(new TempObserver());

        Observable<TempInfo> v2 = getCelsiusTemperatures("New York", "Chicage", "San Francisco");
        v2.blockingSubscribe(new TempObserver());
    }

    /**
     * Publisher와 Subscriber는 동일한 Subscription을 공유해야한다.
     */
    private static Publisher<TempInfo> getTemperatures(String town) {
        return new Publisher<TempInfo>() {
            @Override
            public void subscribe(Subscriber<? super TempInfo> subscriber) {
                subscriber.onSubscribe(new TempSubscription(subscriber, town));
            }
        };
    }



    private static Observable<TempInfo> getTemperaturesV2(String town) {
        return Observable.create(emitter -> //Observer를 소비하는 함수로부터 Observable 만들기
                Observable.interval(1, TimeUnit.SECONDS) //매초마다 무한으로 증가하는 일련의 long 값을 방출하는 Observable
                        .subscribe(i -> {
                            if(!emitter.isDisposed()) { //소비된 옵저버가 아직 폐기되지 않았으면 어떤 작업을 수행
                                if(i >= 5) { //온도를 5번 이상 보고했으면 옵저버를 완료하고 스트림 종료
                                    emitter.onComplete();
                                }
                               else {
                                    try {
                                        emitter.onNext(TempInfo.fetch(town)); //아니면 온도를 옵저버에게 보고
                                    } catch (Exception e) {
                                        emitter.onError(e);
                                    }
                                }
                            }
                        })
                );
    }

    public static Publisher<TempInfo> getCelsiusTemperatures(String town){
        return subscriber -> {
            TempProcessor processor = new TempProcessor();
            processor.subscribe(subscriber);
            processor.onSubscribe(new TempSubscription(processor, town));
        };
    }


    public static Observable<TempInfo> getCelsiusTemperaturesV2(String town) {
        return getTemperaturesV2(town)
                .map(temp -> new TempInfo(temp.getTown(), (temp.getTemp() - 32) * 5 / 9));
    }

    public static Observable<TempInfo> getCelsiusTemperatures(String... towns) {
        return Observable.merge(
                Arrays.stream(towns)
                .map(Main::getCelsiusTemperaturesV2)
                .collect(Collectors.toList()));
    }


}
