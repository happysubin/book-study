# chapter 02. 리액티브 스트림즈

### 2.1 리액티브 스트림즈

리액티브 라이브러리를 정의해 놓은 별도의 표준 사양이 있는데, 이게 바로 리액티브 스트림즈다.

리액티브 스트림즈는 '데이터 스트림을 Non-Blocking이면서 비동기적인 방식으로 처리하기 위한 리액티브 라이브러리의 표준 사양'이라고 표현할 수 있다.

RxJava, Reactor등 다양한 구현체가 있지만 스프링과 궁합이 잘맞는 Reactor에 대해 알아보자.

### 2.2 리액티브 스트림즈 구성요소

리액티브 스트림즈를 통해 구현해야 되는 API 컴포넌트에는 4가지가 있다.

* Publisher: 데이터를 생성하고 통지(발행, 게시, 방출)하는 역할을 한다.
* Subscriber: 구독한 Publisher로부터 통지(발행, 게시, 방출)된 데이터를 전달받아서 처리하는 역할을 한다.
* Subscription: Publisher에 요청할 데이터의 개수를 지정하고, 데이터의 구독을 취소하는 역할을 한다.
* Processor: Publisher와 Subscriber의 기능을 모두 가지고 있다. 즉, Subscriber로서 다른 Publihser를 구독할 수 있고, Publihser로서 다른 Subscriber가 구독할 수 있다.

리액티브 스트림즈의 컴포넌트인 Publisher와 Subscriber 간에 데이터가 전달되는 동작을 아래와 같이 설명할 수 있다.

1. 먼저 Subscriber는 전달받을 데이터를 구독한다.(subscribe)
2. Publihser는 데이터를 통지할 준비가 되었음을 Subscriber에게 알린다. (onSubscribe)
3. Publihser가 데이터를 통지할 준비가 되었다는 알림을 받은 Subscriber는 전달받기를 원하는 데이터의 개수를 Publihser에게 요청한다. (Subscription.request)
4. 다음으로 Publihser는 Subscriber로부터 요청받은 만큼의 데이터를 통지한다.(onNext)
5. 이렇게 Publihser와 Subscriber 간에 데이터 통지, 데이터 수신, 데이터 요청의 과정을 반복하다가 Publihser가 모든 데이터를 통지하게 되면 마지막으로 데이터 전송이 완료되었음을 Subscriber에게 알린다.(onComplete)만약에 Publihser가 데이터를 처리하는 과정에서 에러가 발생하면 에러가 발생했음을 Subscriber에게 알린다.(onError)


Publihser와 Subscriber는 각각 다른 스레드에서 비동기적으로 상호작용하는 경우가 대부분이다.

이럴 경우 Publisher가 통지하는 속도가 Publihser로부터 통지된 데이터를 Subscriber가 처리하는 속도보다 더 빠르면 처리를 기다리는 데이터는 쌓이게 되고,
이는 결과적으로 시스템 부하가 커지는 결과를 낳게 된다.
따라서 이런 문제를 방지하기 위해 Subscription.request를 통해 데이터 개수를 제어하는 것이다.

### 2.3 코드로 보는 리액티브 스트림즈 컴포넌트

```java
public interface Publisher<T> {
    void subscribe(Subscriber<? super T> var1);
}

```

Publisher 인터페이스 코드에서 subscribe 메서드는 파라미터로 전달받은 Subscriber를 등록하는 역할을 한다.
그럼 다음과 같은 의문이 들 수 있다.

'Publisher는 데이터를 생성하고 통지하는 역할을 하고, Subscriber는 Publisher가 통지하는 데이터를 전달받기 위해 구독을 한다고 이해하고 있는데, 왜 Subscriber가 아닌 Publisher에 subscribe 메서드가 정의되어 있을까??'

Publisher와 Subscriber는 브로커에 있는 특정 토픽을 바라보는 구조로 이루어져 있다.
리액티브 스트림즈에서의 Publihser와 Subscriber 개념상으로는 Subscriber가 구독하는 것이 맞는데 실제 코드상에서는 Publihser가 subscribe 메서드의 파라미터인 Subscriber를 등록하는 형태로 구독이 이루어진다.


```java
public interface Subscriber<T> {
    void onSubscribe(Subscription var1);
    void onNext(T var1);
    void onError(Throwable var1);
    void onComplete();
}
```

Subscriber는 총 4개의 메서드를 구현해야 한다.

