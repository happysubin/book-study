## chapter 11. 카피-온-라이트 리팩토링하기

### 배열에 대한 카피-온-라이트 리팩토링

Ex1.js를 참고

코드를 살펴보면 카피-온-라이트 원칙을 코드로 만들었고 이제 똑같은 코드를 여기저기 만들지 않아도 된다.

또한 다양한 라이브러리(정렬 라이브러리등)를 적용할 수도 있다. 최적화를 진행할 수도 있다.

이후에도 고차 함수를 학습.

### 함수를 리턴하는 함수

Ex3.js 참고

1. 원래 동작을 고차함수로 전달한다.
2. 고차 함수 행동을 새로운 함수로 감싸 실행을 미룬다.
3. 새로운 함수를 리턴한다.
4. 원래 행동에 슈퍼 파워가 추가됨.

함수를 리턴하는 함수는 함수 팩토리와 같다.