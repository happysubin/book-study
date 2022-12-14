# chapter 4. 클래스와 인터페이스

## item 15. 클래스와 멤버의 접근 권한을 최소화하라.

잘 설계된 컴포넌트는 모든 내부 구현을 완벽히 숨겨, 구현과 API를 깔끔히 분리한다.
오직 API를 통해서만 다른 컴포넌트와 소통하며 서로의 내부 동작 방식에는 전혀 개의치 않는다. 
정보 은닉, 캡슐화라고 하는 이 개념은 소프트웨어 설계의 근간이 되는 원리다.
 
정보 은닉의 장점은 아래와 같다.

* 시스템 개발 속도를 높인다. 여러 컴포넌트를 병렬로 개발할 수 있기 때문이다.
* 시스템 관리 비용을 낮춘다. 각 컴포넌트를 더 빨리 파악하여 디버깅할 수 있고, 다른 컴포넌트로 교체하는 부담도 적기 때문이다.
* 정보 은닉 자체가 성능을 높여주지는 않지만, 성능 최적화에 도움을 준다.
* 소프트웨어 재사용성을 높인다.
* 큰 시스템을 제작하는 난이도를 낮춘다.

자바는 정보 은닉을 위해 다양한 장치를 제공한다. 
그중 접근 제어 메커니즘은 클래스, 인터페이스, 멤버의 접근성을 명시한다.
각 요소의 접근성은 그 요소가 선언된 위치와 접근 제한자로 정해진다. 이 접근 제한자를 제대로 활용하는 것이 정보 은닉의 핵심이다.
 
기본 원칙은 __모든 클래스와 멤버의 접근성을 가능한 한 좁혀야 한다__ 이다.
소프트웨어가 올바로 동작하는 한 항상 가장 낮은 접근 수준을 부여해야 한다.

* 클래스의 공개 API를 세심히 설계한 후, 그 외의 모든 멤버는 private으로 만들자.
* 그런 다음 패키지의 다른 클래스가 접근해야 하는 멤버에 한하여 package-private, 즉 default로 풀어주자. (사실 이 부분은 딱히 안와닿는다. 아직은)
* 권한을 풀어주는 일이 발생하면 컴포넌트 분해를 고민해보자.
* 테스트만을 위해 클래스, 인터페이스, 멤버를 공개 API로 만들어서는 안된다.

public 클래스의 인스턴스 필드는 되도록 public이면 안된다.
필드가 가변 객체를 참조하거나, final이 아닌 인스턴스 필드를 public으로 선언하면 그 필드에 담을 수 있는 값을 제한할 힘을 잃게 된다.
그 필드와 관련된 몯느 것ㅇ느 불변식을 보장할 수 없게 된다는 뜻이다.
 
여기에 더해 필드가 수정될 때 (락 획득 같은) 다른 작업을 할 수 없게 되므로 __public 가변 필드를 갖는 클래스는 일반적으로 스레드 안전하지 않다.__
 
__클래스에서 public static final 배열 필드를 두거나 이 필드를 반환하는 접근자 메서드를 제공해서는 안된다.__

* 이런 필드나 접근자를 제공한다면 클라이언트에서 그 배열의 내용을 수정할 수 있게 된다.
* 해결책은 두 가지다. 먼저 public 배열을 private으로 만들고 public 불변 리스트를 추가하는 것이다.
* 두 번째는 private으로 만들고 그 복사본을 반환하는 public 메서드를 추가하는 방법이다.

모듈 개념과 관련된 접근 수준은 아직은 사용하지 않는 것이 좋다.

## 정리

* 프로그램 요소의 접근성은 가능한 한 최소한으로 하라.
* 꼭 필요한 것만 골라 최소한의 public  API를 설계하자.
* 그 외에는 클래스, 인터페이스, 멤버가 의도치 않게 API로 공개되는 일이 없도록 하자
* publid 클래스는 상수용 public static final 필드 외에는 어떠한 public 필드도 가져서는 안된다.
* public static final 필드가 참조하는 객체가 불변인지 확인하자.
