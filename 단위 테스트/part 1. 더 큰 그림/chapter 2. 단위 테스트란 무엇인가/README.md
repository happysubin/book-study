# Chapter 2. 단위 테스트란 무엇인가

## Section 1. 단위 테스트의 정의

단위 테스트에는 가장 중요한 3가지 속성이 있다.

* 작은 코드 조각(단위)을 검증하고
* 빠르게 수행하고
* 격리된 방식으로 처리하는 자동화된 테스트다.

여기서 세 번째 특징에서 대중의 의견이 다르다. 격리 문제는 고전파와 런던파를 구분할 수 있게 해주는 근원적 차이에 속한다.

### 격리 문제에 대한 런던파의 접근

코드 조각을 격리된 방식으로 검증한다는 것은 무엇을 의미하는가?

> 런던파에서는 테스트 대상 시스템을 협력자에게서 격리하는 것을 일컫는다.
> 즉 하나의 클래스가 다른 클래스 또는 여러 클래스에 의존하면 이 모든 의존성을 테스트 대역으로 대체해야 한다.

이런식으로 동작을 외부 영향과 분리해서 테스트 대상 클래스에만 집중할 수 있다.
 
이 방법의 큰 이점은 테스트가 실패하면 코드베이스의 어느 부분이 고장 났느지 확실히 알 수 있다는 것이다.

* 즉, __확실히 테스트 대상 시스템이 고장난 것이다. 클래스의 모든 의존성은 테스트 대역으로 대체됐기 때문에 의심할 여지가 없다.__
 
다른 이점은 객체 그래프를 분할할 수 있는 것이다.

* 의존성을 가진 코드베이스를 테스트하는 것은 테스트 대역 없이는 어렵다.
* 유일하게 남은 서택은 전체 객체 그래프를 다시 만드는 것이다. 하지만 클래스의 수가 너무 많으면 어려운 작업일 수 있다.
* 테스트 대역을 사용하면 객체 그래프를 다시 만들지 않아도 된다.
* 또한 클래스의 직접적인 의존성을 대체할 수 있고, 더 나아가 의존성의 의존성을 다룰 필요도 없다.
* 그래프를 효과적으로 분해해 단위 테스트에서 준비를 크게 줄일 수 있다.

또한 단위 테스트 격리에는 작지만 유익한 부가적인 이점이 더 있다.

* 프로젝트 전반적으로 한 번에 한 클래스만 테스트하라는 지침을 도입하면 전체 단위 테스트 스위트를 간단한 구조로 할 수 있다.
* 더 이상 코드베이스를 테스트하는 방법을 고민할 필요가 없다는 것이다.

### 격리 문제에 대한 고전파의 접근

* 테스트는 협력자를 대체하지 않고 운영용 인스턴스를 사용한다.
* 고전적인 방식의 자연스러운 결과로, 이제 테스트 대상 뿐만 아니라 테스트 대상, 협력자 둘 다 효과적으로 검증한다.
* 그러나 테스트 대상이 올바르게 동작하더라도, 협력자 내부에 버그가 있다면 단위 테스트에 실패할 수 있다.
* 테스트에서 두 클래스는 서로 격리돼 있지 않다.

### 격리 문제에 대한 런던파의 접근

* 런던파는 테스트 대역으로 테스트 대상 코드 조각을 분리해서 격리 요구 사항에 다가간다.
* 흥미롭게도 이 관점은 무엇이 작은 코드에 해당하는지에 대한 견해에도 영향을 미친다.
* 각각의 모든 클래스를 격리해야 한다면 테스트 대상 코드 조각은 당연히 단일 클래스이거나 해당 클래스 내의 메서드여야 한다.
* 격리 문제에 접근하는 방식 때문에 이보다 더 클 수가 없다.
* 일반적으로 한 번에 한 클래스로 테스트하는 지침을 따르려고 노력해야 한다.

격리 특성을 해석하는 또 다른 방법으로는 고전적인 방법이 있다.

* 고전적인 방법에서 코드를 꼭 격리하는 방식으로 테스트해야 하는 것은 아니다.
* 대신 단위 테스트는 서로 격리해서 실행해야 한다.
* 이렇게 하면 테스트를 어떤 순서든 가장 적합한 방식으로 실행할 수 있으며, 서로의 결과에 영향을 미치지 않는다.

각각의 테스트를 격리하는 것은 여러 클래스가 모두 메모리에 상주하고 공유 상태에 도달하지 않는 한, 여러 클래스를 한 번에 테스트해도 괜찮다는 뜻이다.

* 이를 통해 테스트가 서로 소통하고 실행 컨텍스트에 영향을 줄 수 있다.
* DB, 파일 시스템 등 프로세스 외부 의존성이 이러한 공유 상태의 대표적인 예다.
* 격리 문제에 대한 이러한 견해는 목과 기타 테스트 대역의 사용에 대한 훨씬 더 평범한 견해를 수반한다.
* 테스트 대역을 사용할 수 있지만, 보통 테스트 간에 공유 상태를 일으키는 의존성에 대해서만 사용한다.

