package chapter_2.section_1;

import chapter_2.setting.Apple;
import chapter_2.setting.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static chapter_2.setting.Color.GREEN;
import static chapter_2.setting.Color.RED;

public class ApplicationV3 {
    public static List<Apple> filterApples( List<Apple> inventory, Color color, int weight, boolean flag){
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if(flag && apple.getColor().equals(color) || (!flag && apple.getWeight() > weight)){  //모든 조건 검사. 형편 없는 코드
                result.add(apple);
            }
        }
        return result;
    }



    public static void main(String[] args) {

        List<Apple> inventory = Arrays.asList(new Apple(GREEN), new Apple(RED), new Apple(GREEN));
        List<Apple> greenApples = filterApples(inventory, GREEN, 0, true);
        List<Apple> redApples = filterApples(inventory, RED, 150, false);
    }
}
