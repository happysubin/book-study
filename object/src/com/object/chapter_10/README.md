# Chapter 10. 상속과 코드 재사용

* 재사용 관점에서 상속이란 클래스 안에 정의된 인스턴스 변수와 메서드를 자동으로 새로운 클래스에 추가하는 구현 기법이다.
* 코드를 재사용하려는 강력한 동기 이면에는 중복된 코드를 제거하려는 욕망이 숨어 있다.
* 따라서 상속에 대해 살펴보기 전에 중복 코드가 초래하는 문제점을 살펴보자.

## part 1. 상속과 중복코드

* 중복코드는 여러 의심을 불러 일으킨다. 
* 이미 존재하는 코드인데 왜 다시 쓴거지?? 단순한 실수인가? 두 코드가 중복이 맞는가? 중복을 없애도 문제가 되는가?와 같은 의심을 심는다.
* 무엇보다 중복 코드의 제일 큰 문제는 변경을 방해하는 것이다.
* 프로그램의 본질은 비즈니스와 관련된 지식을 코드로 변환하는 것인데, 이 지식은 늘 변한다. 그러면 지식에 맞춰 코드도 변경해야 한다.
* 늘 새로운 코드를 추가하면, 이건 언젠가 바뀐다라고 생각하는 것이 현명하다.
* 중복 여부를 판단하는 기술은 변경이다. 요구사항이 변경됐을 때 두 코드를 함께 수정해야 한다면 이 코드는 중복이다.
* 함께 수정할 필요가 없다면 중복이 아니다.
* 중복 코드를 판단하는 것은 코드의 모양이 아니라, 코드가 변경에 반응하는 방식이다.
* 프로그래머들은 DRY 원칙을 따라야 하는데 Dont' repeat Yourself 구문의 첫 글자를 만든것이다. 요약하면 지식을 중복하지 말라는 것이다.
* DRU 원칙은 한 번, 단 한번 원칙 또는 단일 지점 제어 원칙이라고도 부른다.
* 중복 코드의 문제점을 확인하기 위해 전화 요금을 계산하는 애플리케이션 개발 실습을 진행했다. (section_1)
* Phone과 NightlyDiscountPhone 사이에는 중복 코드가 존재함. 이건 시한폭탄과 동일하다.

### 중복 코드 수정하기

* 이제 그럼 중복 코드가 코드 수정에 미치는 영향을 살펴보자. (section_2)
* 추가할 기능은 통화 요금에 부과할 세금을 계산하는 것이다. 부과되는 세율은 가입자의 핸드폰마다 다르다.
* 현재는 통화 요금 계산 로직이 Phone과 NightlyDiscountPhone 양쪽 모두에 구현돼 있기 때문에 세금을 추가하기 위해서는 두 클래스를 모두 수정해야 한다.
* 이 예제는 중복 코드가 가지는 단점을 잘 보여준다. 두 클래스를 모두 변경했음.
* 많은 코드 더미 속에서 어떤 코드가 중복인지를 파악하는 일은 쉬운 일이 아니다.
* 중복 코드는 항상 함께 수정돼야 하기 때문에 수정할 때 하나라도 빠트린다면 버그로 이어질 것이다.
* 로중복 코드를 제거하지 않은 상태에서 코드를 수정할 수 있는 유일한 방법은 새로운 중복 코드를 추가하는 것이다.
* 새로운 중복 코드를 추가하는 과정에서 코드의 일관성이 무너질 위험이 항상 도사리고 있다.
* 더 큰 문제는 중복 코드가 늘어날수록 애플리케이션은 변경에 취약해지고 버그가 발생할 가능성이 높아진다는 것이다.
* 중복 코드의 양이 많아질수록 버그의 수는 증가하며, 그에 비례해 코드를 변경하는 속도는 점점 더 느려진다.

### 타입 코드 사용하기

* 두 클래스 사이의 중복 코드를 제거하는 한 가지 방법은 클래스를 하나로 합치는 것이다.
* Phone과 NightlyDiscountPhone을 하나로 합침.
* 하지만 이러면 클래스는 낮은 응집도와 높은 결합도라는 문제에 시달리게 된다.
* 코드를 합치는 실습을 진행.

### 상속을 이용해서 중복 코드 제거하기

