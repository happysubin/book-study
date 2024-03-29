# Chapter 5. 목과 테스트 취약성

## Section 1. 목과 스텁 구분

### 테스트 대역 유형

* 테스트 대역은 모든 유형의 비운영용 가짜 의존성을 설명하는 포괄적인 용어다.
* 테스트 대역의 주 용도는 테스트를 편리하게 하는 것이다.
* 테스트 대역에는 더미, 스텁, 스파이, 목, 페이크라는 다섯 가지가 있다. 실제로는 목과 스텁의 두 가지 유형으로 나눌 수 있다.

<img width="579" alt="스크린샷 2023-05-09 오후 8 25 43" src="https://github.com/happysubin/book-study/assets/76802855/0093b6e6-6a85-4bc6-aff5-a40f47f25671">

이 두 유형의 차이점은 다음과 같다.

* 목은 외부로 나가는 상호 작용을 모방하고 검사하는 데 도움이 된다. 이러한 상호 작용은 SUT가 상태를 변경하기 위한 의존성을 호출하는 것에 해당한다.
* 스텁은 내부로 들어오는 상호 작용을 모방하는 데 도움이 된다. 이러한 상호 작용은 SUT가 입력 데이터를 얻기 위한 의존성을 호출하는 것에 해당한다.
* 예를 들어 이메일 발송은 SMTP 서버에 사이드 이펙트를 초래하는 상호 작용, 즉 외부로 나가는 상호 작용이다. 해당 테스트 대역은 목이다.
* 데이터베이스에서 데이터를 검색하는 것은 내부로 들어오는 상호 작용이다. 해당 테스트 대역은 스텁이다.

좀 더 자세하게 5가지 테스트 대역에 대해 정리해보자.

* 스파이
  * 스파이는 목과 같은 역할을 한다. 스파이는 수동으로 작성하는 반면, 목은 목 프레임워크의 도움을 받아 생성된다. 가끔 '직접 작성한 목'이라고 부르기도 한다.

스텁, 더미, 페이크의 차이는 얼마나 똑똑한지에 있다.

* 더미
  * 더미는 널 값이나 가짜 문자열과 같이 단순하고 하드 코딩된 값이다.
  * SUT의 메서드 시그니처를 만족시키기 위해 사용하고 최종 결과를 만드는 데 영향을 주지 않는다.
* 스텁
  * 스텁은 더 정교하다. 시나리오마다 다른 값을 반환하게끔 구성할 수 있도록 필요한 것을 다 갖춘 완전한 의존성이다.
* 페이크
  * 페이크는 대다수의 목적에 부합하는 스텁과 같다. 차이점은 생성에 있다.
  * 페이크는 보통 아직 존재하지 않는 의존성을 대체하고자 구현한다.

목과 스텁의 차이점도 알아두자. __목은 SUT와 관련 의존성 간의 상호 작용을 모방하고 검사하는 반면, 스텁은 모방만 한다.__

### 도구로서의 목과 테스트 대역으로서의 목

목은 사람들이 종종 테스트 대역의 의미로 사용한다고 했지만, 테스트 대역의 일부다. 목이라는 용어는 또 다른 의미가 있다.
목 라이브러리의 클래스도 목으로 참고할 수 있다.

* Mockito.mock()은 도구로서의 목인 데 반해, 해당 클래스의 인스턴스인 mock은 테스트 대역으로서의 목이다.
  
```java
User mock = Mockito.mock(User.class);
```

### 스텁으로 상호 작용을 검증하지 말라

목은 SUT에서 관련 의존성으로 나가는 상호 작용을 모방하고 검사하는 반면, 스텁은 내부로 들어오는 상호 작용만 모방하고 검사하지 않는다.
이 두가지의 차이는 스텁과의 상호 작용을 검증하지 말라는 지침에서 비롯된다. SUT에서 스텁으로의 호출은 SUT가 생성하는 최종 결과가 아니다.
이러한 호출은 최종 결과를 산출하기 위한 수단일 뿐이다. 즉, 스텁은 SUT가 출력을 생성하도록 입력을 제공한다.

> 스텁과의 상호 작용을 검증하는 것은 취약한 테스트를 야기하는 일반적인 안티 패턴이다.

```
@Test
void creating_a_report_v2(){

    IDatabase stub = mock(IDatabase.class);
    given(stub.getNumberOfUsers()).willReturn(10);
    DbController sut = new DbController(stub);
    
    Report report = sut.createReport();
    
    Assertions.assertThat(10).isEqualTo(report.getNumberOfUsers());
    verify(stub, times(1)).getNumberOfUsers();
}
```

