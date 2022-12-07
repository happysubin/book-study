package chapter_4;

import java.util.Arrays;
import java.util.List;

public class MenuFactory {

    public static List<Dish> getDishes(){
        return Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", false, 530, Dish.Type.OTHER),
                new Dish("season", true, 350, Dish.Type.OTHER),
                new Dish("rice", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("salmon", false, 300, Dish.Type.FISH),
                new Dish("prawns", false, 450, Dish.Type.FISH)
        );
    }

    public static List<Dish> getDishesV2(){
        return Arrays.asList(
                new Dish("pork", false, 120, Dish.Type.MEAT),
                new Dish("beef", false, 130, Dish.Type.MEAT),
                new Dish("chicken", false, 200, Dish.Type.MEAT),
                new Dish("french fries", false, 330, Dish.Type.OTHER),
                new Dish("season", true, 450, Dish.Type.OTHER),
                new Dish("rice", true, 520, Dish.Type.OTHER),
                new Dish("pizza", true, 650, Dish.Type.OTHER),
                new Dish("salmon", false, 700, Dish.Type.FISH),
                new Dish("prawns", false, 850, Dish.Type.FISH)
        );
    }
}
