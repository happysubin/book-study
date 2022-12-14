# Chapter 14. 일관성 있는 협력

* 객체는 협력을 위해 존재하며, 협력은 객체가 존재하는 이유와 문맥을 제공한다.
* 잘 설계된 애플리케이션은 이해하기 쉽고, 수정이 용이하며, 재사용 가능한 협력의 모임이다.
* 객체지향 설계의 목표는 적절한 책임을 수행하는 객체들의 협력을 기반으로 결합도가 낮고 재사용 가능한 코드 구조를 창조하는 것이다.
* 객체지향 패러다임의 장점은 설계를 재사용할 수 있다는 것이다.
* 재사용을 위해서는 객체들의 협력 방식을 일관성있게 만들어야 한다.
* 특정한 문제를 유사한 방법으로 해결하고 있다는 사실을 알면 문제를 이해하는 것만으로도 코드의 구조를 예상할 수 있게 된다.
* 객체들의 협력이 전체적으로 일관성 있는 유사한 패턴을 따른다면 시스템을 이해하고 확장하기 위해 요구되는 정신적인 부담을 크게 줄일 수 있다.
* 일관성 있는 협력 패턴을 적용하면 코드가 이해하기 쉽고 직관적이며 유연해진다는 것이 이번장의 주제다.

## part 1. 핸드폰 과금 시스템 변경하기

* 11장에서 구현한 핸드폰 과금 시스템의 요금 정책을 수정.
* 기본 정책이 4가지로 늘어남. 고정요금, 시간대별, 요일별, 구간별. p.472 그림 14.1에서 경우의 수를 나타냄.
* 고정요금 방식 구현.
* 시간대별 방식 구현. 시간대별 방식의 통화 요금을 계산하기 위해서는 통화의 시작 시간과 종료 시간뿐만 아니라 시작 일자와 종료일자도 고려해야 한다.
* 시간대별 방식을 구현하는 데 있어 핵심은 규칙에 따라 통화 시간을 분할하는 방법을 결정하는 것이다.
  1. 통화 기간을 일자별로 분리한다. (기간을 처리하는 전문가는 DATETIMEINTERVAL)
  2. 일자별로 분리된 기간을 다시 시간대별 규칙에 따라 분리한 후 각 기간에 대해 요금을 계산한다. (이에 대한 전문가는 TimeOfDayDiscountPolicy 클래스)
* 요일별 방식도 구현함. 요일별 방식을 구성하는 규칙을 구현해야 한다.
* 구간별 방식도 구현함.
* 현재 제일 큰 문제는 클래스들이 유사한 문제를 해결하고 있음에도 불구하고 설계에 일관성이 없다.
* 이 클래스들은 기본 정책을 구현한다는 공통의 목적을 공유하지만 정책을 구현하는 방식은 완전히 다르다.
* 다시 말해서 개념적으로는 연관이 있어도 구현 방식에 있어서는 완전히 제각각이다.
* 유사한 기능을 서로 다른 방식으로 구현해서는 안된다. 유사한 기능은 유사한 방식으로 구현해야 한다.
* 객체지향에서 기능을 구현하는 유일한 방법은 객체 사이의 협력을 만드는 것뿐이므로 유지보수 가능한 시스템을 구축하는 첫걸음은 협력을 일관성 있게 만드는 것이다.

## part 2. 설계에 일관성 부여하기

* 일관성 있는 설계를 만드는 데 가장 훌륭한 조언은 다양한 설계 경험을 익히는 것이다.
* 설계 경험이 풍부하면 풍부할수록 어떤 위치에서 일관성을 보장해야 하고 일관성을 제공하기 위해 어떤 방법을 사용해야 하는지를 직관적으로 결정할 수 있다.
* 일관성 있는 설계를 위한 두 번째 조언은 널리 알려진 디자인 패턴을 학습하고 변경이라는 문맥 안에서 디자인 패턴을 적용하는 것이다.
* 디자인 패턴이 반복적으로 적용할 수 있는 설계 구조를 제공한다고 하더라도 모든 경우에 적합한 패턴을 찾을 수 있는 것은 아니다.
* 따라서 협력을 일관성 있게 만들기 위해 다음과 같은 기본 지침을 따르는 것이 도움이 된다.
  * 변하는 개념을 변하지 않는 개념으로부터 분리하라. (매우 중요!!!) 
  * 변하는 개념을 캡슐화하라.