위 코드는 스텁으로 상호 작용을 검증하는 예시 코드다. 문제를 살펴보자.

* 먼저 getNumberOfUsers를 호출하는 것은 전혀 결과가 아니다.
* 이는 SUT가 보고서 작성에 필요한 데이터를 수집하는 방법에 대한 내부 구현 세부 사항이다.
* 따라서 이런 호출을 검증하는 것은 테스트 취약성으로 이어질 수 임ㅆ다.
* 결과가 올바르다면 SUT가 최종 결과를 어떻게 생성하지는 중요하지 않다.

최종 결과가 아닌 사항을 검증하는 이런 관행을 __과잉 명세__ 라고 부른다. 과잉 명세는 상호 작용을 검증할 때 가장 흔하게 발생한다.
__스텁과의 상호 작용을 확인하는 것은 쉽게 발견할 수 있는 결함이다.__ 테스트가 스텁과의 상호 작용을 확인해서는 안되기 때문이다.

### 목과 스텁 함께 쓰기

때로는 목과 스텁의 특성을 모두 나타내는 테스트 대역을 만들 필요가 있다. 마찬가지로 목과 관련된 특성은 검증을 진행하고 스텁은 진행하지 않는다.

### 목과 스텁은 명령과 조회에 어떻게 관련돼 있는가?

목과 스텁의 개념은 명령 조회 분리 원칙과 관련이 있다. CQS 원칙에 따르면 모든 메서드는 명령이거나 조회여야 한다.
명령은 사이드 이펙트를 일으키고 어떤 값도 반환하지 않는 메서드다. 사이드 이펙트의 예로는 객체 상태 변경 등이 있다.
조회는 그 반대로 사이드 이펙트가 없고 값을 반환한다.
 
* 명령을 대체하는 테스트 대역은 목이다.
* 마찬가지로 조회를 대체하는 테스트 대역은 스텁이다.


## Section 2. 식별할 수 있는 동작과 구현 세부 사항

4장에서 테스트에 거짓 양성이 있는 주요 이유는 코드의 구현 세부 사항과 결합돼 있기 때문이라는 것을 알았다.
이런 강결합을 피하는 방법은 코드가 생성하는 최종 결과를 검증하고 구현 세부 사항과 테스트를 가능한 한 떨어뜨리는 것 뿐이다.
__즉, 테스트는 '어떻게'가 아니라 '무엇'에 중점을 둬야 한다.

### 식별할 수 있는 동작은 공개 API와 다르다.

모든 제품 코드는 2차원으로 분류할 수 있다.

* 공개 API 또는 비공개 API
* 식별할 수 있는 동작 또는 구현 세부 사항 

각 차원의 범주는 겹치지 않는다.
 
식별할 수 있는 동작과 내부 구현 세부 사항에는 미묘한 차이가 있다. 코드가 시스템의 식별할 수 있는 동작이려면 다음 중 하나를 해야 한다.

* 클라이언트가 목표를 달성하는 데 도움이 되는 연산을 노출하라.
* 클라이언트가 목표를 달성하는데 도움이 되는 상태를 노출하라. (게터를 말하는 것 같다.)

구현 세부 사항은 이 두 가지 중 아무것도 하지 않는다.
코드가 식별할 수 있는 동작인지 여부는 해당 클라이언트가 누구인지, 그리고 해당 클라이언트의 목표가 무엇인지에 달려 있다.
식별할 수 있는 동작이 되려면 코드가 이러한 목표 중 하나에라도 직접적인 관계가 있어야 한다. 

> 이상적으로 시스템의 공개 API는 식별할 수 있는 동작과 일치해야 하며, 모든 구현 세부 사항은 클라이언트 눈에 보이지 않아야 한다.

### 잘 설계된 API와 캡슐화

잘 설계된 API를 유지 보수하는 것은 캡슐화 개념과 관련이 있다. 캡슐화는 불변성 위반이라고도 하는 모순을 방지하는 조치다.
불변성은 항상 참이어야 하는 조건이다. 예를 들어 사용자 이름이 50자를 초과하면 안 된다는 불변성이 있다.
 
불변성 위반으로 구현 세부 사항을 노출하게 된다. 즉, 구현 세부 사항을 노출하면 불변성 위반을 가져온다.

> 장기적으로 코드 베이스 유지 보수에서는 캡슐화가 중요하다. 복잡도 때문이다. 코드베이스가 복잡해질수록 작업하기가 더 어려워지고, 개발 속도가 느려지고, 버그 수가 증가하게 된다.

