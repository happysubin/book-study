# chapter 5. 하나뿐인 특별한 객체 만들기 (싱글턴 패턴)

## 고전적인 싱글턴 패턴 구현법

```java
public class SingletonV1 {
    
    private static SingletonV1 uniqueInstance;
    
    private SingletonV1(){}
    
    public static SingletonV1 getInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new SingletonV1();
        }
        return uniqueInstance;
    }
}
```

* 인스턴스가 만들어지지 않았다면 private으로 선언된 생성자를 사용해서 싱글턴 객체를 만든 다음 uniqueInstance에 대입한다.
* 이러면 인스턴스가 필요한 상황이 닥치기 전까지 아예 인스턴스를 생성하지 않게 된다.
* 이런 방법을 게으른 인스턴스 생성이라고 부른다.

## 싱글턴 패턴 정의

* 싱글턴 패턴은 클래스 인스턴스를 하나만 만들고, 그 인스턴스로의 전역 접근을 제공한다.
* 싱글턴 패턴을 실제로 적용할 때는 클래스에서 하나뿐인 인스턴스를 관리하도록 만들면 된다. 그리고 다른 어떤 클래스에서도 자신의 인스턴스를 추가로 만들지 못하게 해야 한다. 인스턴스가 필요하다면 반드시 클래스 자신을 거쳐야한다.
* 어디서든 그 인스턴스에 접근할 수 있도록 전역 접근 지점을 제공한다. 언제든 이 인스턴스가 필요하면 클래스에 요청할 수 있게 만들어 ㄴ호고, 요청이 들어오면 그 하나뿐인 인스턴스를 건네주도록 만들어야 한다.
* 싱글턴이 게으른 방식으로 생성되도록 구현하면, 자원을 많이 잡아먹는 인스턴스가 있을 때 매우 유용하다.

## 멀티 스레딩 문제

* 위 코드는 2개의 스레드에서 동시에 접근하면 문제가 발생할 수 있다.
* 동시에 __uniqueInstance == null__ 인 상황에서 객체 생성에 접근한다면 싱글턴 패턴이 깨져버리는 것이다.
* 이를 synchronized 키워드를 사용해 우선 해결했다.
* 즉 한 메서드가 사용을 끝내기 전까지는 다른 메서드가 기다려야 하는 것이다.
* 그러나 생각해보면 동기화가 꼭 필요한 시점은 바로 __uniqueInstance 변수에 Singleton 인스턴스가 할당되는 순간이다.__
* 그러므로 이 메서드를 동기화된 상태로 유지할 필요가 없다.
* 처음을 제외하면 불필요한 오버헤드만 증가시킨다.

## 효율적으로 멀티스레딩 문제 해결하기

1. getInstance 속도가 중요하지 않다면 그냥 둔다. 그러나 메서드를 동기화하면 성능이 100배 정도 저하된다고 함.
2. 인스턴스가 필요할 때 생성하지 말고 처음부터 만든다.

```java
public class SingletonV3 {

    private static SingletonV3 uniqueInstance = new SingletonV3();

    private SingletonV3(){}

    public static synchronized SingletonV3 getInstance(){
        return uniqueInstance;
    }
}
```

3. DCL을 써서 getInstance()에서 동기화되는 부분을 줄인다.
    * DCL(Double-Checked Locking)을 사용하면 인스턴스가 생성되어 있는지 확인한 다음 생성되어 있지 않을 때만 동기화할 수 있다.
    * 이러면 처음에만 동기화하고 나중에는 동기화하지 않아도 된다.
    * 참고로 DCL은 자바 5이상에서만 사용할 수 있다.

```java
public class SingletonV4 {

    private volatile static SingletonV4 uniqueInstance;

    private SingletonV4(){}

    public static synchronized SingletonV4 getInstance(){
        if(uniqueInstance == null){
            synchronized (SingletonV4.class){
                if(uniqueInstance == null){
                    uniqueInstance = new SingletonV4();
                }
            }
        }
        return uniqueInstance;
    }
}
```

4. enum을 사용하면 된다.

## 추가적인 내용. Bill Pugh Singleton

* Bill Pugh Singleton 구현이라는 것이 있다.
* 내부 정적 클래스(중첩 클래스)를 사용하여 싱글톤 인스턴스를 생성함으로써 오버헤드를 방지한다.
* 내부 클래스는 getInstance 메서드가 호출될 때까지 로드되지 않으며, 이 시점에서 싱글턴 인스턴스가 생성됩니다.

```java
public class Singleton {
    private Singleton() {}

    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
```
* Bill Pugh Singleton은 중첩된 정적 내부 클래스를 사용하여 Singleton 인스턴스를 느리게 초기화하는 Singleton 설계 패턴입니다. 
* 이 접근법은 싱글턴 패턴을 구현하는 다른 접근법에 비해 몇 가지 이점이 있다.
* 빌 퓨 싱글턴의 주요 원칙은 다음과 같다.
* 내부 클래스가 로드될 때 Singleton 인스턴스가 초기화되며, 이는 getInstance() 메서드가 처음 호출될 때만 수행됩니다. 
* 이렇게 하면 Singleton 인스턴스가 필요할 때만 생성됩니다.
* getInstance() 메서드가 호출될 때까지 내부 클래스가 로드되지 않으므로 Singleton 인스턴스는 필요할 때까지 생성되지 않습니다. 
* 이것은 빌 퓨 싱글턴을 열심히 인스턴스를 만드는 다른 싱글턴 구현체들보다 더 효율적으로 만든다.
* 빌 퓨 싱글턴은 스레드 세이프입니다.
* getInstance() 메서드가 호출될 때까지 내부 클래스가 로드되지 않으므로 여러 스레드가 동시에 내부 클래스에 액세스할 수 없으므로 Singleton의 인스턴스가 하나만 생성됩니다.
* 빌 퓨 싱글턴은 동기화된 블록이나 잠금을 사용하지 않기 때문에 다른 싱글턴 구현체보다 효율적이다. 따라서 멀티스레드 환경에서 성능이 향상됩니다.
* 다음은 빌 퓨 싱글턴을 자바에서 구현하는 방법의 예이다.

참고

* 자바에서 정적 변수(static variable)는 클래스의 인스턴스가 아닌 클래스와 연관된 변수이다.
* 중첩된 클래스(내부 클래스라고도 함) 내에서 정적 변수를 선언하면 클래스가 JVM(Java Virtual Machine)에 의해 처음 로드될 때 변수가 초기화된다.
* 즉, 클래스의 인스턴스가 생성되기 전에 변수가 초기화된다.
* 이 동작의 이유는 정적 변수가 클래스의 모든 인스턴스 간에 공유되기 때문이다.
* 클래스의 인스턴스가 생성될 때까지 정적 변수가 초기화되지 않은 경우 클래스의 다른 인스턴스는 정적 변수에 대해 서로 다른 값을 가질 수 있으며, 이는 정적 변수를 사용하는 목적을 처음부터 방해한다.