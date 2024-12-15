
# chapter 09. Sinks

### 9.1 Sinks란?

Sinks는 리액티브 스트림즈의 Signal을 프로그래밍 방식으로 푸시할 수 있는 구조이며 Flux 또는 Mono의 의미 체계를 가진다.

지금까지 배운 방식은 모두 Flux 또는 Mono가 onNext 같은 Signal을 내부적으로 전송해주는 방식이었는데, Sinks를 사용하면 프로그래밍 코드를 통해 
명시적으로 Signal을 전송할 수 있다.

그런데 Reactor에서 프로그램이 방식으로 Signal을 전송하는 가장 일반적인 방법은 generate() or create() Operator 등을 사용하는 것인데,
이는 Reactor에서 Sinks를 지원하기 전부터 이미 사용하던 방식이다.

그렇다면 Sinks를 사용하는 것과 Operator를 사용하는 전통적인 방식에는 어떤 차이점이 있을까?

일반적으로 generate(), create() Operator는 싱글 스레드 기반으로 Signal을 전송하는 데 사용하는 반면,
Sinks는 멀티 스레드 방식으로 Signal을 전송해도 스레드 안전성을 보장하기 때문에 예기치 않은 동작으로 이어지는 것을 방지해준다.

Example9_1 코드와 Example9_2 코드를 참고

### 9.2 Sinks 종류 및 특징

Reactor에서 Sinks를 사용하여 프로그래밍 방식으로 Signal을 전송할 수 있는 방법은 크게 두 가지입니다.
첫째는 Sinks.One을 사용하는 것이고, 둘째는 Sinks.Many를 사용하는 것이다.

#### Sinks.One

* Sinks.One은 Sinks.one() 메서드를 사용해서 한 건의 데이터를전송하는 방법을 정의해 둔 기능 명세다.
* Sinks.One은 한 건의 데이터를 프로그래밍 방식으로 emit하는 역할을 하기도 하고, Mono 방식으로 Subscriber가 데이터를 소비할 수 있도록 해주는 Sinks 클래스 내부에 인터페이스로 정의된 Sinks의 스펙 또는 사양으로 볼 수 있다.
* 즉, Sinks.one() 메서드를 호출하는 것은 한 건의 데이터를 프로그래밍 방식으로 emit하는 기능을 사용하고 싶으니 거기에 맞는 적당한 기능 명세를 달라고 요청하는 것과 같다.

#### Sinks.Many

* Sinks.Many는 Sinks.many() 메서드를 사용해서 여러 건의 데이터를 여러 가지 방식으로 전송하는 기능을 정의해 둔 기능 명세라고 볼 수 있다.
* Sinks.Many는 Sinks.Many를 리턴하지 않고 ManySpec이라는 인터페이스를 리턴한다.