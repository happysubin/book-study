# chapter 7. 적응시키기(어댑터 패턴과 퍼사드 패턴)

## 객체지향 어댑터 알아보기

* 어떤 소프트웨어 시스템에 새로운 업체에서 제공한 클래스 라이브러리르 사용해야 하는데 그 업체에서 사용하는 인터페이스가 기존에 사용하던 인터페이스와 다르다고 가정하자.
* 그런데 기존 코드를 바꿔서 이 문제를 해결할 수 없다. 게다가 업체에서 공급받은 클래스도 변경할 수 없다.
* 새로운 업체에서 사용하는 인터페이스를 기존에 사용하던 인터페이스에 적응시켜 주는 클래스를 만들면 된다.
* 어댑터는 클라이언트로부터 요청을 받아서 새로운 업체에서 제공하는 클래스를 클라이언트가 받아들일 수 있는 형태의 요청으로 변환해 주는 중개인 역할을 한다.

## 어댑터 패턴 알아보기

* 클라이언트가 클라이언트는 타깃 인터페이스에 맞게 구현되어 있다.
* 어댑터는 타깃 인터페이스를 구현하며, 여기에는 어댑터 인스턴스가 들어있습니다.
* 예시에서는 Turkey 객체가 어댑터 인터페이스며, TurkeyAdapter에서는 타깃 인터페이스만 Duck을 구현했다.

## 클라이언트에서 어댑터를 사용하는 방법

* 클라이언트에서 타깃 인터페이스로 메서드를 호출해서 어댑터에 요청을 보낸다.
* 어댑터는 어댑터 인터페이스로 그 요청을 어댑터에 관한(하나 이상의) 메서드 호출로 변환한다.
* 클라이언트는 호출 결과를 받긴 하지만 중간에 어댑터가 있다는 사실을 모른다.

## 어댑터 패턴 정의

어댑터 패턴은 특정 클래스 인터페이스를 클라이언트에서 요구하는 다른 인터페이스로 변환한다. 인터페이스가 호환되지 않아 같이 쓸 수 없었던 클래스를 사용할 수 있다.

* 이 패턴을 사용하면 호환되지 않는 인터페이스를 사용하는 클라이언트를 그대로 활용할 수 있다.
* 이러면 클라이언트와 구현된 인터페이스를 분리할 수 있으며, 변경 내역이 어댑터에 캡슐화되기에 나중에 인터페이스가 바뀌더라도 클라이언트를 바꿀 필요가 없다.
* 어댑터 패턴은 클라이언트 객체가 있고, 타깃 인터페이스를 어댑터가 구현하며 어댑터에는 호출할 객체(타깃 인터페이스 구현체)가 있다.
* 일반적으로 어댑터 패턴은 하나의 인터페이스를 다른 인터페이스로 전환하는 용도로 쓰인다.

## 퍼사드 패턴 살펴보기

* 퍼사드 클래스는 서브 시스템 클래스를 캡슐화하지 않는다.
* 서브시스템의 기능을 사용할 수 있는 간단한 인터페이스를 제공한다.
* 어댑터는 하나 이상의 클래스 인ㅌ퍼ㅔ이스를 클라이언트에서 필요로 하는 인터페이스로 변환한다.
* 퍼사드 패턴도 꼭 여러 클래스를 감싸는 것은 아니다. 아주 복잡한 인터페이스를 가지고 있는 단 하나의 클래스에 대한 퍼사드를 만들수도 있다.
* 어댑터 패턴은 인터페이스를 변경해서 클라이언트에서 필요로 하는 인터페이스로 적응시키는 용도로 쓰인다.
* 반면 퍼사드 패턴은 어떤 서브시스템에 대한 간단한 인터페이스를 제공하는 용도로 쓰인다.

## 퍼사드 패턴 정의

퍼사드 패턴은 서브시스템이 있는 일련의 인터페이스를 통합 인터페이스로 묶어 준다. 또한 고수준 인터페이스도 정의하므로 서브시스템을 더 편리하게 사용할 수 있다.

* 퍼사드 패턴을 사용하려면 어떤 서브시스템에 속한 일련의 복잡한 클래스를 단순하게 바꿔서 통합한 클래스를 만들어야 한다.
* 다른패턴과 달리 퍼사드 패턴은 상당히 단순하다. 복잡한 추상화가 없다. 

제일 중요한 점은 패턴의 용도다. __퍼사드 패턴은 단순화된 인터페이스로 서브시스템을 더 편리하게 사용하려고 쓰인다.__


### 최소 지식 원칙

* 최소 지식 원칙에 따르면 객체 사이의 상호 작용은 될 수 있으면 아주 가까운 친구 사이에서만 허용하는 편이 좋다.
* 의존성을 줄이라는 얘기인 것 같다.
* 정말 협력을 해야하는 객체에게만 메시지를 전송해야 한다.
* 게터로 하나하나 꺼내와서 코드를 작성하면 안된다.

## 디자인 원칙
__진짜 절친에게만 이야기해야 한다.__