* onSubscribe: 메서드는 구독 시작 시점에 어떤 처리를 하는 역할을 한다. 여기서의 처리는 Publihser에게 요청할 데이터의 개수를 지정하거나 구독을 해지하는 것을 의미하는데, 이것은 onSubscribe aptjemdml vkfkalxjfh wjsekfehlsms Subscription 객체를 통해서 이루어진다.
* onNext: 메서드는 Publisher가 통지한 데이터를 처리하는 역할을 한다.
* onError: 메서드는 Publisher가 데이터 통지를 위한 처리 과정에서 에러가 발생했을 때 해당 에러를 처리하는 역할을 한다.
* onComplete: 메서드는 Publihser가 데이터 통지를 완료했음을 알릴 때 호출되는 메서드다. 데이터 통지가 정상적으로 완료될 경우에 어떤 후처리를 해야 한다면 onComplete 메서드에서 처리 코드를 완성하면 된다.

```java
public interface Subscription {
    void request(long var1);
    void cancel();
}
```

Subscription 인터페이스는 Subscriber가 구독한 데이터의 개수를 요청하거나 또는 데이터 요청의 취소, 즉 구독을 해지하는 역할을 한다.
request 메서드를 통해 Publisher에게 데이터의 개수를 요청할 수 있고, cancel 메서드를 통해서 구독을 해지할 수 있다.

* Publisher가 Subscriber 인터페이스 구현 객체를 subscribe 메서드의 파라미터로 전달한다.
* Publisher가 내부에서 전달받은 Subscriber 인터페이스 구현 객체의 onSubscribe 메서드를 호출하면서 Subscriber의 구독을 의미하는 Subscription 인터페이스 구현 객체를 Subscriber에게 전달한다.
* 호출된 Subscriber 인터페이스 구현 객체의 onSubscribe 메서드에서 전달받은 Subscription 객체를 통해 전달받을 데이터의 개수를 Publisher에게 요청한다.
* Publisher는 Subscriber로부터 전달받은 요청 개수만큼의 데이터를 onNext 메서드를 호출해서 Subscriber에게 전달한다.
* Publihser는 통지할 데이터가 더 이상 없을 경우 onComplete 메서드를 호출해서 Subscriber에게 데이터 처리 종료를 알린다.

```java
public interface Processor<T, R> extends Subscriber<T>, Publisher<R> {
}
```

Processor는 별도로 구현할 메서드가 없다.
다른 인터페이스들과 다른 점은 Subscriber 인터페이스와 Publisher 인터페이스를 상속한다는 것이다. 두 가지 기능을 모두 가지고 있기 때문이다.


### 2.4 리액티브 스트림즈 관련 용어 정의

이번에는 리액티브 스트림즈에서 자주 사용하는 용어 몇가지를 알아보자.

#### Signal

리액티브 스트림즈에서는 publisher와 subscriber간에 주고 받는 상호작용을 신호, 즉 signal이라고 표현한다.

리액티브 스트립즈의 인터페이스 코드에서 볼 수 있는 onSubscribe, onNext, onComplete, onError, request 또는 cancel 메서드를 Signal이라고 표현한다.

onSubscribe, onNext, onComplete, onError 메서드는 Subscriber 인터페이스에 정의되지만 이 메서드들을 실제 호출해서 사용하는 주체는 PUblisher이기 때문에 Publisher가 Subscriber에게 보내는 signal이라고 볼 수 있다.

그리고 request와 cancel 메서드는 Subscription 인터페이스 코드에 정의되지만 이 메서드들을 실제로 사용하는 주체는 Subscribe이므로 Subscriber가 Publisher에게 보내는 Signal이라고 볼 수 있다.

#### Demand

Demand는 바로 Subscriber가 Publisher에게 요청하는 데이터를 의미한다.
정확히는 Publisher가 아직 Subscriber에게 전달하지 않은 Subscriber가 요청한 데이터를 말한다.

#### Emit

Publisher와 Subscriber의 동작 과정을 설명하면서 Publisher가 Subscriber에게 데이터를 전달하는 것을 일컬을 때 데이터를 통지한다라는 용어를 사용했다.
데이터를 내보내다라고 의미하면 된다.

Publisher가 emit하는 signal중에서 데이터를 전달하기 위한 OnNext Signal을 줄여서 데이터를 emit 한다라고 표현한다.


#### Upstream/Downstream

```java
public class Example2_5 {
    public static void main(String[] args) {
        Flux
                .just(1, 2, 3, 4, 5, 6)
                .filter(n -> n % 2 == 0)
                .map(n -> n * 2)
                .subscribe(System.out::println);
    }
}
```

위 코드에서 just 메서드를 사용해서 데이터를 생성한 후 emit 하게 된다.
여기서 just 메서드는 리액티브 스트림즈의 컴포넌트 중에서 Publisher 역할을 수행한다.

