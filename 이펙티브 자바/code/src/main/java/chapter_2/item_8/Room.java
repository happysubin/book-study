package chapter_2.item_8;

import java.lang.ref.Cleaner;

/**
 * 방 자원을 수거하기 전에 반드시 clean 해야 함.
 * 사실 자동 청소 안전망이 claner를 사용할지 말지는 순전히 내부 구현 방식에 관한 문제다.
 * 즉 finalizer와 달리 cleaner는 클래스 public API에 나타나지 않는다.
 */

public class Room implements AutoCloseable{

    private static final Cleaner cleaner = Cleaner.create();

    private static class State implements Runnable{
        int numJunkPiles;

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
