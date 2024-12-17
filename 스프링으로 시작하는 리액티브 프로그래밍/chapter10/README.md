
# chapter 10. Scheduler


### 10.1 스레드(Thread)의 개념 이해

Reactor에서 사용되는 Scheduler는 Reactor Sequence에서 사용되는 스레드를 관리해주는 관리자 역할을 한다.

컴퓨터 시스템에서 스레드는 크게 물리적인 스레드와 논리적인 스레드로 구분한다.
물리적인 스레드를 이해하기 위해서는 CPU의 코어가 무엇인지 알아야 한다.

#### 물리적인 스레드

* 코어는 CPU의 명령어를 처리하는 반도체 유닛이다.
* 일반적으로 코어의 개수가 많으면 더 많은 수의 명령어를 더 빠르게 병렬로 처리할 수 있다.
* 만약 컴퓨터의 CPU 사양에 '듀얼 코어 4 스레드'라고 표기 되었다면 이렇게 표기된 4 스레드는 물리적인 스레드를 의미한다고 볼 수 있다.

#### 논리적인 스레드

* 논리적인 스레드는 소프트웨어적으로 생성되는 스레드를 의미하며, Java 프로그래밍에서 사용되는 스레드는 바로 이러한 논리적인 스레드라고 볼 수 있다.
* 논리적인 스레드는 우리가 흔히들 프로그램이라고 부르는 프로세스 내에서 실행되는 세부 작업의 단위가 된다.
* 논리적인 스레드는 이론적으로는 메모리가 허용하는 범위 내에서 얼마든지 만들 수 있지만 물리적인 스레드의 가용 범위 내에 서 실행될 수 있다.

물리적인 스레드는 병렬성과 관련이 있으며, 논리적인 스레드는 동시성과 관련이 있다.

병렬성은 물리적인 스레드가 실제로 동시에 실행되기 때문에 여러 작업을 동시에 처리함을 의미한다.
동시성은 용어 자체의 의미 때문에 동시에 실행된다고 생각할 수 있지만 동시에 실행되는 것처럼 보이는 것을 의미한다.

무수히 많은 논리적인 스레드가 네 개의 물리적인 스레드를 아주 빠른 속도로 번갈아가며 사용하면서 마치 동시에 실행되는 것처럼 보이는 동시성을 가진다.

### 10.2 Scheduler란?


운영체제 레벨에서의 Scheduler는 실행되는 프로그램인 프로세스를 선택하고 실행하는 등 프로세스의 라이프 사이클을 관리해주는 관리자 역할을 한다.

이와 유사하게 Reactor의 Schduler는 비동기 프로그래밍을 위해 사용되는 스레드를 관리해주는 역할을 한다.

다시 말하면 Scheduler를 사용하여 어떤 스레드에서 무엇을 처리할지 제어한다.

자바 프로그래밍에서 멀티 스레드를 완벽하게 제어하는 것은 무척 어렵다.
스레드 간의 경쟁 조건 등을 신중하게 고려해서 코드를 작성해야 하는데, 이로 인해 코드의 복잡도가 높아지고 결과적으로 예상치 못한 오류가 발생할 가능성이 높아진다.

Reactor에서는 Schduler를 통해 이런 문제를 최소화한다.
Scheduler를 사용해 코드 자체가 매우 간결해지고, 스레드의 제어를 대신해주므로 개발자가 직접 스레드를 제어해야 하는 부담에서 벗어날 수 있다.

### 10.3 Scheduler를 위한 전용 Operator

Reactor에서 Scheduler는 전용 Operator를 통해 사용할 수 있다.

subscribeOn(), publishOn() Operatir은 모두 Scheduler 전용 Operator이다.
그리고 이 두개 Operator의 파라미터로 적절한 Scheduler를 전달하면 해당 Scheduler의 특성에 맞는 스레드가 Reactor Sequence에 할당한다.
이제 몇 가지 Operator를 살펴보겠다. 

#### subscribeOn()

* subscribeOn() Operator는 그 이름처럼 구독이 발생한 직후 실행될 스레드를 지정한다.
* subscribeOn() Operator는 구독 시점 직후에 실행되기 때문에 원본 Publisher의 동작을 수행하기 위한 스레드라고 볼 수 있다.

