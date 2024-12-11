
# chapter 05. Reactor 개요

### 5.1 Reactor란?

Reactor는 스프링 프레임워크 팀의 주도하에 개발된 리액티브 스트림즈의 구현체다.
스프링 프레임워크 5버전부터 리액티브 스택에 포함돼 WebFlux 기반의 리액티브 애플리케이션을 제작하기 위한 핵심 역할을 담당한다.
Reactor는 리액티브 프로그래밍을 위한 라이브러라고 정의할 수 있다.

### 5.2 Hello Reactor 코드로 보는 Reactor의 구성요소

```java
public class Example5_1 {
    public static void main(String[] args) {
        Flux<String> sequence = Flux.just("Hello", "Reactor");
        sequence.map(data -> data.toLowerCase())
                .subscribe(data -> System.out.println(data));
    }
}
```
* 3번 라인에서 just 메서드의 파라미터로 들어오는 문자열들이 Publisher가 최초로 제공하는 가공되지 않은 데이터로 __데이터 소스__ 라고 불린다.
* 데이터 소스의 타입이 1개 이상이므로 Flux 타입을 활용했다.
* 5번 라인의 subscribe 메서드의 파라미터로 전달된 람다 표현식이 바로 Subscriber 역할을 한다.
* 3번 라인의 just(), 4번 라인의 map() 은 Reactor에서 제공하는 Operator 메서드인데, just는 데이터를 생성해서 제공하고, map은 전달받은 데이터를 가공하는 역할을 한다.
* 또한 Operator인 just와 map의 리턴값이 Flux인 것을 알 수 있다. 이를 통해 Operator 체인이 가능한 것이다.

데이터를 생성해서 제공하고, 데이터를 가공한 후에 전달받은 데이터를 처리한다는 이 3단계가 중요하다.

위 3단계는 데이터를 가공하는 단계가 얼마나 복잡해지느냐, 스레드를 어떤 식으로 제어하느냐 등의 추가 작업과 상관없이 수행되는 필수 단계다.