* 상속의 기본 아이디어는 이미 존재하는 클래스와 유사한 클래스가 필요하다면 코드를 복사하지 말고 상속을 이용해 코드를 재사용하는 것이다./
* NightlyDiscountPhone 클래스가 Phone 클래스를 상속받게 만들어서 사용할 것이다.
* super 참조를 통해 부모 클래스인 Phone의 calculateFee 메서드를 호출해서 일반 요금제에 따라 통화 요금을 계산한 후 이 값에서 통화 시작 시간이 10시 이후인 통화의 요금을 빼주는 부분이 존재.
* 이렇게 구현된 이유를 개발자의 의도를 바탕으로 p.319에서 설명해줌. 설명이 너무 길어서 생략.
* 설명보다 중요한 점은 개발자의 가정을 이해하기전에는 코드를 이해하기 어렵다는 것이다!!!
* 상속을 염두에 두고 설계되지 않은 클래스를 상속을 이용해 재사용하는 것은 쉽지 않다.
* 상속을 이용해 코드를 재사용하기 위해서는 부모 클래스의 개발자가 세웠던 가정이나 추론 과정을 정확하게 이용해야 한다.
* 이것은 자식 클래스의 작성자가 부모 클래스의 구현 방법에 대한 정확한 지식을 가져야 한다는 것을 의미한다.
* 따라서 상속은 결합도를 높인다. 그리고 상속이 초래하는 부모 클래스와 자식 클래스 사이의 강한 결합이 코드를 수정하기 어렵게 만든다.
* 상속을 바탕으로 세금 계산 요구사항이 추가되는 실습을 진행했다.
* 그러나 세금 계산하는 로직을 추가하기 위해 또 다시 새로운 중복 코드를 만들어야 했다.
* 이것은 NightlyDiscountPhone이 Phone의 구현에 너무 강하게 결합돼 있기 때문이다.
* 따라서 우리는 상속을 사용할 때 다음과 같은 경고에 귀를 기울여야 한다.
* 경고 1. 자식 클래스의 메서드 안에서 super 참조를 이용해 부모 클래스의 메서드를 직접 호출할 경우 두 클래스는 강하게 결합된다. super 호출을 제거할 수 있는 방법을 찾아 결합도를 제거하라.
* 지금까지 살펴본 예제들은 자식 클래스가 부모 클래스의 구현에 강하게 결합될 경우 부모 클래스의 변경에 의해 자식 클래스가 영향을 받는다는 사실을 잘 보여주었다.
* 이처럼 상속 관계로 연결된 자식 클래스가 부모 클래스의 변경에 취약해지는 현상을 가리켜 취약한 기반 클래스 문제라고 부른다.
* 이제 취약한 기반 클래스 문제의 몇 가지 사례를 샬펴보자.

## part 2. 취약한 기반 클래스 문제

* 상속은 자식 클래스와 부모 클래스의 결합도를 높인다.
* 부모 클래스의 변경에 의해 자식 클래스가 영향을 받는 현상을 취약한 기반 클래스 문제라고 부른다.
* 취약한 기반 클래스 문제는 상속이라는 문맥 안에서 결합도가 초래하는 문제점을 가리키는 용어다.
* 상속 관계를 추가할수록 전체 시스템의 결합도가 높아진다는 사실을 알고 있어야 한다.
* 상속은 자식 클래스를 점진적으로 추가해서 기능을 확장하는 데는 용이하지만 높은 결합도로 인해 부모 클래스를 점진적으로 개선하는 것은 어렵게 만든다.
* 취약한 기반 클래스의 문제는 캡슐화를 약화시키고 결합도를 높인다.
* 상속의 첫 번째 문제: 상속은 자식 클래스가 부모 클래스의 구현 세부사항에 의존하도록 만들기 때문에 캡슐화를 약화시킨다. 
* 객체지향의 기반은 캡슐화를 통한 변경의 통제다. 객체는 변경될지도 모르는 불안정한 요소를 캡슐화함으로써 파급효과를 걱정하지 않고도 자유롭게 내부를 변경할 수 있다.
* 자바 초기의 상속을 잘못 사용한 예제를 예시로 들었다. java.util.Properties, java.util.Stack.
* 경고 2. 상속받은 부모 클래스의 메서드가 자식 클래스의 내부 구조에 대한 규칙을 깨트릴 수 있다.
* 안 좋은 예시로 HashSet과 InstrumentHashSet 내용이 나왔다. p.327
* 클래스가 상속되기를 원한다면 상속을 위해 클래스를 설계하고 문서화해야 하며, 그렇지 않은 경우에는 상속을 금지시켜야 한다고 주장한다. (조슈아 블로치)
* 경고 3. 자식 클래스가 부모 클래스의 메서드를 오버라이딩할 경우 부모 클래스가 자신의 메서드를 사용하는 방법에 자식 클래스가 결합될 수 있다.
* 상속은 코드 재사용을 위해 캡슐화를 희생한다. 완벽한 캡슐화를 원한다면 코드 재사용을 포기하거나 상속 이외의 다른 방법을 사용해야 한다.

