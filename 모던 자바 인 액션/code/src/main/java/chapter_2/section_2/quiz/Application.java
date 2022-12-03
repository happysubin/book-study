package chapter_2.section_2.quiz;

import chapter_2.setting.Apple;

import java.util.Arrays;
import java.util.List;

import static chapter_2.setting.Color.GREEN;
import static chapter_2.setting.Color.RED;

public class Application {

    public static void prettyPrintApple(List<Apple> inventory, ApplePrintPredicate predicate){
        for (Apple apple : inventory) {
            String output = predicate.print(apple);
            System.out.println(output);
        }
    }

    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(new Apple(GREEN), new Apple(RED), new Apple(GREEN));
        prettyPrintApple(inventory, new AppleFancyPrintPredicate());
    }
}