* 책에서 설명했던 모든 원칙과 개념들 역시 대부분 변경의 캡슐화라는 목표를 향한다.
* 이전에 진행한 영화 시스템 예제 코드를 살펴보았다.
* 절차지향 프로그램에서 변경을 처리하는 전통적인 방법은 이처럼 조건문의 분기를 추가하거나 개별 분기 로직을 수정하는 것이다.
* 객체지향은 조금 다른 접근 방법을 취한다. 객체지향에서 변경을 다루는 전통적인 방법은 조건 로직을 객체 사이의 이동으로 바꾸는 것이다.
* 다형성은 조건 로직을 객체 사이의 이동으로 바꾸기 위해 객체지향이 제공하는 설계 기법이다.
* 객체지향적인 코드는 조건을 판단하지 않는다. 단지 다음 객체로 이동할 뿐이다.
* 조건 로직들을 변경의 압력에 맞춰 작은 클래스들로 분리하고 나면 인스턴스들 사이의 협력 패턴에 일관성을 부여하기가 더 쉬워진다!! (이 부분 매우 중요)
* 클래스를 분리 하기 위해 따라야할 기준은 바로 변경의 이유와 주기다. 클래스는 단 하나의 이유에 의해서만 변경돼야 하고 클래스 안의 코드는 모두 함께 변경돼야 한다. 즉 단일 책임 원칙을 지켜야 한다.
* 큰 메서드 안에 뭉쳐있던 조건 로직들을 변경의 압력에 맞춰 작은 클래스들로 분리하고 나면 인스턴스들 사이의 협력 패턴에 일관성을 부여하기가 더 쉬워진다.
* 유사한 행동을 수행하는 작은 클래스들이 자연스럽게 역할이라는 추상화로 묶이게 되고 역할 사이에서 이뤄지는 협력 방식이 전체 설계의 일관성을 유지할 수 있게 이끌어주기 때문이다.
* Movie, DiscountPolicy, DiscountCondition 사이의 협력 패턴은 변경을 기준으로 클래스를 분리함으로 써 어떻게 일관성 있는 협력을 얻을 수 있는지를 보여준다.
* 우리는 각 조건문을 개별적인 객체로 분리했고 이 객체들과 일관성 있게 협력하기 위해 타입 계층을 구성했다. (이 부분도 매우 중요)
* 그리고 이 타입 계층을 클라이언트로부터 분리 하기 위해 역할을 도입하고, 최종적으로 이 역할을 추상 클래스와 인터페이스로 구현했다.
* 훌륭한 추상화를 찾아 추상화에 의존하도록 만들어야 한다.
* 변경에 초점을 맞추고 캡슐화의 관점에서 설계를 바라보면 일관성 있는 협력 패턴을 얻을 수 있다.

### 캡슐화 다시 살펴보기

* 캡슐화란 단순히 데이터를 감추는 것이 아니라, 소프트웨어 안에서 변할 수 있는 모든 개념을 감추는 것이다. 즉 캡슐화란 변하는 어떤 것이든 감추는 것이다.
* 캡슐화의 가장 대표적인 예는 객체의 퍼블릭 인터페이스와 구현을 분리하는 것이다.
* 캡슐화는 데이터 캡슐화, 메서드 캡슐화, 객체 캡슐화, 서브타입 캡슐화 등이 있다.
* 캡슐화는 단지 데이터 은닉이 아닌, 코드 수정으로 인한 파급효과를 제어할 수 있는 모든 기법이 캡슐화의 일종이다.
* 서브타입 캡슐화는 인터페이스 상속을 사용하고, 객체 캡슐화는 합성을 사용한다.
* 변하는 부분을 분리해서 타입 계층을 만들고, 변하지 않는 부분의 일부로 타입 계층을 합성한다.

## part 3. 일관성 있는 기본 정책 구현하기

