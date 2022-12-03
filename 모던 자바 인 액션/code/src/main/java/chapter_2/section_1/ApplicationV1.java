package chapter_2.section_1;

import chapter_2.setting.Apple;

import java.util.ArrayList;
import java.util.List;

import static chapter_2.setting.Color.GREEN;

public class ApplicationV1 {
    public static List<Apple> filterGreenApples(List<Apple> inventory){
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if(GREEN.equals(apple.getColor())){  //녹색 사과만 선택
                result.add(apple);
            }
        }
        return result;
    }
}
