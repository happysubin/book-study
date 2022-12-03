package chapter_2.section_3.quiz;

import java.sql.SQLOutput;

public class MeaningOfThis {

    public final int value = 4;

    public void doIt(){
        int value = 6;
        Runnable r = new Runnable() {
            public final int value = 5;
            @Override
            public void run() {
                int value = 10;
                System.out.println(this.value);
            }
        };
        r.run();
    }

    public static void main(String[] args) {
        MeaningOfThis meaningOfThis = new MeaningOfThis();
        meaningOfThis.doIt(); // 이 행의 출력 결과는??
    }
}

/**
 * 코드에서 this는 MeaningOfThis가 아니라 Runnablefmf 참조하므로 5가 답이다.
 */
