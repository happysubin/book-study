package chapter_2.item_8;

public class Teenager {
    public static void main(String[] args) {
        new Room(99);
        //System.gc(); 이걸 추가해야 방청소가 출력된다.
        System.out.println("아무렴");
    }
}

/**
 * 방 청소 출력문이 출력되지 않음.
 */
