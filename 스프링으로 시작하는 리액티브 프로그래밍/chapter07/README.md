
# chapter 07. Cold Sequence와 Hot Sequence

### 7.1 Cold와 Hot의 의미

__Cold는 무언가를 새로 시작하고, Hot은 무언가를 새로 시작하지 않는다.__

### 7.2 Cold Sequence

* Sequence는 Publisher가 emit하는 데이터의 연속적인 흐름을 정의해 놓은 것으로 코드로 표현하면 Operator 체인 형태로 정의된다.
* Cold Sequence는 Subscriber가 구독할 때마다 데이터 흐름이 처음부터 다시 시작되는 Sequence다.
* Subscriber 구독 시점이 달라도 구독을 할 때마다 Publisher가 데이터를 emit하는 과정을 처음부터 다시 시작하는 데이터의 흐름을 Cold Sequence라고 한다.
* 그리고 이 Cold Sequence 흐름으로 동작하는 Publisher를 Cold Publisher라고 한다. 
* 결과적으로 Cold Sequence의 경우, Sequence 타임라인이 구독을 할 때마다처럼 하나씩 더 생긴다고 볼 수 있다.

### 7.3 Hot Sequence

* Hot Sequence의 경우 구독이 발생한 시점 이전에 Publisher로부터 emit된 데이터는 Subscriber가 전달받지 못하고 구독이 발생한 시점 이후에 emit된 데이터만 전달받을 수 있다.
* Hot Sequence의 경우 구독이 아무리 많이 발생해도 Publisher가 데이터를 처음부터 emit하지 않는다.
* Publisher가 데이터를 emit하는 과정이 한 번만 일어나고, Subscriber가 각각의 구독 시점 이후에 emit된 데이터만 전달받는 데이터의 흐름을 Hot Sequence라고 한다.
* Hot Sequence의 경우 Cold Sequence와 반대로 구독 횟수와는 상관없이 Sequence 타임라인이 하나만 생긴다.

### 7.4 HTTP 요청과 응답에서 Cold Sequence와 Hot Sequence의 동작 흐름

HTTP 예제 코드를 사용한 Hot Sequence와 Cold Sequence 예시 코드를 살펴봄. cache() Operator를 통해 Cold Sequence를 Hot Sequence로 변경
cache()는 Operator는 Cold Sequence로 동작하는 Mono를 Hot Sequence로 변경해주고 emit된 데이터를 캐시한 뒤, 구독이 발생할 때마다 캐시된 데이터를 전달해준다.