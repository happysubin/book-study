# chapter 3. 모든 객체의 공통 메서드

## item 13. clone 정의는 주의해서 진행하라.

* Cloneable은 복제해도 되는 클래스임을 명시하는 믹스인 인터페이스지만, 아쉽게도 의도한 목적을 제대로 이루지 못했다.
* 가장 큰 문제는 clone 메서드가 선언된 곳이 Clobeable이 아닌 Object이고, 그마저도 protected라는 데 있다.