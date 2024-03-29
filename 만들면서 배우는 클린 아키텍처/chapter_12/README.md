# chapter 12. 아키텍처 스타일 결정하기

## 도메인이 왕이다.

* 외부의 영향을 받지 않고, 도메인 코드를 자유롭게 발전시킬 수 있다는 것은 육각형 아키텍처 스타일이 내세우는 가장 중요한 가치다.
* DDD에서는 도메인이 개발을 주도한다.
* 그리고 영속성 문제나 다른 기술적인 측면에 대해서 함께 생각할 필요가 없게 되면 도메인에 대해 가장 잘 고려할 수 있게 된다.
* 육각형 스타일과 같은 도메인 중심의 아키텍처 스타일은 DDD의 조력자라고까지 말할 수 있다.
* 도메인을 중심에 두는 아키텍처 없이는, 또 도메인 코드를 향한 의존성을 역전시키지 않고서는, DDD를 제대로 할 가능성이 없다.
* 즉, 설계가 항상 다른요소들에 의해 주도되고 말 것이다.

그러므로 만약 도메인 코드가 애플리케이션에서 가장 중요한 것이 아니라면 이 아키텍처 스타일은 필요하지 않을 것이다.

## 경험이 왕이다.

* 인간은 습관의 동물이다. 습관이 나쁜 결정을 내릴 때만큼, 좋을 결정을 내릴 때도 도움이 된다.
* 우리가 경험한 바를 그대로 하고 있다는 것을 이야기하는 것이다.
* 우리가 과거에 했던 일에 편안함을 느끼는데 무언가를 바꿔야할 이유는 매우 적다.
* 따라서 아키텍처 스타일 에 대해서 괜찮은 결정을 내리는 유일한 방법은 다른 아키텍처 스타일을 경험해 보는 것이다.

## 그때그때 다르다.

* 어떤 아키텍처 스타일을 골라야 하는가에 대한 대답은 '그때그때 달라요.' 이다.
* 어떤 소프트웨어를 만드느냐에 따라서도 다르고, 도메인 코드의 역할에 따라서도 다르다. 팀의 경험에 따라서도 다르다.
