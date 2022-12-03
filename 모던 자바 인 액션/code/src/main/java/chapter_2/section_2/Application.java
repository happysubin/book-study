package chapter_2.section_2;

import chapter_2.setting.Apple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static chapter_2.setting.Color.GREEN;
import static chapter_2.setting.Color.RED;

public class Application {

    public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p){

        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if(p.test(apple)){
                result.add(apple);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(new Apple(GREEN), new Apple(RED), new Apple(GREEN));
        List<Apple> greenApple = filterApples(inventory, new AppleGreenColorPredicate());
    }
}
