package chapter_3.section_7;

import chapter_2.setting.Apple;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static chapter_2.setting.Color.GREEN;
import static chapter_2.setting.Color.RED;
import static java.util.Comparator.comparing;

public class Application {

    public static void main(String[] args) {

        //코드 전달
        List<Apple> inventory = Arrays.asList(new Apple(GREEN, 80), new Apple(GREEN, 160), new Apple(RED, 120));
        inventory.sort(new AppleComparator());

        //익명 클래스 사용
        inventory.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight().compareTo(o2.getWeight());
            }
        });

        //람다 사용
        inventory.sort((a1, a2) -> a2.getWeight().compareTo(a2.getWeight()));

        //메서드 참조 사용
        //Comparator<Apple> c = comparing((Apple a1) -> a1.getWeight());
        inventory.sort(comparing(Apple::getWeight));
    }
}
