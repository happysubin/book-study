# Chapter 3. 역할, 책임, 협력

* 객체지향 패러다임의 관점에서 핵심은 역할, 책임, 협력이다.
* 객체지향의 본질은 협력하는 객체들의 공동체를 창조하는 것이다.
* 객체지향 설계의 핵심은 협력을 구성하기 위해 적절한 객체를 찾고 적절한 책임을 할당하는 과정에서 드러난다.

## part 1. 협력
* 객체가 협력에 참여하기 위해 수행하는 로직은 책임이라고 부른다.
* 객체들이 협력 안에서 수행하는 책임들이 모여 객체가 수행하는 역할을 구성한다.
* 메시지 전송은 객체 사이의 협력을 위해 사용할 수 있는 유일한 커뮤니케이션 수단이다. 메시지를 수신한 객체는 메서드를 실행해 요청에 응답한다.
* 메시지를 어떻게 처리할지는 수신한 객체가 직접 결정한다.
* 객체의 행동을 결정하는 것은 객체가 참여하고 있는 협력이다.
* 협력은 객체를 설계하는 데 필요한 일종의 문맥(context)를 제공한다.

## part 2. 책임
* 객체를 설계하기 위해 필요한 문맥인 협력이 갖춰지면 다음으로 할 일은 협력에 필요한 행동을 수행할 수 있는 객체를 찾는 것이다.
* 협력에 참여하기 위해 객체가 수행하는 행동을 책임이라고 한다.
  * 객체의 책임을 크게 하는 것과 아는 것으로 나눈다.
    * 하는 것
      * 객체를 생성하거나 계싼을 수행하는 등의 스스로 하는 것
      * 다른 객체의 행동을 시작시키는 것
      * 다른 객체의 활동을 제어하고 조절하는 것
    
    * 아는 것
      * 사적인 정보에 관해 아는 것
      * 관련된 객체에 관해 아는 것
      * 자신이 유도하거나 개선할 수 있는 것에 관해 아는 것.
* 저번 단원 예시
  * Screening의 책임은 영화를 예매하는 것. -> 이것은 하는 것과 관련된 책임.
  * Movie의 책임은 요금을 계산하는 것. -> 이것은 하는 것과 관련된 책임.
  * Screening는 자신이 상영할 영화를 알고 있어야 한다. -> 이것은 아는 것과 관련된 책임.
  * Movie는 가격과 어떤 할인 정책이 적용됐는지 알아야 한다. -> 이것은 아는 것과 관련된 책임.
* 책임은 아는 것과, 하는 것에 밀접한 관련이 있다. 책임은 외부의 인터페이스와 내부의 인터페이스를 결정한다.
* 객체지향에서 제일 중요한 것은 책임이다. 객체에게 얼마나 적절한 책임을 할당하느냐가 설계의 전체적인 품질을 결정한다.
* 자율적인 객체를 만드는 기본적인 방법은 책임을 수행하는 데 필요한 정보를 가장 잘 알고 있는 전문가에게 그 책임을 할당하는 것이다.
* 어떤 도움이 필요한 경우 그 일의 전문가에게 도움을 요청하는 논리와 비슷하다.
* 객체지향 설계는 시스템의 책임을 완료하는 데 필요한 더 작은 책임을 찾아내고 이를 객체들에게 할당하는 반복적인 과정을 통해 모양을 갖춰간다.
* 책임 주도 설계의 주요 과정
  * 시스템이 사용자에게 제공해야 하는 기능인 시스템 책임을 파악.
  * 시스템 책임을 더 작은 책임으로 분할.
  * 분할된 책임을 수행할 수 있는 적절한 객체 또는 역할을 찾아 책임을 할당한다.
  * 객체가 책임을 수행하는 도중 다른 객체의 도움이 필요한 경우 이를 책임질 적절한 객체 또는 역할을 찾는다.
  * 해당 객체 또는 역할에게 책임을 할당함으로써 두 객체가 협력하게 한다.
* 협력이 책임을 이끌어내고 책임이 협력에 참여할 객체를 결정한다.
* 객체가 메시지를 선택하는 것이 아니라 메시지가 객체를 선택한다.
* 행동이 상태를 결정한다.

## part 3. 역할
* 객체가 어떤 특정한 협력 안에서 수행하는 책임의 집합을 역할이라고 부른다.
* 실제로 협력을 모델링하면 특정한 객체가 아니라 역할에게 책임을 할당한다고 생각하는 것이 좋다.
* 예를 들어 이전 챕터에서 만든 할인 정책이 그 예시다. AmountDiscountPolicy와 PercentDiscountPolicy가 있는데 인터페이스로 추상화를해 역할을 부여했다.
* 이렇게 설계하는 것이 유연한 설계이며 재사용성이 높아진다. 역할이 두 종류의 DiscountPolicy를 포괄하는 추상화인 점에 주목.
* 책임과 역할을 중심으로 협력을 바라보는 것이 변경과 확장이 용이한 유연한 설계롤 나아가는 첫걸음이다.
* 역할을 구현하는 가장 일반적인 방법은 추상 클래스와 인터페이스.
* 처음에는 객체로 시작하고 반복적으로 책임과 협력을 정제해가면서 필요한 순간에 객체로부터 역할을 분리해내는 방법이 좋은 방법이다.
* 객체는 다양한 역할을 가질 수 있으며 협력에 참여할 때 협력 안에서 하나의 역할로 보여진다. 다른 협력에서는 또 다른 하나의 역할로 보여진다.

## 정리
1. 협력이라는 문맥을 갖추어야함.
2. 협력에 필요한 행동을 정의해야함.
3. 행동을 수행할 수 있는 역할, 객체를 찾기.
4. 객체가 필요한 행동을 정의하고 상태를 결정.
5. 협력 -> 역할 -> 객체 -> 클래스.