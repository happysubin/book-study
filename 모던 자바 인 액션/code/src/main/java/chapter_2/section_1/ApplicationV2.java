package chapter_2.section_1;

import chapter_2.setting.Apple;
import chapter_2.setting.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static chapter_2.setting.Color.GREEN;
import static chapter_2.setting.Color.RED;

public class ApplicationV2 {
    public static List<Apple> filterApplesByColor(List<Apple> inventory, Color color){
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if(apple.getColor().equals(color)){  //녹색 사과만 선택
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight){
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if(apple.getWeight() > weight){  //녹색 사과만 선택
                result.add(apple);
            }
        }
        return result;
    }

    public static void main(String[] args) {

        List<Apple> inventory = Arrays.asList(new Apple(GREEN), new Apple(RED), new Apple(GREEN));
        List<Apple> greenApples = filterApplesByColor(inventory, GREEN);
        List<Apple> redApples = filterApplesByColor(inventory, GREEN);
    }
}
