# chapter 11. null 대신 Optional 클래스

## section 11.1 값이 없는 상황을 어떻게 처리할까?

* null인 객체에서 메서드나 필드를 호출하면 NullPointerException이 발생한다.
* 예기치 않은 NullPointerException을 피하려면 어떻게 해야 할까?
* 필요한 곳에 다양한 null 확인 코드를 삽입해야 함. 이와 같은 반복 패턴 코드를 '깊은 의심'이라고 부른다.
* 즉, 변수가 null인지 의심되어 중첩 if 블록을 추가하면 코드 들여쓰기 수준이 증가한다.

### null 때문에 발생하는 문제

* 에러의 근원이다.
* 코드를 어지럽힌다. -> null 확인 코드로 코드 가독성이 떨어진다.
* 아무 의미가 없다. -> null은 아무 의미도 표현하지 않음.
* 자바 철학에 위배된다. -> 자바는 개발자로부터 모든 포인터를 숨김. 특히 정적 형식 언어에서 값이 없음을 표현하는 방법으로는 적절하지 않다.
* 형식 시스템에 구멍을 만든다. -> null은 무형식이며 정보를 포함하고 있지 않으므로 모든 참조 형식에 null을 할당할 수 있다.

## section 11.2 Optional 클래스 소개

* Optional은 선택형 값을 캡슐화하는 클래스다.
* 값이 있으면 Optional 클래스는 값을 감싼다. 값이 없으면 Optional.empty 메서드로 Optional을 반환한다.
* Optional.empty는 Optional의 특별한 싱글턴 인스턴스를 반환하는 정적 팩터리 메서드다.
* 모든 null 참조를 Optional로 대치하는 것은 바람직하지 않다.
* Optional의 역할은 더 이해하기 쉬운 API를 설계하도록 돕는 것이다.
* 즉 메서드의 시그니처만 보고도 선택형값인지 여부를 구별할 수 있다.
* Optional이 등장하면 이를 언랩해서 값이 없을 수 있는 상황에 적절하게 대응하도록 강제하는 효과가 있다.

## section 11.3 Optional 적용 패턴

* 정적 팩토리 메서드 Optional.of로 null이 아닌 값을 포함하는 Optional을 만들 수 있다.
* 정적 팩토리 메서드 Optional.empty로 빈 Optional 객체를 얻을 수 있다.
* 정적 팩토리 메서드 Optional.ofNullable로 null 값을 저장할 수 있는 Optional을 만들 수 있다.
* map으로 Optional 값을 추출하고, flatMap으로 Optional 객체를 연결하는 예제를 살펴봄. p.367 자세한 설명이 있다.
* Optional 클래스의 stream 메서드를 통해 Optional 스트림을 조작할 수 있다.

### Optional 언랩

* get 메서드는 가장 간단한 메서드면서 제일 안전하지 않다. 사용 X
* orElse 메서드는 Optional이 값을 포함하지 않을 때 기본값을 제공할 수 있다.
* orElseGet(Supplier <? extends T>)는 orElse 메서드에 대응하는 게으른 버전의 메서드다. Optional 값이 없을 때만 Supplier가 실행되기 때문이다.
* orElseThrow는 Optional이 비었을 때 예외를 발생시킨다. 이 메서드는 발생시킬 예외의 종류를 선택핢 수 있다.
* ifPresent 메서드는 값이 존재할 때 인수로 넘겨준 동작을 실행할 수 있다.
* ifPresentOrElse 해당 메서드는 Optional이 비었을 때 실행할 수 있는 Runnable을 인수로 받는다는 점만 ifPresent와 다르다.
* filter 메서드는 프레디케이트를 인수로 받는다. 값이 존재하며 프레디케이트와 일치하면 값을 Optional을 반환하고, 값이 없거나 프레디케이트와 일치하지 않으면 빈 Optional을 반환한다.

## section 11.4 Optional을 사용한 실용 예제

* 잠재적으로 null이 될 수 있는 대상을 Optional로 감싼다. Optional.ofNullable 메서드를 이용.
* 기본형 특화 Optional 사용은 권장하지 않는다.
* 응용 예제와 유틸리티 클래스 작성을 살펴봄.

## section 11.5 정리

* 역사적으로 프로그래밍 언어에서는 null 참조로 값이 없는 상황을 표현해왔다.
* 자바 8에서는 값이 있거나 없음을 표현할 수 있는 클래스 java.util.Optional<T>를 제공한다.
* 팩터리 메서드 Optional.empty, Optional.of, Optional.ofNullable 등을 이용해서 Optional 객체를 만들 수 있다.
* Optional 클래스는 스트림과 비슷한 연산을 하는 map, flatMap, filter 등의 메서드를 제공한다.
* Optional로 값이 없는 상황을 적절하게 처리하도록 강제할 수 있다. 즉, Optional로 예상치 못한 null 예외를 방지할 수 있다.
* Optional을 활용하면 더 좋은 API를 설계할 수 있다. 즉, 사용자는 메서드의 시그니처만 보고도 Optional 값이 사용되거나 반환되는지 예측할 수 있다.