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
}
