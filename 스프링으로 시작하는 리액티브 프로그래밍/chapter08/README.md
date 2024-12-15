
# chapter 08. Backpressure


### 8.1 Backpressure란?


리액티브 프로그래밍에서의 배압, 즉 Backpressure는 콘서트장에서 관객의 입장을 통제하는 안전요원에 비유할 수 있다.
Publisher가 끊임없이 emit하는 무수히 많은 데이터를 적절하게 제어하여 데이터 처리에 과부하가 걸리지 않도록 제어하는 것이 Backpressure의 역할이다.

예를 들어 Publisher가 데이터 1을 Subscriber에게 emit한다.
그런데 Subscriber가 data 1을 처리하는 속도가 느려서 처리가 끝나기도 전에, Publisher가 data 2부터 N까지 계속 emit할 수 있다.
이럴 경우 emit된 데이터들은 Subscriber가 data 1을 처리 완료하기 전까지 대기하게 된다.

data1의 처리 속도가 너무 느린 반면 Publisher는 아주 빠른 속도로 데이터를 끊임없이 emit하게 되면 어떤 일이 발생할까?
처리되지 않고 대기 중인 데이터가 지속적으로 쌓이게 되면 오버플로가 발생하거나 최악의 경우에는 시스템이 다운되는 문제가 발생한다.

이런 문제를 해결하기 위한 수단이 바로 Backpressure이다.

### 8,2 Reactor에서의 Backpressure 처리 방식

그럼 Reactor에서 지원하는 Backpressure 처리 방식에는 무엇이 있을까? 

1. 데이터 개수 제어

* Subscriber가 적절히 처리할 수 있는 수준의 데이터 개수를 Publisher에게 요청하는 것이다.
* Subscriber가 request() 메서드를 통해서 적절한 데이터 개수를 요청하는 방식
* Example8_1 코드 참고

2. Backpressure 전략 사용

Reactor에서는 Backpressure를 위한 다양한 전략을 제공한다.
Reactor에서 지원하는 Backpressure 전략을 아래에서 확인해보자.

* IGNORE 전략: Backpressure를 적용하지 않는다. 
* ERROR 전략: Downstream으로 전달할 데이터가 버퍼에 가득 찰 경우, Exception을 발생시키는 전략 (Example8_2 코드 참고)
* DROP 전략: Downstream으로 전달할 데이터가 버퍼에 가득 찰 경우, 버퍼 밖에서 대기하는 먼저 emit된 데이터부터 Drop 시키는 전략 (Example8_3 코드 참고)
  * 버퍼에 데이터가 가득차있으면 데이터 소비될때까지 대기하는 emit 된 데이터를 버림. 이후 데이터가 전부 소비되면 다시 emit된 데이터를 버퍼에 넣어서 소비함
* LATEST 전략: Downstream으로 전달할 데이터가 버퍼에 가득 찰 경우, 버퍼 밖에서 대기하는 가장 최근에(나중에) emit된 데이터부터 버퍼에 채우는 전략 (Example8_4 코드 참고)
  * DROP 전략은 버퍼가 가득 찰 경우 버퍼 밖에서 대기중인 데이터를 하나씩 차례로 DROP 하면서 폐기, LATEST 전략은 새로운 데이터가 들어오는 시점에 가장 최근의 데이터만 남겨두고 나머지 데이터를 폐기
* BUFFER 전략: Downstream으로 전달할 데이터가 버퍼에 가득 찰 경우, 버퍼 안에 있는 데이터부터 Drop 시키는 전략
  * Backpressure BUFFER 전략은 버퍼의 데이터를 폐기하지 않고 버퍼링을 하는 전략도 지원하지만, 버퍼가 가득 차면 버퍼 내의 데이터를 폐기하는 전략, 그리고 버퍼가 가득 차면 에러를 발생시키는 전략도 지원한다.
  * Backpressure DROP 전략과 LATEST 전략은 버퍼가 가득 찼을 때 버퍼가 비워질 때까지 버퍼 바깥쪽에 있는 데이터를 폐기하는 방식이다.
  * 그런데 이와 달리 BUFFER 전략에서의 데이터 폐기는 BUFFER가 가득 찼을 때 BUFFER 바깥쪽이 아닌 버퍼 안에 있는 데이터를 폐기하는 것을 의미한다.
  * BUFFER 전략 중에서 데이터를 폐기하는 전략에는 DROP_LATEST 전략과 DROP_OLDEST 전략 이렇게 두 가지가 있다.
  * DROP_LATEST: 버퍼에 데이터가 가득 찬 경우, 가장 최근에 버퍼 안에 채워진 데이터를 DROP 하여 폐기한 후, 이렇게 확보된 공간에 emit된 데이터를 채우는 전략이다.(Example8_5) 
  * BUFFER_DROP_OLDEST: 버퍼에 데이터가 가득 찬 경우, 버퍼 안에 채워진 데이터 중에서 가장 오래된 데이터를 DROP하여 폐기한 후, 확보된 공간에 emit된 데이터를 채우는 전략이다(Example8_6)
