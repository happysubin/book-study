package chapter_4;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {
        List<String> threeHighCaloricDishNames = MenuFactory.getDishes()
                .stream()
                .filter(dish -> dish.getCalories() > 300)
                .map(Dish::getName)
                .limit(3)
                .collect(Collectors.toList());


        MenuFactory.
                getDishes()
                .stream()
                .filter(dish ->{
                    System.out.println("filtering: " + dish.getName());
                    return dish.getCalories() > 300;
                })
                .map(dish -> {
                    System.out.println("mapping: " + dish.getName());
                    return dish.getName();
                })
                .limit(3)
                .collect(Collectors.toList());
        /**
         * 출력 결과
         * filtering: pork
         * mapping: pork
         * filtering: beef
         * mapping: beef
         * filtering: chicken
         * mapping: chicken
         */
    }

}
