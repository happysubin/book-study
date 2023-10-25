# chapter 16. CompletableFuture: 안정적 비동기 프로그래밍

## section 16.1 Future의 단순 활용

### Future 제한

Future이 가지고 있는 메서드만 사용해서는 복잡한 로직 코드를 간결한 동시 실행 코드로 구현하기 어렵다.
다음과 같은 선언형 기능이 있다면 유용할 것이다.

* 두 개의 비동기 계산 결과를 하나로 합친다. 두 가지 계산 결과는 서로 독립적일 수 있으며 또는 두 번째 결과가 첫 번째 결과에 의존하는 상황일 수 있다.
* Future 집합이 실행하는 모든 태스크의 완료를 기다린다.
* Future 집합에서 가장 빨리 완료되는 태스크를 기다렸다가 결과를 얻는다.
* 프로그램적으로 Future를 완료시킨다. (즉, 비동기 동작에 수동으로 결과 제공)
* Future 완료 동작에 반응한다.(즉, 결과를 기다리면서 블록되지 않고 결과가 준비되었다는 알림을 받은 다음에 Future의 결과로 원하는 추가 동작을 실행할 수 있다.)

<br>

## section 16.2 비동기 API 구현

### CompletableFuture로 비동기 애플리케이션 만들기

애플리케이션 만들기 시작

### 에러 처리 방법

catch 절에서 completeExceptionally 메서드 사용

### 팩토리 메서드 supplyAsync로 CompletableFuture 만들기

* supplyAsync 메서드는 Supplier를 인수로 받아서 CompletableFuture를 반환한다.
* CompletableFuture는 Supplier를 실행해서 비동기적으로 결과를 생성한다.
* ForkJoinPool의 Executor 중 하나가 Supplier를 실행할 것이다.
* 하지만 두 번째 인수를 받는 오버로드 버전의 supplyAsync 메서드를 이용해서 다른 Executor를 지정할 수 있다.
* 결국 모든 다른 CompletableFuture의 팩토리 메서드에 Executor를 선택적으로 전달할 수 있다.
* 에러 관리도 마찬가지로 해준다.

지금부터는 Shop 클래스에서 구현한 API를 제어할 권한이 우리에게 없는 상황이며 모든 API는 동기 방식의 블록 메서드라고 가정할 것이다.

<br>

## section 16.3 비블록 코드 만들기

처음은 단순 스트림 활용. 

### 병렬 스트림으로 요청 병렬화하기 

parallelStream() 메서드를 활용해서 요청을 병렬화함.

### CompletableFuture로 비동기 호출 구현하기

```
public List<String> findPricesV3(String product) {
        List<CompletableFuture<String>> futures = shops
                .stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> String.format("%s prices is %.2f", shop.getName(), shop.getPrice(product))))
                .collect(Collectors.toList());
        return futures
                .stream()
                .map(future -> future.join()) //get 메서드와 동일한 의미
                .collect(Collectors.toList());
    }
```

위 코드에서 두 map 연산을 하나의 스트림 처리 파이프라인으로 처리하지 않고 두 개의 스트림 파이프라인으로 처리했다는 사실을 주목하자.
 
스트림 연산은 게으른 특성이 있으므로 하나의 파이프라인으로 연산을 처리했다면 모든 가격 정보 요청이 동작이 동기적, 순차적으로 이루어지는 결과가 나온다.