공유 의존성은 테스트 대상 클래스 간이 아니라 단위 테스트 간에 공유한다.
그런 의미에서 싱글턴 의존성은 각 테스트에서 새 인스턴스를 만들 수 있기만 하면 공유되지 않는다.
의존성이 주입되면 각 테스트에서 새 인스턴스를 만들 수 있기 때문이다.
 
그러나 새 파일 시스템이나 데이터베이스를 만들 수 는 없으며, 테스트 간에 공유되거나 테스트 대역으로 대체돼야 한다.
공유 의존성을 대체하는 또 다른 이유는 테스트 실행 속도를 높이는데 있다.

## Section 2. 단위 테스트의 런던파와 고전파

런던파는 테스트 대상 시스템에서 협력자를 격리하는 것으로 보는 반면, 고전파는 단위 테스트끼리 격리하는 것으로 본다.
종합 하면 세가지 주요 주제에 대해 의견 차이가 있다.

1. 격리 요구 사항
2. 테스트 대상 코드 조각(단위)의 구성 요소
3. 의존성 처리

|  |격리 주체 | 단위의 크기          | 테스트 대역 사용 대상    |
|--------|------|-----------------|-----------------|
| 런던파 |단위| 단일 클래스          | 불변 의존성 외 모든 의존성 |
| 고전파 |단위 테스트| 단일 클래스 또는 클래스 세트| 공유 의존성          |

### 고전파와 런던파가 의존성을 다루는 방법

* 고전파에서는 공유 의존성을 테스트 대역으로 교체한다.
* 런던파에서는 변경 가능한 한 비공개 의존성도 테스트 대역으로 교체할 수 있다.

* 이 책에서는 공유 의존성과 프로세스 외부 의존성이라는 용어는 동일시한다.
* 보통 의존성이 프로세스 내부에 있으면 각 테스트에서 별도의 인스턴스를 쉽게 공급할 수 있으므로 테스트 간에 공유할 필요가 없다.
* 마찬가지로 공유되지 않는 프로세스 외부 의존성은 일반적으로 접할 일이 없다.

## Section 3. 고전파와 런던파의 비교

고전파와 런던파 간의 주요 차이는 단위 테스트의 정의에서 격리 문제를 어떻게 다루는지에 있다.
이는 결국 테스트해야 할 단위의 처리와 의존성 취급에 대한 방법으로 넘어간다.
 
작가는 고전파를 선호한다고 한다. 
고품질의 테스트를 만들고 단위 테스트의 궁극적인 목표인 프로젝트의 지속 가능한 성장을 달성하는 데 더 적합하기 때문이다.
그 이유는 취약성에 있다. 이는 5장에서 살펴본다.
 
런던파의 장점은 아래와 같다.

1. 입자성이 좋다. 테스트가 세밀해서 한 번에 한 클래스만 확인한다.
2. 서로 연결된 클래스의 그래프가 커져도 테스트하기 쉽다. 모든 협력자는 테스트 대역으로 대체되기 때문에 테스트 작성 시 걱정할 필요가 없다.
3. 테스트가 실패하면 어떤 기능이 실패했는지 확실히 알 수 있다. 클래스의 협력자가 없으면 테스트 대상 클래스 외에 다른 것을 의심할 여지가 없다.

#### 한 번에 한 클래스만 테스트하기

* 런던파는 클래스는 단위로 간주한다.
* 객체지향 프로그래밍 경력을 가진 개발자들은 보통 클래스를 모든 코드베이스의 기초에 위치한 원자 빌딩 블록으로 간주한다.
* 이로 인해 자연스럽게 클래스를 테스트에서 검증할 원자 단위로도 취급하게 한다.
* 이런 경향은 이해되기는 하지만 오해의 소지가 있다.

> 테스트는 코드의 단위를 검증해서는 안 된다. 오히려 동작의 단위, 즉 문제 영역에 의미가 있는 것, 이상적으로는 비즈니스 담당자가 유용하다고 인식할 수 있는 것을 검증해야 한다.
> 동작 단위를 구현하는 데 클래스가 얼마나 필요한지는 상관없다. 단위는 여러 클래스에 걸쳐 있거나 한 클래스에만 있을 수 있고, 심지어 아주 작은 메서드가 될 수도 있다.

그래서 좋은 코드 입자성을 목표로 하는 것은 도움이 되지 않는다. __테스트가 단일 동작 단위를 검증하는 한 좋은 테스트다.__

#### 상호 연결된 클래스의 큰 그래프를 단위 테스트하기

실제 협력자를 대신해 목을 사용하면 클래스를 쉽게 테스트할 수 있다.
특히 테스트 대상 클래스에 의존성이 있고, 이 의존성에 다시 각각의 의존성이 있고, 이렇게 여러 계층에 걸쳐서 계속되는 식으로 의존성 그래프가 복잡하게 있을 때 쉽게 테스트할 수 있다.
테스트 대역을 쓰면 클래스의 직접적인 의존성을 대체해 그래프를 나눌 수 있으며, 이는 단위 테스트에서 준비해야할 작업량을 크게 줄일 수 있다.
__고전파를 따라 테스트 대상 시스템을 설정하려면 전체 객체 그래프를 다시 생성해야 하는데, 작업이 많을 수 있다.
 
