package chapter_6;

import chapter_4.CaloricLevel;
import chapter_4.Dish;
import chapter_4.MenuFactory;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Application {
    public static void main(String[] args) {
        Long count = MenuFactory.getDishes()
                .stream()
                .collect(Collectors.counting());
        System.out.println("count = " + count);

        long count1 = MenuFactory.getDishesV2()
                .stream()
                .count();
        System.out.println("count1 = " + count1); //이렇게도 가능

        /**
         * 스트림에서 최댓값과 최솟값
         */

        Comparator<Dish> dishCaloriesComparator = Comparator.comparing(Dish::getCalories);

        Optional<Dish> dish = MenuFactory.getDishesV2()
                .stream()
                .collect(Collectors.maxBy(dishCaloriesComparator));
        System.out.println("dish.get() = " + dish.get());

        /**
         * 메뉴 리스트의 총 칼로리 계산.
         */
        Integer caloriesSum = MenuFactory.getDishesV2()
                .stream()
                .collect(Collectors.summingInt(Dish::getCalories));
        System.out.println("caloriesSum = " + caloriesSum);

        /**
         * 하나의 요약 연산으로 메뉴에 있는 요소 수, 요리의 칼로리 합계, 평균, 최댓값, 최솟값 등을 계산하는 코드
         */
        IntSummaryStatistics collect = MenuFactory.getDishesV2()
                .stream()
                .collect(Collectors.summarizingInt(Dish::getCalories));
        System.out.println("collect = " + collect);

        /**
         * 문자열을 합해서 추출
         */

        String collect1 = MenuFactory.getDishesV2()
                .stream()
                .map(Dish::getName)
                .collect(Collectors.joining(", ")); //예쁘게 출력.
        System.out.println("collect1 = " + collect1);

        /**
         * reducing 메서드 사용
         */

        Integer sum = MenuFactory.getDishesV2()
                .stream()
                .collect(Collectors.reducing(0, Dish::getCalories, Integer::sum));
        System.out.println(sum);

        Optional<Dish> result = MenuFactory
                .getDishesV2()
                .stream()
                .collect(Collectors.reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)); //제일 큰 칼로리 음식을 뽑음
        System.out.println("result.get() = " + result.get());

        /**
         * groupingBy 메서드
         */

        Map<Dish.Type, List<Dish>> map = MenuFactory.getDishesV2()
                .stream()
                .collect(Collectors.groupingBy(Dish::getType));

        for (Dish.Type type : map.keySet()) {
            System.out.println("type = " + type);
        }

        Map<CaloricLevel, List<Dish>> collect2 = MenuFactory
                .getDishesV2()
                .stream()
                .collect(Collectors.groupingBy(d -> {
                    if (d.getCalories() <= 400) return CaloricLevel.DIET;
                    else if (d.getCalories() <= 700) return CaloricLevel.NORMAL;
                    else return CaloricLevel.FAT;
                }));
        for (CaloricLevel caloricLevel : collect2.keySet()) {
            System.out.println("caloricLevel = " + caloricLevel);
        }

        /**
         * 그룹화된 요소 조작
         */

        Map<Dish.Type, List<Dish>> advancedGroupByResult = MenuFactory
                .getDishesV2()
                .stream()
                .collect(Collectors.groupingBy(
                        Dish::getType, Collectors.filtering(d -> d.getCalories() > 500, Collectors.toList())));
        for (Dish.Type type : advancedGroupByResult.keySet()) {
            System.out.println("type = " + type);
            System.out.println("map.get(type) = " + map.get(type));
        }


        /**
         * 다수준 그룹화
         */

        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel = MenuFactory.getDishesV2()
                .stream()
                .collect(Collectors.groupingBy(Dish::getType,
                        Collectors.groupingBy(d -> {
                            if (d.getCalories() < 400) return CaloricLevel.DIET;
                            else if (d.getCalories() < 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        })
                ));
        for (Dish.Type type : dishesByTypeCaloricLevel.keySet()) {
            System.out.println("type = " + type);
            System.out.println(dishesByTypeCaloricLevel.get(type));
        }

        /**
         * 서브그룹으로 데이터 수집
         */

        Map<Dish.Type, Long> countMap = MenuFactory
                .getDishesV2()
                .stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.counting()));

        for (Dish.Type type : countMap.keySet()) {
            System.out.println("type = " + type);
            System.out.println("countMap = " + countMap.get(type));
        }

        Map<Dish.Type, Optional<Dish>> compareMap = MenuFactory
                .getDishesV2()
                .stream()
                .collect(Collectors.groupingBy(Dish::getType,
                        Collectors.maxBy(Comparator.comparing(Dish::getCalories)))); //음식 타입의 제일 높은 칼로리 음식을 뽑음.

        for (Dish.Type type : compareMap.keySet()) {
            System.out.println("type = " + type);
            System.out.println("compareMap = " + compareMap.get(type));
        }

        /**
         * 채식주의자를 기준으로 분할
         */

        Map<Boolean, List<Dish>> collect3 = MenuFactory
                .getDishesV2()
                .stream()
                .collect(Collectors.partitioningBy(Dish::isVegetarian)); //true, false 2개의 키로 구성된 map
        for (Boolean aBoolean : collect3.keySet()) {
            System.out.println("aBoolean = " + aBoolean);
            System.out.println("collect3.get(aBoolean) = " + collect3.get(aBoolean));
        }


        Map<Boolean, Map<Dish.Type, List<Dish>>> collect4 = MenuFactory
                .getDishesV2()
                .stream()
                .collect(Collectors.partitioningBy(Dish::isVegetarian, Collectors.groupingBy(Dish::getType)));

        for (Boolean aBoolean : collect4.keySet()) {
            System.out.println("aBoolean = " + aBoolean);
            System.out.println("collect4 = " + collect4.get(aBoolean));
        }

    }
}
