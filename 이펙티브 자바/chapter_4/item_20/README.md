# chapter 4. 클래스와 인터페이스

## item 20. 추상 클래스보다는 인터페이스를 우선하라

* 자바8 부터 인터페이스에 디폴트 메서드를 사용할 수 있게 되면서, 추상 클래스와 인터페이스 모두 인서튼서 메서드를 구현 형태로 제공할 수 있다.
* 둘의 가장 큰 차이점은 추상 클래스가 정의한 타입을 구현하는클래스는 반드시 추상 클래스의 하위클래스가 되어야 한다는 점이다.
* 자바는 단일 상속만 지원하니, 추상 클래스 방식은 세로운 타입을 정의하는데 커다란 제약을 안게 된다.
* 인터페이스가 선언한 메서드를 모두 정의하고 그 일반 규약을 잘 지킨 클래스라면 다른 어떤 클래스를 상속했든 같은 타입으로 취급된다.


### 인터페이스의 장점

1. 기존 클래스에도 손쉽게 새로운 인터페이스를 구현해 넣을 수 있다.
2. 인터페이스는 믹스인 정의에 안성 맞춤이다. (오브젝트에서는 그래도 지양하라고 본 것 같다.)
3. 인터페이스로는 계층 구조가 없는 타임 프레임워크를 만들 수 있다. 유연성이 확보된다.
4. 래퍼 클래스와 함께 사용하면 인터페이스는 기능을 향상시키는 안전하고 강력한 수단이 된다. (조합을 얘기하는 듯.)

인터페이스와 추상 골격 구현 클래스를 함께 제공하는 식으로 인터페이스와 추상클래스의 장점을 모두 취하는 방법도 있다. 이 내용 또한 오브젝트에서 이미 보았었다.

* 인터페이스로는 타입을 정의하고, 필요하면 디폴트 메서드 몇 개도 함께 제공한다.
* 그리고 골격 구현 클래스는 나머지 메서드들까지 구현한다.
* 이렇게 해두면 단순히 골격 구현을 확장하는 것만으로 이 인터페이스를 구현하는 데 필요한 일이 대부분 완료된다. 바로 템플릿 메서드 패턴이다.
* 관례상 인터페이스 이름이 interface라면, 골격 구현 클래스의 이름은 AbstractInterface라고 짓는다.
* 골격 구현 클래스의 아름다움은 추상 클래스처럼 구현을 도와주는 동시에, 추상 클래스로 타입을 정의할 때 따라오는 심각한 제약에서는 자유롭다는 점에 있다.

구조상 골격 구현을 확장하지 못하는 처지라면 인터페이스를 직접 구현해야 한다.

* 디폴트 메서드를 이용하고나 골격 구현 클래스를 우회적으로 이용할 수 있다.
* 인터페이스를 구현한클래스에서 해당 골격 구현을 확장한 private 내부 클래스를 정의하고, 각 메서드 호출을 내부 클래스의 인스턴스에 전달하는 것이다.
* 래퍼 클래스와 비슷한 이 방식을 시뮬레이트한 다중 상속이라 한다.


## 정리

* 일반적으로 다중 구현용 타입으로는 인터페이스가 가장 적합하다.
* 복잡한 인터페이스라면 구현하는 수고를 덜어주는 골격 구현을 함께 제공하는 방법을 고려하자.
* 골격 구현은 '가능한 한' 인터페이스의 디폴트 메서드로 제공하여 그 인터페이스를 구현한 모든 곳에서 활용하는 것이 좋다.
* '가능한 한'이라고 말한 이유는 인터페이스에 걸려 있는 구현 상의 제약 때문에 골격 구현을 추상 클래스로 제공하는 경우가 더 흔하기 때문이다.