계속해서 증가하는 코드 복잡도에 대처할 수 있는 방법은 실질적으로 캡슐화 말고는 없다.

* 좋은 단위 테스트와 잘 설계된 API 사이에는 본질적인 관계가 있다.
* 모든 구현 사항을 비공개로 하면 테스트가 식별할 수 있는 동작을 검증하는 것 외에는 다른 선택지가 없으며, 이로 인해 리팩터링 내성도 자동으로 좋아진다.
* API를 잘 설계하면 단위 테스트도 자동으로 좋아진다.
* 잘 설계된 API의 정의에서 비롯된 또 다른 지침으로, 연산과 상태를 최소한으로 노출해야 한다.

## Section 3. 목과 테스트 취약성 간의 관계

* 어떤 테스트든 비즈니스 요구 사항으로 거슬러 올라갈 수 있어야 한다. 
* 각 테스트는 도메인 전문가에게 의미 있는 이야기를 전달해야 하며, 그렇지 않으면 테스트가 구현 세부 사항과 결합돼 있으므로 불안정하다는 강한 암시다.

간단하게 육각형 아키텍처에 대해 살펴보았다.

* 코드베이스의 공개 API를 항상 비즈니스 요구 사항에 따라 추적하라는 이 지침은 대부분의 도메인 클래스와 애플리케이션 서비스에 적용되지만, 유틸리티나 인프라 코드에는 적용되지 않는다.

### 시스템 내부 통신과 시스템 간 통신

일반적인 애플리케이션에는 시스템 내부 통신가 시스템 간 통신이 있다.
시스템 내부 통신은 애플리케이션 내 클래스 간의 통신이다. 시스템 간 통신은 애플리케이션이 다른 애플리케이션과 통신하는 것을 말한다.

> 시스템 내부 통신은 구현 세부 사항이고, 시스템 간 통신은 그렇지 않다.

연산을 수행하기 위한 도메인 클래스 간의 협력은 식별할 수 있는 동작이 아니므로 시스템 내부 통신은 구현 세부 사항에 해당한다.
이러한 협력은 클라이언트의 목표와 직접적인 관계가 없다. 따라서 이러한 협력과 결합하면 테스트가 취약해진다.
 
시스템 내부 통신과 다르게, 시스템 외부 환경과 통신하는 방식은 전체적으로 해당 시스템의 식별할 수 있는 동작을 나타낸다.
이는 애플리케이션에 항상 있어야 하는 계약이다.

* 시스템 간 통신의 특성은 별도 애플리케이션과 함께 성장하는 방식에서 비롯된다.
* 성장의 주요 원칙 중 하나로 하위 호환성을 지키는 것이다.
* 시스템 내부에서 하는 리팩터링과 다르게, 외부 애플리케이션과 통신할 때 사용하는 통신 패턴은 항상 외부 애플리케이션이 이해할 수 있도록 유지해야 한다.

목을 사용하면 시스템과 외부 애플리케이션 간의 통신 패턴을 확인할 때 좋다. 반대로 시스템 내 클래스 간의 통신을 검증하는 데 사용하면 테스트가 구현 세부 사항과 결합되며, 그에 따라 리팩터링 내성 지표가 미흡해진다.

> 시스템 내 통신을 검증하고 목을 사용하면 취약한 테스트로 이루어진다.
> 따라서 시스템간 통신과 해당 통신의 사이드 이펙트가 외부 환경에서 보일 때만 목을 사용하는 것이 타당하다.

## Section 4. 단위 테스트의 고전파와 런던파 재고

프로세스 외부 의존성과의 통신은 외부에서 관찰할 수 없으면 구현 세부 사항이다. 리팩터링 후에는 그대로 유지할 필요가 없으므로 목으로 검증해서는 안된다.

* 좋은 예로는 애플리케이션 DB가 있다. 어떤 외부 시스템도 이 DB에 접근할 수 없다.
* 따라서 기존 기능을 손상시키지 않는 한 시스템과 애플리케이션 데이터베이스 간의 통신 패턴을 원하는 대로 수정할 수 있다.
* 완전히 통제권을 가진 프로세스 외부 의존성에 목을 사용하면 깨지기 쉬운 테스트로 이어진다.
* 피드백속도를 저하시키지 않고 어떻게 이런 의존성으로 테스트할 수 있을까?? 이는 추후 6장과 7장에서 살펴본다.