모두 사실이지만, 이 추리 과정은 잘못된 문제에 초점을 맞추고 있다.

* 상호 연결된 클래스의 크고 복잡한 그래프를 테스트할 방법을 찾는 대신, 먼저 이러한 클래스 그래프를 갖지 않는데 집중해야 한다.
* 대개 클래스 그래프가 커진 것은 코드 설계 문제의 결과다.

#### 버그 위치 정확히 찾아내기

고전적인 방식에서는 오작동하는 클래스를 참조하는 클라이언트를 대상으로 하는 테스트도 실패할 수 있다.
즉, 하나의 버그가 전체 시스템에 걸쳐 테스트 실패를 야기하는 파급 효과를 초래한다.
결국 문제의 원인을 찾기 더 어려워진다.
 
우려할 만하지만, 큰 문제는 아니다.

* 테스트를 정기적으로 실행하면 버그의 원인을 알아낼 수 있다.
* 즉, 마지막으로 한 수정이 무엇인지 알기 때문에 문제를 찾는 것은 그리 어렵지 않다.
* 또한 실패한 테스트를 모두 볼 필요는 없다.
* 하나를 고치면 다른 것들도 자동으로 고쳐진다.
* 게다가 테스트 스위트 전체에 결쳐 계단식으로 실패하는 데 가치가 있다.
* 버그가 테스트 하나뿐만 아니라 많은 테스트에서 결함으로 이어진다면, 방금 고장 낸 코드에 조각이 큰 가치가 있다는 것을 보여준다.
* 즉, 전체 시스템이 그것에 의존한다.

#### 고전파와 런던파 사이의 다른 차이점

고전파와 런던파 사이에 남아있는 두 가지 차이점은 다음과 같다.

* 테스트 주도 개발을 통한 시스템 설계 방식
* 과도한 명세 문제

## Section 4. 두 분파의 통합 테스트

* 런던파는 실제 협력자 객체를 사용하는 모든 테스트를 통합 테스트로 간주한다.
* 고전 스타일로 작성된 대부분의 테스트는 런던파 지지자들에게 통합 테스트로 느껴질 것이다.

단위 테스트의 정의는 아래와 같다.

* 단일 동작 단위를 검증하고, 빠르게 수행하고, 다른 테스트와 별도로 처리한다.

통합 테스트는 이러한 기준 중 하나를 충족하지 않는다.

* 공유 의존성에 접근하는 테스트는 다른 테스트와 분리해 실행할 수 없다. 이런 테스트에서 데이터베이스 상태 변경이 생기면 병렬로 실행할 때 동일한 데이터베이스에 의존하는 다른 모든 테스트의 결과가 변경될 것이다.
* 마찬가지로 외부 의존성에 접근하면 테스트는 매우 느려진다.
* 마지막으로, 둘 이상의 동작 단위를 검증할 때의 테스트는 통합 테스트다.
* 또한 다른 팀이 개발한 모듈이 둘 이상 있을 때 통합 테스트로 어떻게 작동하는지 검증할 수 있다.
* 이는 또한 여러 동작 단위를 한 번에 검증하는 제 3의 테스트 유형에 해당한다.
* 이런 통합은 통상적으로 프로세스 외부 의존성을 필요로 하므로 테스트는 하나뿐만 아니라 세 가지 기준 모두를 충족시키지 못할 것이다.

통합 테스트는 시스템 전체를 검증해 소프트웨어 품질을 기여하는데 중요한 역할을 한다.

#### 엔드 투 엔드 테스트

* 엔드 투 엔드 테스트는 통합 테스트의 일부다.
* 엔드 투 엔드 테스트도 코드가 프로세스 외부 종속성과 함께 어떻게 작동하는지 검증한다.
* 엔드 투 엔드 테스트는 일반적으로 통합테스트보다 더 많은 의존성을 포함한다.
* 통합테스트는 프로세스 외부 의존성을 한 두개만 가지고 동작한다.
* 반면 엔드 투 엔드 테스트는 외부 프로세스 의존성을 전부 또는 대다수 갖고 작동한다.

엔드 투 엔드 테스트는 유지 보수 측면에서 가장 비용이 많이 들기 때문에 모든 단위 테스트와 통합 테스트를 통과한 후 빌드 프로세스 후반에 실행하는 것이 좋다.
또한 개인 개발자 머신이 아닌 빌드 서버에서만 실행할 수도 있다.

## 중요하다고 느낀 부분

* 테스트는 코드의 단위를 검증해서는 안 된다. 
* 오히려 동작의 단위, 즉 문제 영역에 의미가 있는 것, 이상적으로는 비즈니스 담당자가 유용하다고 인식할 수 있는 것을 검증해야 한다.






