# chapter 2. 객체 생성과 파괴 

## item 8. finalizer와 cleaner 사용을 피하라

* 자바는 두 가지 객체 소멸자를 제공한다.
* finalizer는 예측할 수 없고, 상황에 따라 위험할 수 있어 불필요하다. 기본적으로 쓰지말아야 한다.
* 그래서 자바 9에서는 finalizer를 사용 자제 API로 지정하고 cleaner를 그 대안으로 소개했다.
* cleaner는 finalizer보다는 덜 위험하지만, 여전히 예측할 수 없고, 느리고, 일반적으로 불필요하다.
* 자바의 finalizer과 cleaner는 C++의 파괴자와는 다른 개념이다.
* 자바에서 파괴자 역할은 try-with-resources와 try-finally를 사용해 해결한다.

### 단점 1

* finalizer와 cleaner는 즉시 실행된다는 보장이 없다.
* 객체에 접근할 수 없게 된 후 finalizer나 cleaner가 실행되기까지 얼마나 걸릴지 알 수 없다.
* __즉, finalizer와 cleaner로는 제때 실행되어야 하는 작업은 절대 할 수 없다.__
* 예를 들어 파일 닫기를 finalizer과 cleaner에 맡기면 중대한 오류를 일으킬 수 있다.

### 단점 2

* 굼뜬 finalizer 처리는 현업에서도 실제로 문제를 일으킨다. 
* 클래스에 finalizer를 달아두면 그 인스턴스의 자원회수가 제멋대로 지연될 수 있다.

### 단점 3

* 자바 언어 명세는 finalizer나 cleaner의 수행 시점뿐 아니라 수행 여부조차 보장하지 못한다.
* 접근할 수 없는 일부 객체에 딸린 종료 작업을 전혀 수행하지 못한 채 프로그램이 중단될 수도 있다는 얘기다.
* 따리서 프로그램 생애주기와 상관없는, __상태를 영구적으로 수정하는 작업에서는 절대 finalizer나 cleaner에 의존해서는 안된다.__
* 예를 들어 DB 같은 공유 자원의 영구 락해제를 finalizer나 cleaner에 맡겨 놓으면 분산 시스템 전체가 서서히 멈출 것이다.

### 단점 4

* System.gc나 System.runFinalization 메서드에 현혹되지 말자.
* finalizer와 cleaner가 실행될 가능성을 높여줄 수는 있으나, 보장해주진 않는다.

### 단점 5

* finalizer 동작 중 발생한 예외는 무시되며, 처리할 작업이 남았더라도 그 순간 종료된다.
* 잡지 못한 예외 때문에 해당 객체는 자칫 마무리가 덜 된 상태로 남을 수 있다.
* 그리고 다른 스레드가 이처럼 훼손된 객체를 사용하려 한다면 어떻게 동작할지 예측할 수 없다.

### 단점 6

* finalizer와 cleaner는 심각한 성능 문제도 동반한다.
* AutoCloseable 객체를 생성하고 GC가 수거하기까지 12ns가 걸렸다. (try-with-resources로 자신을 닫도록함)
* finalizer를 사용하면 550ns가 걸렸다. 즉 50배나 느린 것이다.
* finalizer가 가비지 컬렉터의 효율을 떨으뜨리기 때문이다.
* cleaner도 클래스의 모든 인스턴스를 수거하는 형태로 사용하면 finalizer와 비슷하다.

### 단점 7

* finalizer를 사용한 클래스는 finalizer 공격에 노출되어 심각한 보안 문제를 일으킬 수도 있다.
* finalizer 공격 원리는 다음과 같은데, 생성자나 직렬화 과정에서 예외가 발생하면, 이 생성되다만 객체에서 악의적인 하위 클래스의 finalizer가 수행될 수 있게 된다.
* finalizer는 정적 필드에 자신의 참조를 할당하여 가비지 컬렉터가 수집하지 못하게 막을 수 있다. 결국 좀비처럼 남아있는 것이다.
* 객체 생성을 막으려면 생성자에서 예외를 던지는 것만으로 충분하지만, finalizer가 있다면 그렇지 않다.
* final이 아닌 클래스를 finalizer 공격으로부터 방어하려면 아무 일도 하지 않는 finalizer 메서드를 만들고 final로 선언하자.

그렇다면 파일이나 스레드 등 종료해야 할 자원을 담고 있는 객체의 클래스에서 finalizer나 cleaner를 대신해줄 묘안은 무엇일까?
__그저 AutoCloseable을 구현해주고, 클라이언트에서 인스턴스를 다 쓰고 나면 close 메서드를 호출하면 된다.__