이후 filter와 map 메서드가 연속으로 호출되는 것을 메서드 체인이라고 한다.
메서드 체인 방식이 가능한 이유는 호출하는 각각의 메서드들이 모두 같은 타입의 객체를 반환하기 때문이다.
각각의 메서드들이 반환하는 (subscribe 메서드 제외) 반환 값이 모두 Flux 타입의 객체이기 때문에 이런 식의 메서드 호출이 가능하다.

데이터 스트림의 관점에서 볼 때, 4번 라인의 just 메서드 호출을 통해 반환된 Flux 입장에서는 5번 라인의 filter 메서드 호출을 통해 반환된 Flux가 자신보다 더 하위에 있기 때문에 Downstream이 된다.

반면에 filter 메서드 호출을 통해 반횐된 Flux 입장에서는 just 메서드 호출을 통해 변환된 Flux가 자신보다 더 상위에 있기 때문에 Upstream이 된다.

#### Sequence

Sequence는 Publisher가 emit하는 데이터의 연속적인 흐름을 정의해 놓은 것 자체를 의미하는데, 이 Sequence는 Operator 체인 형태로 정의된다.
위 코드와 같이 Flux를 통해서 데이터를 생성, emit하고 filter 메서드를 통해서 필터링하고 map 메서드를 통해 변환하는 이런 모든 과정을 Sequence라고 부른다.

#### Operator

Operator는 우리말로 연산자 정도로 해석할 수 있다.
just, filter, map 같은 메서드들을 리액티브 프로그래밍에서 연산자라고 부른다. 아주 중요한 내용

#### Source 

가장 대표적으로 Data Source, Source Publisher, Source Flux 등을 들 수 있는데 대부분 '최초의'라는 의미로 사용된다.


### 2.5 리액티브 스트림즈의 규칙

Publisher 구현을 위한 주요 기본 규칙

1. Publisher가 Subscriber에게 보내는 onNext signal의 총 개수는 항상 해당 Subscriber의 구독을 통해 요청된 데이터의 총 개수보다 더 작거나 같아야 한다.
2. Publisher는 요청된 것보다 적은 수의 onSignal을 보내고 onComplete 또는 onError를 호출하여 구독을 종료할 수 있다.
3. Publisher의 데이터 처리가 실패하면 onError signal을 보내야 한다.
4. Publisher의 데이터 처리가 성공적으로 종료되면 onComplete signal을 보내야 한다.
5. PUblisher가 Subscriber에게 onError 또는 onComplete signal을 보내는 경우 해당 Subscriber의 구독은 취소된 것으로 간주되어야 한다.
6. 일단 종료 상태 signal을 받으면 (onError, onComplete) 더 이상 signal이 발생되지 않아야 한다.
7. 구독이 취소되면 Subscriber는 결국 signal을 받는 것을 중지해야 한다.

Subscriber 구현을 위한 기본 규칙

1. Subscriber는 Publisher로부터 onNext signal을 수신하기 위해 Subscription.request(n)를 통해 Demand signal을 Publisher에게 보내야 한다.
2. Subscriber.onComplete() 및 Subscriber.onError(Throwable t)는 Subscription 또는 Publisher의 메서드를 호출해서는 안된다.
3. Subscriber.onComplete(), Subscriber.onError(Throwable t)는 signal을 수신한 후 구독이 취소된 것으로 간주해야한다.
4. 구독이 더 이상 필요하지 않은 경우 Subscriber는 Subcription.cancel()을 호출해야 한다.
5. Subscriber.onSubcribe()는 지정된 Subscriber에 대해 최대 한 번만 호출되어야 한다.

Subscription 구현을 위한 주요 기본 규칙

1. 구독은 Subcriber가 onNext 또는 onSubscriber 내에서 동기적으로 Subscription.request를 호출하도록 허용해야 한다.
2. 구독이 취소된 후 추가적으로 호출되는 Subscription.request(long n)은 효력이 없어야 한다.
3. 구독이 취소된 후 추가적으로 호출되는 Subscription.cancel()은 효력이 없어야 한다.
4. 구독이 취소되지 않은 동안 Subscription.request(long n)의 매개변수가 0보다 작거나 같으면 java.lang.IllengalArgumentException과 함께 onError signal을 보내야 한다.
5. 구독이 취소되지 않은 동안 Subscription.cancel()은 Publisher가 Subscriber에게 보내는 signal을 결국 중지하도록 요청해야 한다.
6. 구독이 취소되지 않은 동안 Subscription.cancel()은 Publisher에게 해당 구독자에 대한 참조를 결국 삭제하도록 요청해야 한다.
7. Subscription.cancel(), Subscription.request() 호출에 대한 응답으로 예외를 던지는 것을 허용하지 않는다.
8. 구독은 무제한 수의 request 호출을 지원해야 하고 최대 2^63 - 1개의 Demand를 지원해야 한다.