* 일관성 있는 협력을 만들기 위한 첫 번째 단계는 변하는 개념과 변하지 않는 개념을 분리하는 것이다.
* 먼저 시간대별, 요일별, 구간별 방식의 공통점은 각 기본 정책을 구성하는 방식이 유사하다는 점이다.
  * 기본 정책은 한 개 이상의 규칙으로 구성된다.
  * 하나의 규칙은 적용조건과 단위 요금의 조합이다.
* 변하지 않는 규칙으로부터 변하는 적용 조건을 분리해야 한다.

### 변경 캡슐화하기

* 협력을 일관성 있게 만들기 위해서는 변경을 캡슐화해서 파급효과를 줄여야 한다.
* 변경을 캡슐화하는  가장 좋은 방법은 변하지 않는 부분으로부터 변하는 부분을 분리하는 것이다.
* 물론 변하는 부분의 공통점을 추상화하는 것도 잊어서는 안 된다.
* 이제 변하지 않는 부분이 오직 이 추상화에만 의존하도록 관계를 제한하면 변경을 캡슐화할 수 있게 한다.
* 변하지 않는 것은 규칙이며 변하는 것은 적용 조건이다.
* 규칙에서 적용조건을 추상화해서 분리한 후 규칙이 적용조건을 표현하는 추상화를 합성 관계로 연결한다.
* 하나의 기본 정책은 하나 이상의 규칙들로 구성된다.
* 변하지 않는 부분은 기본 정책이 여러 '규칙'들의 집합이며, 하나의 '규칙'은 '적용조건'과 '단위요금'으로 구성된다.
* 해당 예제는 변경을 캡슐화해서 협력을 일관성 있게 만들면 어떤 장점을 얻을 수 있는지를 잘 보여준다.
* 변하는 부분을 변하지 않는 부분으로부터 분리했기 때문에 변하지 않는 부분을 재사용할 수 있다.
* 새로운 기능을 추가하기 위해 오직 변하는 부분만 구현하면 되기 때문에 원하는 기능을 쉽게 완성할 수 있다.
* 기능을 따라야 하는 구조를 강제할 수 있기 때문에 기능을 추가하거나 변경할 때도 설계의 일관성이 무너지지 않는다.
* 공통 코드의 구조와 협력 패턴은 모두 기본 정책에 걸쳐 동일하기 때문에 코드를 한 번 이해하면 이 지식을 다른 코드를 이해하는 데 그대로 적용할 수 있다.
* 일단 일관성 있는 협력을 이해하고 나면 변하는 부분만 따로 떼어내어 독립적으로 이해하더라도 전체적인 구조를 쉽게 이해할 수 있다.
* 유사한 기능에 대해 유사한 협력 패턴을 적용하는 것은 객체지향 시스템에서 개념적 무결성을 유지할 수 있는 가장 효과적인 방법이다.
* 협력은 고정된 것이 아니므로 현재의 협력 패턴이 무게를 지탱하기 어렵다면 변경을 수용할 수 있는 협력 패턴을 향해 과감하게 리팩토링하자.
* 요구사항의 변경에 따라 협력도 지속적으로 개선해야 한다.
* 중요한 것은 현재의 설계에 맹목적으로 일관성을 맞추는 것이 아니라 달라지는 변경의 영향에 맞춰 지속적으로 코드를 개선하려는 의지다.

### 패턴을 찾아라.

* 일관성 있는 협력의 핵심은 변경을 분리하고 캡슐화하는 것이다.
* 변경을 캡슐화하는 방법이 협력에 참여하는 객체들의 역할과 책임을 결정하고 이렇게 결정된 협력이 코드의 구조를 결정한다.
* 좋은 설계자가 되기 위해서는 변경의 방향을 파악할 수 있는 날카로운 감각을 기르는 것이 중요하다.
* 애플리케이션에서 유사한 기능에 대한 변경이 지속적으로 발생하고 있다면 변경을 캡슐화할 수 있는 적절한 추상화를 찾은 후, 이 추상화에 변하지 않는 공통적인 책임을 할당하라.
* 협력을 일관성 있게 만드는 과정은 유사한 기능을 구현하기 위해 반복적으로 적용할 수 있는 협력의 구조를 찾아가는 기나긴 여정이다.
* 협력을 일관성 있게 만드는 것은 유사한 변경을 수용할 수 있는 협력 패턴을 발견하는 것과 동일하다.
