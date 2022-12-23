package chapter_5;

public class Singleton {
    private Singleton() {}

    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    // Other instance fields and methods go here
}

/**
 * Bill Pugh Singleton 구현은 내부 정적 도우미 클래스를 사용하여 싱글톤 인스턴스를 생성함으로써 이러한 오버헤드를 방지합니다.
 * 내부 클래스는 getInstance 메서드가 호출될 때까지 로드되지 않으며, 이 시점에서 싱글턴 인스턴스가 생성됩니다.
 *
 * 이 구현은 getInstance 메서드가 호출될 때만 싱글턴 인스턴스가 생성되기 때문에 정적 팩토리 메서드를 사용하는 기존 접근 방식보다 더 효율적이고 스레드 안전성이 높습니다
 * .홀더 클래스는 스레드 세이프 방식으로 초기화됩니다
 */

/**
 *
 *
 * 자바에서 정적 변수(static variable)는 클래스의 인스턴스가 아닌 클래스와 연관된 변수이다.
 * 중첩된 클래스(내부 클래스라고도 함) 내에서 정적 변수를 선언하면 클래스가 JVM(Java Virtual Machine)에 의해 처음 로드될 때 변수가 초기화됩니다.
 * 즉, 클래스의 인스턴스가 생성되기 전에 변수가 초기화됩니다.
 *
 * 이 동작의 이유는 정적 변수가 클래스의 모든 인스턴스 간에 공유되기 때문입니다.
 * 클래스의 인스턴스가 생성될 때까지 정적 변수가 초기화되지 않은 경우 클래스의 다른 인스턴스는 정적 변수에 대해 서로 다른 값을 가질 수 있으며,
 * 이는 정적 변수를 사용하는 목적을 처음부터 방해합니다.
 *
 * public class OuterClass {
 *   public static class InnerClass {
 *     private static int x = 0;
 *   }
 *
 *   public static void main(String[] args) {
 *     InnerClass c1 = new InnerClass();
 *     InnerClass c2 = new InnerClass();
 *     System.out.println(c1.x);  // prints 0
 *     System.out.println(c2.x);  // prints 0
 *   }
 * }
 *
 * 이 코드에서 정적 변수 x는 내부 클래스 내에서 선언됩니다. 외부 클래스 OuterClass가 JVM에 의해 로드되면 내부 클래스 InnerClass도 로드되고 정적 변수 x가 0으로 초기화됩니다.
 * 두 개의 InnerClass 인스턴스, c1 및 c2를 생성하면 둘 다 정적 변수 x에 대해 동일한 값(0)을 갖습니다.
 *
 * 정적 변수는 클래스의 특정 인스턴스와 연결되고 클래스의 인스턴스가 생성될 때 초기화되는 인스턴스 변수와 같지 않습니다.
 * 인스턴스(instance) 변수는 다른 값을 명시적으로 할당하지 않는 한 기본값(예: 숫자 유형의 경우 0, 참조 유형의 경우 null)으로 초기화됩니다.
 */