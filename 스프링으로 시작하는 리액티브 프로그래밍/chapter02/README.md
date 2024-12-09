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


