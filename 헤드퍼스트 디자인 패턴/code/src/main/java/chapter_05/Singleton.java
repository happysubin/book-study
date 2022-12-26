package chapter_05;

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

//https://kdhyo98.tistory.com/70