![KakaoTalk_Photo_2023-10-24-21-47-27](https://github.com/happysubin/book-study/assets/76802855/7037eb73-c352-417b-b979-1f2a1de9d851)

* 윗 부분은 순차적으로 평가를 진행하는 단일 파이프라인 스트림 처리 과정을 보여준다. 
* 즉, 이전 요청의 처리가 완전히 끝난 다음에 새로 만든 CompletableFuture가 처리된다.
* 반면 아래쪽은 우선 CompletableFuture를 리스트로 모은 다음에 다른 작업과는 독립적으로 각자의 작업을 수행하는 모습을 보여준다.

결과는 순차적인, 블록 방식의 구현 보다는 빠르다. 그러나 병렬 스트림을 사용한 구현보다는 두 배 느리다.

### 더 확장성이 좋은 해결 방법

* 병렬 스트림 버전의 코드는 정확히 네 개의 상점에 하나의 스레드를 할당해서 네 개의 작업을 병렬로 수행하면서 검색 시간을 최소화할 수 있었다.
* 만약에 5개의 상점으로 늘어나면 어떨까?
* 보통 스레드풀에서 제공하는 기본 스레드가 4개이다. 따라서 다섯 번째 상점을 처리하는데 추가로 1초 이상이 소요된다.
* 즉 네 개의 스레드 중 누군가가 작업을 완료해야 다섯 번째 질의를 수행할 수 있다.

CompletableFuture 버전에서는 어떤 일이 일어날까?

* CompletableFuture 버전이 병렬 스트림 버전보다 아주 조금 빠르다.
* 예를 들어 9개의 상점이 있다고 가정하면 병렬 스트림 버전은 3143 밀리초, CompletableFuture 버전은 3009 밀리초가 소요된다.
* 두 가지 버전 모두 내부적으로 Runtime.getRuntime().availableProcessoprs()가 반환하는 스레드 수를 사용하면서 비슷한 결과가 된다.
* 결과적으로는 비슷하지만 CompletableFuture는 병렬 스트림 버전에 비해 작업에 이용할 수 있는 다양한 Executor를 지정할 수 있다는 장점이 있다.
* 따라서 Executor로 스레드 풀의 크기를 조절하는 등 애플리케이션에 맞는 최적화된 설정을 만들 수 있다.

### 커스텀 Executor 사용하기

아래는 적절한 스레드 풀의 스레드 갯수를 찾는 공식이다. W/C는 대기시간과 계산 시간의 비율이다.

> 스레드 수  = 코어 갯수 * CPU 활용 비율 * (1 + W/C)

__애플리케이션의 특성에 맞는 Executor를 만들어 CompletableFuture를 활용하는 것이 바람직하다.

작업이 I/O를 기다리는 작업을 병렬로 실행할 때는 CompletableFuture가 더 많은 유연성을 제공하며 대기/계산 (W/C) 비율에 적합한 스레드 수를 설정할 수 있다.
특히 스트림의 게으른 특성 때문에 스트림에서 I/O를 실제로 언제 처리할지 예측하기 어려운 문제도 있다. 

<br>

## section 16.4 비동기 작업 파이프라인 만들기

```
public List<String> findPricesV4(String product) {
        return shops.stream()
                .map(shop -> shop.getPrice(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(Collectors.toList());
    }
```

세 개의 map 연산을 상점 스트림에 파이프라인으로 연결해서 원하는 결과를 얻었다.

* 첫 번째 연산에서는 각 상점을 요청한 제품의 가격과 할인 코드로 변환한다.
* 두 번째 연산에서는 이들 문자열을 파싱해서 Quote 객체를 만든다.
* 세 번째 연산에서는 원격 Quote 서비스에 접근해서 최종 할인 가격을 계산하고 가격에 대응하는 상점 이름을 포함하는 문자열을 반환한다.

이 구현은 성능 최적화와는 거리가 멀다. 

역시 최선의 방범은 CompletableFuture에서 수행하는 태스크를 설정할 수 있는 커스텀 Executor를 정의함으로써 우리의 CPU 사용률을 극대화시키는 것이다.

### 동기 작업과 비동기 작업 조합하

```
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
```

#### 가격 정보 얻기

* 팩토리 메서드 supplyAsync에 람다 표현식을 전달해서 비동기적으로 상점에서 정보를 조회했다.
* 첫 번째 변환의 결과는 Stream<CompletableFuture<String>> 이다.
* 각 CompletableFuture는 작업이 끝났을 때 해당 상점에서 반환하는 문자열 정보를 포함한다.
* 그리고 이전에 개발한 커스텀 Executor로 CompletableFuture를 설정한다.

#### Quote 파싱하기

* 파싱 동작은 원격 서비스 I/O가 없으므로 원하는 즉시 지연 없이 동작ㅇ르 수행할 수 있다.
* 따라서 첫 번째 과정에서 생성된 CompletableFuture에 thenApply를 호출한 다음에 문자열을 Quote 인스턴스로 변환하는 Function으로 전달한다.
* thenApply 메서드는 CompletableFuture가 끝날 때까지 블록하지 않는다는 점을 주의해야 한다.
* 즉, CompletableFuture가 동작을 완료한 다음에 thenApply 메서드로 전달된 람다 표현식을 적용할 수 있다.
* 따라서 CompletableFuture<String>을 CompletableFuture<Quote>로 변환할 것이다.

#### CompletableFuture를 조합해서 할인된 가격 계산하기

* 이번에는 원격 실행이 포함되므로 이전 두 변환과 다르며 동기적으로 작업을 수행해야 한다. (1초 지연으로 원격 실행을 흉내 낸다.)
* 람다 표현식으로 이 동작을 팩토리 메서드 supplyAsync에 전달할 수 있다.
* 그러면 다른 CompletableFuture가 반환된다.
* 결국 두 가지 CompletableFuture로 이루어진 연쇄적으로 수행되는 두 개의 비동기 동작을 만들 수 있다.

1. 상점에서 가격 정보를 얻어와서 Quote로 변환하기
2. 변환된 Quote를 Discount 서비스로 전달해서 할인된 최종가격 획득하기

자바 8의 CompletableFuture API는 이와 같이 두 비동기 연산을 파이프라인으로 만들 수 있도록 thenCompose 메서드를 제공한다.
thenCompose 메서드는 첫 번째 연산의 결과를 두 번째 연산으로 전달한다.

마지막으로 CompletableFuture가 완료되기를 기다렸다가 join으로 값을 추출할 수 있다.

* CompletableFuture 클래스의 다른 메서드처럼 위 코드에서 사용한 thenCompose 메서드도 Async로 끝나는 버전이 존재한다.
* Async로 끝나지 않는 메서드는 이전 작업을 수행한 스레드와 같은 스레드에서 작업을 실행함을 의미하며 Async로 끝나는 메서드는 다음 작업이 다으른 스레드에서 실행되도록 스레드 풀로 작업을 제출한다.
* 여기서 두 번째 CompletableFuture의 결과는 첫 번째 CompletableFuture에 의존하므로 두 CompletableFuture를 하나의 조합하든 Async 버전의 메서드를 사용하든 최종 결과나 개괄적인 실행 시간에는 영향을 미치지 않는다.
* 따라서 스레드 전환 오버헤드가 적게 발생하면서 효율성이 좀 더 좋은 thenCompose를 활용했다.

### 독립 CompletableFuture와 비독립 CompletableFuture 합치기

* 실전에서는 독립적으로 실행된 두 개의 CompletableFuture 결과를 합쳐야하는 상황이 종종 발생한다.
* 물론 첫 번째 CompletableFuture의 동작 완료와 관계 없이 두 번째 CompletableFuture를 실행할 수 있어야 한다.

이런 상황에서는 thenCombine 메서드를 사용한다. thenCombine는 BiFunction을 두 번째 인수로 받는다.

* BiFunction은 두 개의 CompletableFuture 결과를 어떻게 합칠지 정의한다.
* thenCompose와 마찬가지로 thenCombine 메서드에더 Async 버전이 존재한다.
* thenCombineAsync 메서드에서는 BiFunction이 정의하는 조합 동작이 스레드 풀로 제출되면서 별도의 태스크에서 비동기적으로 수행된다.

두 CompletableFuture의 결과가 생성되고 BiFunction으로 합쳐진 다음에 세 번째 CompletableFuture를 얻을 수 있다.

예제는 단순 곱셉이므로 thenCombine 메서드를 활용했다.

너무 많은 시간이 흘러서 timeout 시키는 코드를 예제에 추가했다.

<br>

## section 16.5 CompletableFuture의 종료에 대응하는 방법

* CompletableFuture에 등록된 동작은 CompletableFuture에 동작을 등록한다. (stream의 경우)
* 자바 8의 CompletableFuture API는 thenAccept라는 메서드로 이 기능을 제공한다.
* theynAcceptAsync는 활용하지 않는 편이 대부분의 경우에 좋다.잏
* 이후 배열로 만들어 join 메서드를 실행해서 실행 완료를 기다리고,, CompletableFuture의 값으로 동작을 완료했다.

<br>

## section 16.7 마치며

* 한 개 이상의 원격 외부 서비스를 사용하는 긴 동작을 실행할 때는 비동기 방식으로 애플리케이션의 성능과 반응성을 향상시킬 수 있다.
* 우리 고객에게 비동기 API를 제공하는 것을 고려해야 한다. CompletableFuture의 기능을 이용하면 쉽게 비동기 API를 구현할 수 있다.
* CompletableFuture를 이용할 때 비동기 태스크에서 발생한 에러를 관리하고 전달할 수 있다.
* 동기 API를 CompletableFuture로 감싸서 비동기적으로 소비할 수 있다.
* 서로 독립적인 비동기 동작이든 아니면 하나의 비동기 동작이 다른 비동기 동작의 결과에 의존하는 상황이든 여러 비동기 동작을 조립하고 조합할 수 있다.
* CompletableFuture에 콜백을 등록해서 Future가 동작을 끝내고 결과를 생산했을 때 어떤 코드를 실행하도록 지정할 수 있다.
* CompletableFuture 리스트의 모든 값이 완료될 때까지 기다릴지 아니면 첫 값만 완료되길 기다릴지 선택할 수 있다.
* 자바 9에서는 orTimeOut, completeOnTimeOut 메서드로 CompletableFuture에 비동기 타임아웃 기능을 추가했다.