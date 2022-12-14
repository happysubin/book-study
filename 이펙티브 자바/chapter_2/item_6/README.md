# chapter 2. 객체 생성과 파괴 

## item 6. 불필요한 객체 생성을 피하라

* 똑같은 기능의 객체를 매번 생성하기보다는 객체 하나를 재사용하는 편이 나을 때가 많다.
* 재사용은 빠르고 세련되다. 특히 불변 객체는 언제든 재사용할 수 있다.
* 아래 코드는 절대 하지 말아야 할 극단적인 예시다.

```
String s = new String("java");
```

* 위 문장은 실행될 때마다 String 인스턴스를 새로 만든다. 정말 쓸데없는 행위다.
* 개선된 버전은 아래와 같다.

```
String s = "java";
```

* 이 코드는 새로운 인스턴스를 매번 만드는 대신 하나의 String 인스턴스를 사용한다.
* 나아가 이 방식을 사용한다면 같은 가상머신 안에서 이와 똑같은 문자열 리터럴을 사용하는 모든 코드가 같은 객체를 사용함이 보장된다. (String pool 얘기인 듯)
* 생성자 대신 정적 팩터리 메서드를 제공하는 불변 클래스에서는 정적 팩터리 메서드를 사용해 불필요한 객체 생성을 피할 수 있다.
* 예컨대 Boolean 생성자 대신 Boolean.valueOf 팩터리 메서드를 사용하는 것이 좋다.
* 생성자는 호출될 때마다 새로운 객체를 만들지만, 팩터리 메서드는 전혀 그렇지 않다.
* 불변 객체만이 아니라 가변 객체라 해도 사용 중에 되지 않을 것임을 안다면 재사용할 수 있다.
* 생성 비용이 아주 비싼 객체도 있다. 이런 비싼 객체가 반복해서 필요하다면 캐싱하여 사용하는 것이 좋다.

#### String.matches 예시 코드

* 예시 코드에서는 String.matches 메서드를 사용.
* String.matches는 정규 표현식으로 문자열 형태를 확인하는 가장 쉬운 방법이지만, 성능이 중요한 상황에서 반복해 사용하기엔 적합하지 않다.
* 이 메서드가 내부에서 만드는 정규표현식용 Pattern 인스턴스는, 한 번 쓰고 버려져서 곧바로 가비지 컬렉션 대상이 된다.
* Pattern은 입력받은 정규표현식에 해당하는 유한 상태 머신을 만들기 때문에 인스턴스 생성 비용이 높다.
* 성능을 개선하려면 필요한 정규표현식을 표현하는 불변인 Pattern 인스턴스를 클래스 초기화 과정에서 직접 생성해 캐싱해두고, 나중에 isRomanNumeral 메서드가 호출될 때마다 이 인스턴스를 재사용한다.
* 예시는 결국 값비싼 객체를 재사용해 성능을 개선한 것이다.

## 정리

* 이번 단원을 '객체 생성은 비싸니 피해야 한다'로 오해하면 안된다.
* 특히 요즘 JVM에서는 별다른 일을 하지 않는 작은 객체를 생성하고 회수하는 일이 크게 부담되지는 않는다.
* 프로그램의 명확성, 간결성, 기능을 위해서 객체를 추가로 생성하는 것이라면 일반적으로 좋은 일이다.
* 거꾸로, 아주 무거운 객체가 아닌 다음에야 단순히 객체 생성을 피하고자 우리만의 객체 풀을 만들지는 말자.
* 데이터베이스 연결 같으 경우에는 풀을 사용하는 것이 좋다.
* 하지만 일반적으로는 자체 객체 풀은 코드를 헷갈리게 만들고 메모리 사용량을 늘리고 성능을 떨어뜨린다.
* 요즘 JVM의 가비지 컬렉터는 상당히 잘 최적화되어서 가벼운 객체용을 다룰 때는 직접 만든 객체 풀보다 훨씬 빠르다.
* 이번 아이템은 아이템 50에서 나오는 방어적 복사와 대조적이다.
* 방어적 복사란 '새로운 객체를 만들어야 한다면 기존 객체를 재사용하지 마라'다.
* 방어적 복사가 필요한 상황에서 객체를 재사용했을 때의 피해가 필요 없는 객체를 반복 생성했을 때의 피해보다 훨씬 크다는 사실을 기억하자.
* 방어적 복사에 실패하면 언제 터져 나올지 모르는 버그와 보안 구멍으로 이어지지만, 불필요한 객체 생성은 그저 코드 형태와 성능에만 영향을 준다.

참고 

* 박싱된 기본 타입보다는 기본 타입을 사용하고, 의도치 않은 오토박싱이 숨어들지 않도록 주의하자.
* 오토박싱은 기본 타입과 그에 대응하는 박싱된 기본 타입의 구분을 흐려주지만, 완전히 없애는 것은 아니다.