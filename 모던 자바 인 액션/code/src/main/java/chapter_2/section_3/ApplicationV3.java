package chapter_2.section_3;

import chapter_2.section_2.ApplePredicate;
import chapter_2.setting.Apple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static chapter_2.setting.Color.GREEN;
import static chapter_2.setting.Color.RED;

public class ApplicationV3 {

    interface Predicate<T>{
        boolean test(T t);
    }


    public static <T> List<T> filter(List<T> inventory, Predicate<T> predicate){

        List<T> result = new ArrayList<>();
        for (T e : result) {
            if(predicate.test(e)){
                result.add(e);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<Apple> inventory =
                Arrays.asList(new Apple(GREEN, 80), new Apple(GREEN, 160), new Apple(RED, 120));
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        List<Apple> redApples = filter(inventory, (Apple apple) -> RED.equals(apple.getColor()));
        List<Integer> evenNumbers = filter(numbers, (Integer i) -> i % 2 ==0);

    }
}
