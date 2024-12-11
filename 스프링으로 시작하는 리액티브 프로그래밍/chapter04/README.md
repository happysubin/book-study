
# chapter 04. 리액티브 프로그래밍을 위한 사전 지식

### 함수형 인터페이스

인터페이스인데 추상 메서드가 1개라면 그것은 함수형 인터페이스

### 람다 표현식

* 자바에서도 인터페이스의 익명 구현 클래스를 화살표 함수처럼 간결하게 표현하는 방법을 지원하는데 그것이 바로 람다 표현식
* 람다 표현식은 함수형 인터페이스를 구현한 클래스의 메서드 구현을 단순화한 표현식.
* 함수형 인터페이스를 구현한 클래스의 인스턴스를 람다 표현식으로 작성해서 전달한다

### 메서드 레퍼런스

아래는 제일 간단한 메서드 레퍼런스 예시다.

```
(Car car) -> car.getCarMame() == Car::getCarName 
```

메서드 레퍼런스로 표현할 수 있는 유형은 네가지다.

1. ClassName :: static Method 유형 

```
.map(name -> StringUtils.upperCase(name))
.map(StringUtils::upperCase)
```

2. ClassName :: instance method 유형

```
.map(name -> name.toUpperCase())
.map(String::toUpperCase)
```

3. object :: instance method 유형

```

Calculator calculator = new Caculator();

.. 생략

.map(pair -> calculator.getTotalPayment(pair))
.map(calculator::getTotalPayment)
```

4. ClassName :: new 유형

```
.map(pair -> new PaymentCalculator(pair))
.map(PaymentCalculator::new)
```

### 함수 디스크립터

함수 디스크립터는 함수 서술자, 함수 설명자 정도로 이해할 수 있다.
실제로는 일반화된 람다 표현식을 통해서 이 함수형 인터페이스가 어떤 파라미터를 가지고,
어떤 값을 리턴하는지 설명해주는 역할을 한다.

| 함수형인터페이스            | 함수 디스크립터          |
|---------------------|-------------------|
| Predicate<T>        | T -> boolean      |
| Consumer<T>         | T -> void         |
| Function<T, R>      | T -> R            |
| Supplier<T>         | () -> T           |
| BiPredicate<L, R>   | (L, R) -> boolean |
| BiConsumer<T, U>    | (T, U) -> void    |
| BiFunction<T, U, R> | (T, U) -> R       |

#### Predicate 

* 해당 코드를 흔하게 볼수있는 것은 스트림의 filter 메서드
* filter 메서드는 파라미터로 Predicate을 가지며 filter 메서드 내부에서 이 Predicate를 활용해 리턴값이 true인 데이터만 필터링

#### Consumer

* 데이터를 소비하는 용도로 사용
* 배치 처리가 대표적인 예시

#### Function

* 함수 내에서 어떤 처리 과정을 거친후에 그 결과로 특정 타입의 값을 반환하는 전형적인 함수 역할
* 대표적인게 자바 스트림의 map 메서드

#### Supplier

* 데이터를 제공하는 용도로 자주 사용함

#### Bixxxxxx

* Bi로 시작하는 함수형 인터페이스는 함수형 인터페이스에서 구현해야 하는 추상 메서드에 전달하는 파라미터가 하나 더 추가되어 두 개의 파라미터를 가지는 확장형 함수형 인터페이스