#### publishOn()

* publishOn() Operator는 Downstream으로 Signal을 전송할 때 실행되는 스레드를 제어하는 역할을 하는 Operator라고 할 수 있다.
* publishOn() Operator는 코드상에서 publishOn()을 기준으로 아래쪽인 Downstream의 실행 스레드를 변경한다.
* subscribeOn()과 마찬가지로 파라미터로 Scheduler를 지정함으로써 해당 Scheduler의 특성을 가진 스레드로 변경할 수 있다.

#### parallel()

* subscribeOn() Operator와 publishOn() Operator의 경우, 동시성을 가지는 논리적인 스레드에 해당되지만 parallel() Operator는 병렬성을 가지는 물리적인 스레드에 해당된다.
* parallel()의 경우, 라운드 로빈 방식으로 CPU 코어 개수만큼의 스레드를 병렬로 실행한다.
* 여기서 말하는 CPU 코어 개수는 물리적인 코어 개수가 아니라 논리적인 코어(물리 스레드) 개수를 의미한다.

### 10.4 publishOn()과 subscribeOn()의 동작 이해

* Example10_5 코드에서 publishOn()과 subscribeOn()을 사용하지 않는 경우를 확인
* Example10_6 에서는 PublishOn()을 추가해 Operator 체인에서 실행되는 스레드의 동작 과정을 살펴봄
* Example10_7 에서는 PublishOn()을 2개 추가해 Operator 체인에서 실행되는 스레드의 동작 과정을 살펴봄
* Example10_8 에서는 PublishOn()과 sbscribeOn()을 추가해 Operator 체인에서 실행되는 스레드의 동작 과정을 살펴봄

### 10.5 Scheduler의 종류

* Schedulers.immediate(): 별도의 스레드를 추가적으로 생성하지 않고, 현재 스레드에서 작업을 처리하고자 할 때 사용 
* Schedulers.single(): 스레드 하나만 생성해서 Scheduler가 제거되기 전까지 재사용하는 방식. 하나의 스레드로 다수의 작업을 처리하므로 지연시간이 짧은 작업을 처리하는 것이 효과적
* Schedulers.newSingle(): Schedulers.single()이 하나의 스레드를 재사용하는 반면에, Schedulers.newSingle()은 호출할 때마다 매번 새로운 스레드 하나를 생성
* Schedulers.boundedElastic()
  * Schedulers.boundedElastic()는 ExecutorService 기반의 스레드 풀을 생성한 후, 그 안에서 정해진 수만큼의 스레드를 사용해 작업을 처리하고 작업이 종료된 스레드는 반납하여 재사용하는 방식
  * 기본적으로 CPU 코어 수 * 10만큼의 스레드를 생성하며, 풀에 있는 모든 스레드가 작업을 처리하고 있다면 이용 가능한 스레드가 생길 때까지 최대 100,000개의 작업이 큐에서 대기할 수 있다.
  * Schedulers.boundedElastic()은 Blocking I/O 작업을 효과적으로 처리하기 위한 방식
  * 실행 시간이 긴 Blocking I/O 작업이 포함된 경우, 다른 Non-Blocking 처리에 영향을 주지 않도록 전용 스레드를 할당해서 Blocking I/O 작업을 처리하기 때문에 처리 시간을 효율적으로 사용할 수 있다.
* Schedulers.parallel(): Schedulers.parallel()은 Non-Blocking I/O에 최적화되어 있는 Scheduler로서 CPU 코어 수만 큼 스레드를 생성한다.
* Schedulers.fromExecutorService(): 기존에 이미 사용하고 있는 ExecutorService가 있다면 이 ExecutorService로부터 Scheduler를 생성하는 방식. Reactor에서 이 방식은 추천 X
* Schedulers.newXXXX(): 새로운 Scheduler 인스턴스를 생성. 즉 스레드 이름, 생성 가능한 디폴트 스레드의 개수, 스레드의 유휴 시간, 데몬 스레드의 동작 여부 등을 직접 지정해서 커스텀 스레드 풀을 새로 생성할 수 있다.

