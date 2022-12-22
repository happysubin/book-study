# chapter 2. 객체 생성과 파괴 

## item 9. try-finally 보다는 try-with-resources 를 사용하라.

* try-catch를 사용하면 코드에서 실수가 발생할 확률이 높아지고, 자원이 많을 경우 코드가 굉장히 지저분해진다.
* 이러한 문제들을 자바 7에서는 try-with-resources를 활용해 해결했다.
* 이 구조를 사용하려면 해당 자원이 AutoCloseable 인터페이스를 구현해야 한다.
* 단순히 void를 반환하는 close 메서드 하나만 덩그러니 정의한 인터페이스다.
* 자바 라이브러리와 서드 파티 라이브러리들의 수많은 클래스와 인스턴스가 이미 AutoCloseable을 구현하거나 확장해뒀다.
* 닫아야하는 자원을 뜻하는 클래스를 작성한다면 AutoCloseable을 반드시 구현해해야 한다.

try-with-resources를 활용한 코드가 짧고 읽기 수월할 뿐 아니라 문제를 진단하기도 훨씬 좋다.

* 보통의 try-finally에서처럼 try-with-resources에서도 catch 절을 사용할 수 있다.
* catch절 덕분에 try문을 더 중첩하지 않고도 다수의 예외를 처리할 수 있다.

## 정리

* 꼭 회수해야 하는 자원을 다룰 때는 try-finally 말고, try-with-resources를 사용하자.
* 코드는 더 짧고 분명해지고, 만들어지는 예외 정보도 훨씬 유용하다.
* try-finally로 작성하면 실용적이지 못할 만큼 코드가 지저분해지는 경우라도, try-with-resources로는 정확하고 쉽게 자원을 회수할 수 있다.