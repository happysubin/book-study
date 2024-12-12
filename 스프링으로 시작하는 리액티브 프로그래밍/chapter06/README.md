
# 06. 마블 다이어그램

### 마블 다이어그램이란?

마블 다이어그램은 여러 가지 구슬 모양의 도형으로 구성된 도표로 Reactor에서 지원하는 Operator를 이해하는데 중요한 역할을 한다.

![KakaoTalk_Photo_2024-12-12-21-28-36](https://github.com/user-attachments/assets/5a4bba7a-53e9-428d-b4c7-1f30dc5b7ea8)

1. 다이어그램에는 두 개의 타임라인이 존재하는데, 첫째가 바로 1과 같이 Publisher가 데이터를 emit하는 타임라인이다. 그림에서는 단순히 Publlisher의 타임라인이라고 했지만 이 Publisher는 데이터 소스를 최초로 emit 하는 
   Publisher일 수도 있고 그렇지 않을 수도 있다. Operator 함수(5)를 기준으로 Upstream의 Publisher라고 보는 것이 적절하다. (Reactor에서는 Flux의 경우, Source Flux라고도 부른다)
2. 2는 Publisher가 emit하는 데이터를 의미한다. 타임라인은 왼쪽에서 오른쪽으로 시간이 흐르는 것을 의미하기 때문에 가장 왼쪽에 있는 1번 구슬이 시간상으로 가장 먼저 emit된 데이터입니다.
3. 3의 수직으로 된 바는 데이터의 emit이 정상적으로 끝났음을 의미한다. onComplete Signal에 해당한다.
4. 4와 같이 Operator 함수쪽으로 들어가는 점섬 화살표는 Publisher로부터 emit된 데이터가 Operator 함수의 입력으로 전달되는 것을 의미한다.
5. 5는 Publisher로부터 전달받은 데이터를 처리하는 Operator 함수다. 위 사진에서 예시는 map() Operator 함수를 보여준다.
6. 6과 같이 Operator 함수에서 나가는 점선 화살표는 Publisher로부터 전달받은 데이터를 가공 처리한 후에 출력으로 내보내는 것을 의미한다. 출력으로 내보낸다는 의미는 정확하게 표현하자면, Operator 함수에서 리턴하는 새로운 Publisher를 이용해 Downstream에 가공된 데이터를 전달하는 것을 의미한다.
7. 7에서의 타임라인은 Operator 함수에서 가공 처리되어 출력으로 낸보내진 데이터의 타임라인이다.
8. 8은 Operator 함수에서 가공 처리된 데이터를 의미한다.
9. 마지막으로 9와 같은 'X'표시는 에러가 발생해 데이터 처리가 종료되었음을 의미하며, onError Signal에 해당된다.

마블 다이어그램을 활용해 Operator를 설명해 둔 API를 읽어보면 이해가 쉽다.


### 6.2 마블 다이어그램으로 Reactor의 Publisher 이해하기

* Mono는 오직 0건 또는 1건의 데이터를 emit하는 Publisher이다. Flux는 1건 이상.
* Mono는 1건의 데이터를 응답으로 보내는 HTTP 응답에 매우 적절힌 Publisher


코드 예제를 통해 더 살펴봄.