### 부모 클래스와 자식 클래스의 동시 수정 문제

* 음악 목록을 추가할 수 있는 플레이리스트 예제를 구현.
* 해당 예시에서는 자식 클래스가 부모 클래스의 메서드를 오버라이딩하거나 불필요한 인터페이스를 상속받지 않았음에도 부모 클래스를 수정할 때 자식 클래스를 함께 수정해야 할 수 있다는 사실을 보여준다.
* 상속을 사용하면서 자식 클래스가 부모 클래스의 구현에 강하게 결합되기 때문에 이 문제를 피하기는 어렵다.
* 결합도란 다른 대상에 대해 알고 있는 지식의 양이다. 
* 상속은 기본적으로 부모 클래스의 구현을 재사용한다는 기본 전제를 따르기 때문에 자식 클래스가 부모 클래스의 내부에 대해 상세히 알도록 강요한다.
* 그러므로 코드 재사용을 위한 상속은 부모 클래스의 자식 클래스를 강하게 결합시키기 때문에 함께 수정해야 하는 상황 역시 빈번하게 발생할 수 밖에 없다.
* 경고 4. 클래스를 상속하면 결합도로 인해 자식 클래스와 부모 클래스의 구현을 영원히 변경하지 않거나, 자식 클래스와 부모 클래스를 동시에 변경하거나 둘 중 하나를 선택할 수 밖에 없다.

## part 3. Phone 다시 살펴보기

* 이제 상속으로 인한 피해를 최소화할 수 있는 방법에 대해 찾아보자.
* 문제 해결의 열쇠는 바로 추상화다.

### 추상화에 의존하자

* 이전 NightlyDiscountPhone의 문제점은 Phone에 강하게 결합돼 있기 때문에 Phone이 변경될 경우 함께 변경될 가능성이 높다는 것이다.
* 이 문제를 해결하는 가장 일반적인 방법은 자식 클래스가 부모 클래스의 구현이 아닌 추상화에 의존하도록 만드는 것이다.
* 정확하게 말하면 부모 클래스와 자식 클래스 모두 추상화에 의존하도록 수정해야 한다.
* 코드 중복을 제거하기 위해 상속을 도입할 때 따르는 선생님의 2가지 원칙이 있다.
  1. 두 메서드가 유사하게 보인다면 차이점을 메서드로 추출하라. 메서드 추출을 통해 두 메서드를 동일한 형태로 보이도록 만들 수 있다.
  2. 부모 클래스의 코드를 하위로 내리지 말고 자식 클래스의 코드를 상위에 올려라. 부모 클래스의 구체적인 메서드를 자식 클래스로 내리는 것보다 자식 클래스의 추상적인 메서드를 부모 클래스로 올리는 것이 재사용성과 응집도 측면에서 더 뛰어난 결과를 얻을 수 있다.
* 중복 코드 안에서 차이를 메서드로 추출하는 코드 예제를 살펴봄.

### 중복 코드를 부모 클래스를 옮겨라

* 모든 클래스들이 추상화에 의존하도록 만들자. 따라서 부모 클래스를 추상 클래스로 구현해야한다.
* AbstractPhone이라는 추상 클래스이면서 Phone과 NightlyDiscountPhone 부모 클래스를 추가함.
* 그리고 추상화에 의존하는 코드 예제를 살펴봄.
* 예제를 통해 자식 클래스들 사이의 공통점을 부모 클래스로 옮김으로써 실제 코드를 기반으로 상속 계층을 구성했다.
* 이제 설계가 추상화에 의존하게 되었다. (템플릿-메서드 패턴 같다)

### 추상화가 핵심이다.

