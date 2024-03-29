# Chapter 8. 통합 테스트를 하는 이유


## Section 1. 통합 테스트는 무엇인가?

### 통합 테스트의 역할

아래 단위 테스트의 요구 사항을 하나라도 충족하지 못하는 테스트는 통합 테스트 범주에 속한다.

* 단일 동작 단위를 검증하고,
* 빠르게 수행하고,
* 다른 테스트와 별도로 처리한다.

통합 테스트는 대부분 시스템이 프로세스 외부 의존성과 통합해 어떻게 작동하는지를 검증한다.

<br>

__단위 테스트는 도메인 모델을 다루는 반면, 통합 테스트는 프로세스 외부 의존성과 도메인 모델을 연결하는 코드를 확인한다.__

<br>

### 다시 보는 테스트 피라미드

* 단위 테스트와 통합 테스트 간의 균형을 유지하는 것이 중요하다.
* 통합 테스트가 프로세스 외부 의존성에 직접 작동하면 느려지며, 이러한 테스트는 유지비가 많이 든다.

유지비 증가의 이유는 다음과 같다.

1. 프로세스 외부 의존성 운영이 필요함.
2. 관련된 협력자가 많아서 테스트가 비대해진다.

통합 테스트는 코드를 더 많이 거치므로 회귀 방지가 단위 테스트보다 우수하다. 또한 제품 코드와의 결합도가 낮아서 리팩터링 내성도 우수하다.

<br>

단위 테스트로 가능한 한 많이 비즈니스 시나리오의 예외 상황을 확인하고, 통합 테스트는 주요 흐름과 단위 테스트가 다루지 못하는 기타 예외 상황을 다룬다.

> 주요 흐름은 시나리오의 성공적인 실행이다. 예외 상황은 비즈니스 시나리오 수행 중 오류가 발생하는 경우다.

<br>

### 통합 테스트와 빠른 실패

* 통합 테스트에서 프로세스 외부 의존성과의 상호 작용을 모두 확인하려면 가장 긴 주요 흐름을 선택하라.
* 모든 상호 작용을 거치는 흐름이 없으면, 외부 시스템과의 통신을 모두 확인하는 데 필요한 만큼 통합 테스트를 추가로 작성하라.

> 좋지 않은 테스트를 작성하는 것보다는 테스트를 작성하지 않는 것이 좋다. 가치가 별로 없는 테스트는 좋지 않은 테스트다.

<br>

## Section 2. 어떤 프로세스 외부 의존성을 직접 테스트해야 하는가?

### 프로세스 외부 의존성의 두 가지 유형

1. 관리 의존성

* 이러한 의존성은 애플리케이션을 통해서만 접근할 수 있으며, 해당 의존성과의 상호 작용은 외부 환경에서 볼 수 없다.
* 대표적인 예로 데이터베이스가 있다.
* 외부 시스템은 보통 데이터베이스에 직접 접근하지 않고 애플리케이션에서 제공하는 API를 통해 접근한다.

2. 비관리 의존성

* 해당 의존성과의 상호 작용을 외부에서 볼 수 있다.
* 예를 들어 STMP 서버와 메시지 버스 등이 있다.
* 둘 다 다른 애플리케이션에서 볼 수 있는 사이드 이펙트를 발생시킨다.

##### 관리 의존성은 실제 인스턴스를 사용하고, 비관리 의존성은 목으로 대체해라.

<br>


### 관리 의존성이면서 비관리 의존성인 프로세스 외부 의존성 다루기

* 때로는 의존 관리성과 비관리 의존성 모두의 속성을 나타내는 외부 의존성이 있을 수 있다. (ex: 다른 애플리케이션이 접근할 수 있는 DB)
* 이 경우는 다른 애플리케이션에서 볼 수 있는 테이블을 비관리 의존성으로 취급하라.

> 참고로 시스텝ㅁ 간의 통합을 구현하는 데 DB를 사용하지말라. API나 메시징 큐를 사용할 것.

<br>

### 통합 테스트에서 실제 데이터베이스를 사용할 수 없으면 어떻게 알까?

* 데이터베이스를 그대로 테스트할 수 없다면 통합 테스트를 아예 작성하지 않고 도메인 모델의 단위 테스트에만 집중하라.
* 항상 모든 테스트를 철저히 검토해야 한다.
* 가치가 충분하지 않은 테스트는 테스트 스위트에 있어서는 안된다.

<br>

## Section 3. 통합 테스트 예제

* 데이터베이스는 실제 인스턴스를 사용하고 메시징 큐는 목으로 대체

### 엔드 투 엔드 테스트

* 외부 클라이언트의 동작을 모방하려면 메시지 버스는 직접 확인하고, 데이터베이스 상태는 애플리케이션을 통해 검증한다.

<br>

## Section 4. 의존성 추상화를 위한 인터페이스 사용

### 인터페이스와 느슨한 결합

인터페이스를 사용하는 이유는 아래와 같다.

1. 프로세스 외부 의존성을 추상화해 느슨한 결합을 달성하고,
2. 기존 코드를 변경하지 않고 새로운 기능을 추가해 공개 폐쇄 원칙을 지키기 때문이다.

단일 구현을 위한 인터페이스는 추상화가 아니며, 해당 인터페이스를 구현하는 구체 클래스보다 결합도가 낮지 않다.
 
#### 진정한 추상화는 발견하는 것인지, 발명하는 것이 아니다.