* 일반적으로 예외가 발생해도 제대로 종료되도록 try-with-resources를 사용해야 한다.
* 각 인스턴스는 자신이 닫혔는지를 추적하는 것이 좋다.
* 다시 말해 close 메서드에서 이 객체는 더 이상 유효하지 않음을 필드에 기록하고, 다른 메서드는 이 필드를 검사해서 객체가 닫힌 후에 불렸다면 IllegalStateException을 던지는 것이다.

cleaner과 finalizer는 두가지 정도 적절한 쓰임새가 있다.

### 첫 번째 예시

* 하나는 자원의 소유자가 close 메서드를 호출하지 않는 것에 대한 안전망 역할이다.
* cleaner나 finalizer가 즉시 호출되리라는 보장은 없지만, 클라이언트가 하지 않은 자원 회수를 늦게라도 해주는 것이 안하는 것보다는 낫다.
* 자바 라이브러리의 일부 클래스는 안전망 역할의 finalizer를 제공한다. FileInputStream, FileOutputStream, ThreadPoolExecutor이 대표적이다.

### 두 번째 예시

* cleaner, finalizer를 적절히 활용하는 두 번째 예는 네이티브 피어와 연결된 객체에서다.
* 네이티브 피어란 일반 자바 객체가 네이티브 메서드를 통해 기능을 위임한 네이티브 객체를 말한다.
* 네이티프 피어는 자바 객체가 아니니 가비지 컬렉터는 그 존재를 알지 못한다.
* 그 결과 자바 피어를 회수할 때 네이티브 객체까지 회수하지 못한다.
* cleaner나 finalizer가 나서서 처리하기에 적당한 작업이다. 단, 성능 저하를 감당할 수 있고 네이티브 피어가 심각한 자원을 가지고 있지 않을 때에만 해당된다.
* 성능 저하를 감당할 수 없거나 네이티브 피어가 사용하는 자원을 즉시 회수해야 한다면 앞서 설명한 close 메서드를 사용해야 한다.

## cleaner 사용법


```
public class Room implements AutoCloseable{
    
    private static final Cleaner cleaner = Cleaner.create();

    private static class State implements Runnable{
        int numJunkPiles; //방 안의 쓰레기 수

        public State(int numJunkPiles) {
            this.numJunkPiles = numJunkPiles;
        }

        /**
         * close 메서드나 cleaner가 호출한다.
         */
        @Override
        public void run() {
            System.out.println("방 청소");
            numJunkPiles = 0;
        }
    }

    private final State state;
    private final Cleaner.Cleanable cleanable;
    
    public Room(int numJunkPiles) {
        state = new State(numJunkPiles);
        cleanable = cleaner.register(this, state);
    }

    @Override
    public void close() throws Exception {
        cleanable.clean();
    }
}
```


* 방 자원을 수거하기 전에 반드시 clean 해야 함.
* 사실 자동 청소 안전망이 claner를 사용할지 말지는 순전히 내부 구현 방식에 관한 문제다.
* 즉 finalizer와 달리 cleaner는 클래스 public API에 나타나지 않는다.
* static으로 선언된 중첩 클래스인 State는 cleaner가 방을 청소할 때 수거할 자원들을 잠고 있다.
* State는 Runnalbe을 표현하고, 그 안의 run 메서드는 cleanable에 의해 딱 한 번만 호출 될 것이다.
* 이 cleanable 객체는 Room 생성자에서 cleaner에 Room과 State를 등록할 때 얻는다.
* run 메서드가 호출되는 상황은 둘 중 하나다.
  1. 보통은 Room의 close 메서드를 호출할 때다.
  2. 혹은 GC가 Room을 회수할 때까지 클라이언트가 close를 호출하지 않는다면, cleaner가 state의 run 메서드를 호출해줄 것이다.

State 인스턴스는 절대로 Room 인스턴스를 참조해서는 안된다. 순환 참조가 생겨 GC가 Room 인스턴스를 회수해갈 기회가 오지 않는다.

* State가 정적인 클래스인 이유가 바로 여기에 있다. 정적이 아닌 중첩 클래스는 자동으로 바깥 객체의 참조를 갖게 되기 때문이다.
* 이와 비슷하게 람다 역시 바깥 객체의 참조를 갖기 쉬우니 사용하지 않는 것이 좋다.
* 마지막으로 안전망으로 잘 사용된 코드와 사용하지 못한 코드를 살펴봄.
* __System.exit을 호출할 때의 cleaner 동작은 구현하기 나름이다. 처소가 이뤄질지는 보장할 수 없다.__ 

##결론

* cleaner, finalizer는 그냥 사용하지말자.