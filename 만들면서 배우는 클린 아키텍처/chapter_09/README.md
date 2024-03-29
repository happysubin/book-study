# chapter 9. 애플리케이션 조립하기

* 애플리케이션이 시작될 때 클래스를 인스턴스화하고 묶기 위해서 의존성 주입 메커니즘을 이용한다.
* 평범한 자바, 스프링, 스프링 부트 각각에서 의존성 주입 메커니즘을 살펴봄.

## 왜 조립까지 신경 써야 할까?

* 유스케이스와 어댑터를 그냥 필요할 때 인스턴스화를 시키면 안되는 이유는 의존성이 올바른 방향을 가리키게 하기 위해서다.
* 모든 의존성은 안쪽으로, 애플리케이션의 도메인 코드 방향으로 향해야 도메인 코드가 바깥 계층의 변경으로부터 안전하다.
* 유스케이스가 영속성 어댑터를 호출해야 하고 스스로 인스턴스화한다면 코드 의존성이 잘못된 방향으로 만들어진 것이다.
* 이것이 바로 아웃고잉 포트 인터페이스를 생성한 이유다.
* 유스케이스는 인터페이스만 알아야 하고, 이 인터페이스의 구현을 제공 받아야 한다.
* 이 프로그래밍 스타일의 유익한 부수 효과 중 하나는 코드를 훨씬 더 테스트하기 쉽다는 것이다.
* 한 클래스가 필요로 하는 모든 객체를 생성자로 전달할 수 있다면 실제 객체 대신 mock로 전달할 수 있고, 이러면 격리된 단위 테스트를 작성하기 쉬워진다.
* 우리 객체 인스턴스를 생성할 책임은 바로 아키텍처에 대해 중립적이고 인스턴스 생성을 위해 모든 클래스에 대한 의존성을 가지는 설정 컴포넌트에게 있다.
* 설정 컴포넌트는 우리가 제공한 조각들로 애플리케이션을 조립하는 것을 책임진다.

이 컴포넌트는 아래와 같은 역할을 수행한다.

* 웹 어댑터 인스턴스 생성
* HTTP 요청이 실제로 웹 어댑터로 전달되도록 보장
* 유스케이스 인스턴스 생성
* 웹 어댑터에 유스케이스 인스턴스 제공
* 영속성 어댑터 인스턴스 생성
* 유스케이스에 영속성 어댑터 인스턴스 제공
* 영속성 어댑터가 실제로 데이터베이스에 접근할 수 있도록 보장

더불어 설정 컴포넌트는 설정 파일이나 커맨드라인 파라미터 등과 같은 설정 파라미터의 소스에도 접근할 수 있어야 한다.
SRP를 위반하지만 애플리케이션의 나머지 부분을 깔끔하게 유지하고 싶다면 이처럼 구성요소들을 연결하는 바깥쪽 컴포넌트가 필요하다.
그리고 이 컴포넌트는, 작동하는 애플리케이션으로 조립하기 위해 애플리케이션을 구성하는 모든 움직이는 부품을 알아야 한다.


## 평범한 코드로 조립하기

* 순수 자바를 사용한 방식이다.
* Application 클래스의 main 메서드에서 실행하면서 애플리케이션을 조립한다. 즉 main 메서드에서 필요한 모든 인스턴스를 생성해 연결한다.
* 너무 복잡하고 단점이 많다.

## 스프링의 클래스패스 스캐닝으로 조립하기

* 스프링 프레임워크를 이용해서 애플리케이션을 조립한 결과물을 애플리케이션 컨텍스트라고 한다.
* 애플리케이션 컨텍스트는 애플리케이션을 구성하는 모든 객체, 빈을 포함한다.
* 클래스 패스 스캐닝 방식을 살펴보았다.
* 스프링은 클래스패스 스캐닝으로 클래스패스에서 접근 가능한 모든 클래스를 확인해서 @Component 애너테이션이 붙은 클래스를 찾는다.
* 그리고 나서 이 애너테이션이 붙은 각 클래스의 객체를 생성한다.
* 이때 클래스는 필요한 모든 필드를 인자로 받는 생성자를 가지고 있어야 한다.
* 스프링은 이 생성자를 찾아서 생성자의 인자로 사용된 @Component가 붙은 클래스를 찾고, 이 클래스들의 인스턴스를 만들어 애플리케이션 컨텍스트에 추가한다.
* 생성된 객체도 마찬가지로 애플리케이션 컨텍스트에 추가된다.
* 클래스패스 스캐닝 방식을 이용하면 아주 편리하게 애플리케이션을 조립할 수 있다.ㄹ

물론 이 방식에도 단점이 있다.

* 클래스에 프레임워크에 특화된 애너테이션을 붙여야 한다.
* 다른 단점은 스프링 전문가가 아니라면 원인을 찾는데 수일이 걸릴 수 있는 부수 효과가 발생할 수 있다.
* 보통 이런 마법 같은 일이 발생하는 이유는 클래스패스 스캐닝이 애플리케이션 조립에 사용하기는 너무 둔한 도구이기 때문이다.
* 단순히 스프링에게 부모의 패키지를 알려준 후 이 패키지 안에서 @Component가 붙은 클래스를 찾으라고 지시한다.

## 스프링의 자바 컨피그로 조립하기

* @Configuration 애너테이션을 활용한 기법이다. 마찬가지로 이 애노테이션도 클래스 패스 스캐닝에 의해서 발견된다.
* 애플리케이션 컨텍스트에 추가할 빈을 생성하는 설정 클래스를 만드는 기법.
* 빈 자체는 설정 클래스 내의 @Bean 애너테이션이 붙은 팩터리 메서드를 통해 생성된다.

물론 문제점도 존재한다.

* 설정 클래스가 생성하는 빈이 설정 클래스와 같은 패키지에 존재하지 않는다면 이 빈들을 public으로 만들어야 한다.
* 가시성을 제한하기 위해 패키지를 모듈 경계로 사용하고 각 패키지 안에 전용 설정 클래스를 만들 수는 있다. 대신 이러면 하위 패키지를 사용할 수 없다고 한다.
* 이는 10장에서 더 살펴본다.

### 스프링과 스프링 부트는 애플리케이션 조합을 정말 간편하게 해준다. 코드의 규모가 커지면 단점도 존재한다.