따라서 인터페이스가 진정으로 추상화하려면 구현이 적어도 두 가지는 있어야 한다.
 
두 번째 이유는 더 기본적인 원칙인 YANGI를 위바하기 때문에 잘못된 생각이다. YANGI는 현재 필요하지 않은 기능에 시간을 들이지 말라는 것이다.
크게 두 가지 이유가 있으며 다음과 같다.

1. 기회 비용
2. 프로젝트 코드가 작을 수록 좋다.

> 코드를 작성하는 것은 문제를 해결하는 값비싼 방법이다. 해결책에 필요한 코드가 적고 간단할수록 더 좋다.

<br>

### 프로세스 외부 의존성에 인터페이스를 사용하는 이유는 무엇인가?

결론부터 말하면 목을 사용하기 위함이다.
 
* 인터페이스가 없으면 테스트 대역을 만들 수 없으므로 테스트 대상 시스템과 프로세스 외부 의존성 간의 상호 작용을 확인할 수 없다.
* 따라서 이러한 의존성을 목으로 처리할 필요가 없는 한, 프로세스 외부 의존성에 대한 인터페이스를 두지 말라.
* 비관리 의존성만 목으로 처리하므로, 결국 비관리 의존성에 대해서만 인터페이스를 쓰라는 지침이 된다.

<br>

## Section 5. 통합 테스트 모범 사례

통합 테스트를 최대한 활용하는 데 도움이 되는 몇 가지 일반적인 지침이 있다.

* 도메인 모델 경계 명시하기
* 애플리케이션 내 계층 줄이기
* 순환 의존성 제거하기 (ex: 콜백)

<br>

## Section 6. 로깅 기능을 테스트하는 방법

로깅은 외부 의존성(파일, DB)에 사이드 이펙트를 초래한다.

* 이런 사이드 이펙트를 고객이나 애플리케이션의 클라이언트 또는 개발자 이외의 다른 사람이 보는 경우는, 로깅은 식별할 수 있는 동작이므로 테스트해야 한다.
* 하지만 보는 이가 개발자뿐이라면, 아무도 모르게 자유로이 수정할 수 있는 구현 세부사항이므로 테스트해서는 안된다.
 
예를 들어 로깅 라이브러리를 작성하는 경우, 이 라이브러리가 생성하는 로그는 식별할 수 있는 동작에서 가장 중요한(유일한) 부분이다. 
또 다른 예로 비즈니스 담당자가 주요 애플리케이션 작업 흐름을 기록해야 한다고 주장하는 경우가 있다.
이 경우 로그도 비즈니스 요구 사항이므로 테스트를 거쳐야 한다.
  
로깅은 두 가지 유형이 있다.

1. __자원 로깅은 지원 담당자나 시스템 관리자가 추적할 수 있는 메시지를 생성한다.__
2. __진단 로깅은 개발자가 애플리케이션 내부 상황을 파악할 수 있도록 돕는다.__

<br>

### 로깅을 어떻게 테스트할 것인가?

로깅에는 프로세스 외부 의존성이 있기 때문에 테스트에 관한 한 프로젝트 외부 의존성에 영향을 주는 다른 기능과 동일한 기능이 적용된다. 

즉 목을 사용하는 것이다. 

* 그러나 지원 로깅은 비즈니스 요구 사항이므로, 해당 요구 사항을 코드베이스에 명시적으로 반영해야 한다. 
* 따라서 비즈니스에 필요한 모든 지원 로깅을 명시적으로 나열하는 특별한 DomainLogger 클래스를 만들고 단순 로거 대신 해당 클래스와의 상호작용을 확인하자.
* DomainLogger를 도입해 도메인 언어를 사용해 비즈니스에 필요한 특정 로그 항목을 선언하면서 지원 로깅을 더 쉽게 이해하고 유지보수할 수 있다.
* 이 구현은 구조화된 로깅 개념과 매우 유사. 로그 파일의 후처리와 분석에서 유연성이 크게 향상된다.

<br>

### 구조화된 로깅 이해하기

* 구조화된 로깅은 로그 데이터 캡처와 렌더링을 분리하는 로깅 기술이다.
* 구조화된 로깅은 로그 저장소에 구조가 있다.

```
log.info("User id is {}", userId);
```
* 메시지 템플릿의 해시를 계산하고 해당 해시를 입력 매개변수와 결합해 캡처한 데이터 세트를 형성한다.
* 캡처한 데이터를 JSON 또는 CSV 파일로 렌더링하도록 로깅 라이브러리를 설정할 수 있으며, 이를 통해 분석이 더 쉬워질 수 있다.

<br>

### 지원 로깅과 진단 로깅을 위한 테스트 작성

* DomainLogger를 도메인 이벤트로 교체할 수 있다.
* 도메인 모델에서는 진단 로깅을 절대 사용하지 말자. 컨트롤러로 옮기자.

<br>

### 로깅이 얼마나 많으면 충분한가?

지원 로깅은 비즈니스 요구 사항이므로, 여기에는 질문의 여지가 없다. 
 
진단 로깅을 과도하게 사용하지 않는 것이 중요하다. 가끔 사용하자.

이유는 코드를 혼란스럽게 한다. 또한 로그가 많을 수록 관련 장보를 찾기가 어렵기 때문이다.

<br>

#### 로거 인스턴스는 클래스 생성자를 통해 주입 받는 것이 이상적이다.