* 공통 코드를 이동시킨 후에 각 클래스는 서로 다른 변경의 이유를 가진다는 것에 주목해라.
* Abstract Phone은 전체 통화 목록을 계산하는 방법이 바뀔 경우에만 변경된다. 다른 클래스들은 요금제에 따라 통화 한건을 계산한다.
* 우리가 방금 살펴본 코드 예제에서 클래스들은 단일 책임 원칙을 준수하기 때문에 응집도가 높다.
* 설계를 변경하기 전에는 자식 클래스인 NightlyDiscountPhone이 부모 클래스인 Phone의 구현에 강하게 결합돼 있었기 때문에 Phone의 구현을 변경하더라도 NightlyDiscountPhone도 함께 영향을 받았었다.
* 이제 NightlyDiscountPhone은 부모 클래스의 구현에 의존하지 않고 추상화에 의존한다. 정확하게는 부모 클래스에서 정의한 추상 클래스인 calculateCallFee에만 의존한다.
* calculateCallFee 메서드의 시그니처가 변경되지 않는 한 부모 클래스의 내부 구현이 변경되더라도 자식 클래스는 영향을 받지 않는다.
* 이 설계는 낮은 결합도를 유지하고 있다.
* 사실 부모 클래스 역시 자신의 내부에 구현된 추상 메서드를 호출하기 때문에 추상화에 의존한다고 말할 수 있다.
* 새로운 요금제를 추상화하기도 쉬워졌다. 새로운 요금제가 필요하다면 부모 추상클래스를 상속받아 calculateCallFee 메서드만 오버라이딩하면 된다.
* 현재의 설계는 확장에는 열려있고 변경에는 닫혀 있으므로 OCP 규칙을 준수한다.
* 이 모든 것은 추상화를 기반으로 얻어진 장점들이다.
* 상속 계층이 코드를 진화시키는데 걸림돌이 된다면 추상화를 찾아내고 상속 계층 안의 클래스들이 그 추상화에 의존하도록 코드를 리팩토링하자.
* 차이점을 메서드로 추출하고 공통적인 부분은 부모 클래스로 이동하라.
* 또한 Phone이 아니라 의도를 드러낼 수 있는 RegularPhone라는 이름이 더 좋다.

### 세금 요구사항 추가하기

* 추상화를 기반으로 한 상속 관계는 세금 요구사항을 추가하기 훨씬 수월했다. (실습 코드 확인)
* 물론 클래스 사이의 상속은 행동 뿐만 아니라 인스턴스도 결합되게 만드므로 자식 클래스에서 부모 클래스 생성자를 호출해야했다.
* 자식 클래스는 자신의 인스턴스를 생성할 때 부모 클래스에 정의된 인스턴스 변수를 초기화해야 하기 때문에 자연스럽게 부모 클래스에 추가된 인스턴스 변수는 자식 클래스의 초기화 로직에 영향을 미치게 된다.
* 결과적으로 책임을 아무리 잘 분리하더라도 인스턴스 변수의 추가는 종종 상속 계층 전반에 걸친 변경을 유발한다.
* 그래도 인스턴스 초기화 로직을 변경하는 것이 두 클래스에 동일한 세금 계산 코드를 중복시키는 것보다는 현명한 선택이다.
* 객체 생성 로직에 대한 변경을 막기 보다는 핵심 로직의 중복을 막아라.
* 핵심 로직은 한 곳에 모아 놓고 조심스럽게 캡슐화해야 한다. 그리고 공통적인 핵심 로직은 최대한 추상화해야 한다.

### 간단한 정리

* 상속으로 인한 클래스 사이의 결합을 피할 수 있는 방법은 없다. 결국 부모 클래스와 자식 클래스가 결합된다.
* 메서드 구현에 대한 결합은 추상 메서드를 사용해 완화가 되더라도 인스턴스 변수에 대한 잠재적인 결합을 제거할 수 잇는 방법은 없다.
* 우리가 원하는 것은 행동을 변경하기 위해 인스턴스 변수를 추가하더라도 상속 계층 전체에 걸쳐 부작용이 퍼지지 않게 막는 것이다.

## part 4. 차이에 의한 프로그래밍

* 기존 코드와 다른 부분만을 추가함으로써 애플리케이션의 기능을 확장하는 방법을 차이에 의한 프로그래밍이라고 부른다.
* 상속을 이용하면 이미 존재하는 클래스의 코드를 쉽게 재사용할 수 있기 때문에 애플리케이션의 점진적인 정의가 가능해진다.
* 프로그래밍 세계에서 중복 코드는 악의 근원이다. 따라서 중복 코드를 제거하기 위해 최대한 코드를 재사용해야 한다.
* 재사용 가능한 코드란 심각한 버그가 존재하지 않는 코드다. 따라서 코드를 재사용하면 코드의 품질은 유지하면서 코드를 작성하는 노력과 테스트를 줄일 수 있다.
* 중복 코드를 제거하고 코드 재사용할 수 있는 방법에서 제일 유명한 것은 상속이다.
* 그러나 코드를 재사용하기 위해 맹목적으로 상속을 사용하는 것은 위험하다.
* 상속은 정말 필요한 경우에만 사용해야 한다.
* 상속은 코드 재사용과 관련된 대부분의 경우 우아한 해결 방법이 아니다.
* 상속의 단점을 피하면서도 코드를 재사용할 수 있는 더 좋은 방법이 있는데, 그것이 바로 합성이다.