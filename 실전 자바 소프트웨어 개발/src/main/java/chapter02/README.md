# chapter 02 프로젝트 요구사항

* 은행 입출금 내역의 총 수입과 총 지출은 각각 얼마인가? 결과가 양수인가? 음수인가?
* 특정 달엔 몇 건의 입출금 내역이 발생했는가?
* 지출이 가장 높은 상위 10건은 무엇인가?
* 돈을 가장 많이 소비하는 항목은 무엇인가?

<br>

### Section 5.

코드를 구현할 대는 __코드 유지보수성__ 을 높이기 위해 노력한다. 이는 무슨 뜻이지?

아래는 구현 코드가 가졌으면 하는 속성을 정리했다.

* 특정 기능을 담당하는 코드를 쉽게 찾을 수 있어야 한다.
* 코드가 어떤 일을 수행하는지 쉽게 이해할 수 있어야 한다.
* 새로운 기능을 쉽게 추가하거나 기존 기능을 쉽게 제거할 수 있어야 한다.
* 캡슐화가 잘 되어 있어야 한다. 즉 코드 사용자에게는 세부 구현 내용이 감춰져 있으므로 사용자가 쉽게 코드를 이해하고, 기능을 바꿀 수 있어야 한다.

지금처럼 새로운 요구사항이 생길 때마다 복사, 붙여넣기로 문제를 해결하면 다음과 같은 문제가 생긴다.

효과적이지 않은 방법이며 이를 안티패턴이라고 부른다.

* 하나의 거대한 갓 클래스 때문에 코드를 이해하기가 어렵다. (너무 많은 책임을 가지고 있음)
* 모드 중복 때뭉네 코드가 불안정하고 변화에 쉽게 망가진다. (변경 시 중복 코드 전체를 수정해야 함, 코드를 수정하므로 버그 발생률 상승)

<br>

### Section 6.

단일 책임 원칙(SRP)은 쉽게 관리하고 유지보수하는 코드를 구현하는 데 도움을 주는 포괄적인 소프트웨어 개발 지침이다.

두 가지를 보완하기 위해 SRP를 적용한다. 일반적으로 메서드와 클래스에 적용

* 한 클래스는 한 기능만 책임진다.
* 클래스가 바뀌어야 하는 이유는 오직 하나여야 한다.

이제 기존의 코드에서 

1. 입력 읽기
2. 주어진 형식의 입력 파싱
3. 결과 처리
4. 결과 요약 리포트

이 네 부분을 분리하자.

<br>

### Section 7.

실무에서는 일반적으로 다음과 같은 여섯 가지 방법으로 그룹화한다.

1. 기능
2. 정보
3. 유틸리티
4. 논리
5. 순차 
6. 시간

| 응집도 수준        | 장점              | 단점                  |
|---------------|-----------------|---------------------|
| 기능(높은 응집도)    | 이해하기 쉬움         | 너무 많은 클래스 생성        |
| 정보(중간 응집도)    | 유지보수하기 쉬움       | 불필요한 디펜던시           |
| 순차(중간 응집도)    | 관련 동작을 찾기 쉬움    | SRP를 위배할 수 있음       |
| 논리(중간 응집도)    | 높은 수준의 카테고리화 제공 | SRP를 위배할 수 있음       |
| 유틸리티(낮은 응집도)  | 간단히 추가 가능       | 클래스의 책임을 파악하기 어려움   |
| 시간(낮은 응집도)    | 판단불가            | 각 동작을 이해하고 사용하기 어려움 |


### Section 8.

* 결합도를 낮추기 위해 인터페이스 적용
* 결합도를 낮추기 위한 방법으로는 중간 객체, 인터페이스, 이벤트가 있다.


<br>

> 커버리지는 분기 커버리지가 좋다.