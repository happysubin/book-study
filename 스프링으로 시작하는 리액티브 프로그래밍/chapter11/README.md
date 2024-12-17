
# chapter 11. Context

### 11.1 Context란?

프로그래밍 용어로 Context란 __'어떤 것을 이해하는 데 도움이 될 만한 관련 정보나 이벤트, 상황'__ 으로 해석할 수 있다.
즉 __Context는 어떤 상황에서 그 상황을 처리하기 위한 정보__ 라고 볼 수 있다.

Reactor에서 Context는 Operator 같은 Reactor 구성 요소 간에 전파되는 key/value 형태의 저장소라고 정의하는데, 여기서의 '전파'는 Downstream에서
Upstream으로 Context가 전파되어 Operator 체인상의 각 Operator가 해당 Context의 정보를 동일하게 이용할 수 있음을 의미한다.

Reactor의 Context는 ThreadLocal과 다소 유사한 면이 있지만, 각각의 실행 스레드와 매핑되는 ThreadLocal과 달리 실행 스레드와 매핑되는 것이 아니라
__Subscriber와 매핑__ 된다.

즉, __구독이 발생할 때마다 해당 구독과 연결된 하나의 Context가 생긴다__ 고 보면 된다.

예제 Example11_1을 통해 Context에서 쓰고 읽는 코드를 살펴봄.

Context에서 데이터를 읽는 방식은 크게 두가지다.

1. 원본 데이터 소스 레벨에서 읽음
2. Operator 체인의 중간에서 읽음

Reactor에서는 Operator 체인상의 서로 다른 스레드들이 Context의 저장된 데이터에 손쉽게 접근이 가능하다.

그리고 context.put()을 통해 Context에 데이터를 쓴 후에 매번 불번 객체를 다음 ContextWrite() Operator로 전달함으로써 스레드 안전성을 보장한다.

### 11.2 자주 사용되는 Context 관련 API

Context

* put(key, value): key value 형태로 Context에 값을 넣는다.
* of(key1, value2, key2, value2, ...): key value 형태로 Context에 여러 개의 값을 쓴다.
* putAll(ContextView): 현재 Context와 파라미터로 입력된 ContextView를 머지한다.
* delete(key): Context에서 key에 해당하는 Value를 삭제한다.

ContextView

* get(key): ContextView에서 key에 해당하는 value를 반환한다.
* getOrEmpty(key): ContextView에서 key에 해당하는 value를 Optional로 래핑해서 반환한다.
* getOfDefault(key, default, value): ContextView에서 key에 해당하는 value를 가져온다. key에 해당하는 value가 없으면 deafult value를 가져온다.
* hasKey(key): ContextView에서 특정 key가 존재하는지를 확인한다.
* isEmpty(): Context가 비어 있는지 확인한다.
* size(): Context 내에 있는 key value의 개수를 반홚난다.

ContextView를 활용하는 것은 Java Map을 다루는 것과 매우 유사하다.

### 11.3 Context의 특징

* Context는 구독이 발생할 대마다 하나의 Context가 해당 구독에 연결된다.
* Context는 구독별로 연결되는 특징이 있기 때문에 구독이 발생할 때마다 해당하는 하나의 Context가 하나의 구독에 연결된다.
* Context는 Operator 체인의 아래에서 위로 전파도니다.
* 동일한 키에 대한 값을 중복해서 저장하면 Operator 체인에서 가장 위쪽에 위치한 contextWrite()이 저장한 값으로 덮어쓴다.
* 일반적으로 모든 Operator에서 Context에 저장된 데이터를 읽을 수 있도록 contextWrite()을 Operator 체인의 맨 마지막에 둔다.
* Inner Sequence 내부에서는 외부 Context에 저장된 데이터를 읽을 수 있다.
* Inner Sequence 외부에서는 Inner Sequence 내부 Context에 저장된 데이터를 읽을 수 없다.
* Context는 인증 정보 같은 직교성(독립성)을 가지는 정보를 전송하는데 적합하다.
