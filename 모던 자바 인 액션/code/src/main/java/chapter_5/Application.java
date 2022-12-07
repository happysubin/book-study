package chapter_5;

import chapter_4.Dish;
import chapter_4.MenuFactory;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class Application {

    public static void main(String[] args) throws IOException {
        /**
         * filter
         */
        List<Dish> vegetarianMenu = MenuFactory.getDishes()
                .stream()
                .filter(Dish::isVegetarian)
                .collect(toList());
        /**
         * distinct
         */
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 5, 3, 2, 1)
                .stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .collect(toList());

        for (Integer number : numbers) {
            System.out.println("number = " + number);
        }

        /**
         * takewhile
         * 정렬된 음식 메뉴 콜렉션일 때, 320이하인 음식 메뉴 리스트를 리턴
         */

        List<Dish> slicedMenu1 = MenuFactory.getDishesV2()
                .stream()
                .takeWhile(dish -> dish.getCalories() < 320)
                .collect(toList());

        /**
         * dropwhile
         * 정렬된 음식 메뉴 콜렉션일 때, 320 이상인 음식 메뉴를 리턴
         */

        List<Dish> slicedMenu2 = MenuFactory.getDishesV2()
                .stream()
                .dropWhile(dish -> dish.getCalories() < 320)
                .collect(toList());

        /**
         * 스트림 축소 limit을 사용
         * 프레디케이트와 일치하는 처음 세 요소를 선택한 다음에 즉시 결과를 반환한다.
         */

        List<Dish> slicedMenu3 = MenuFactory.getDishesV2()
                .stream()
                .limit(3)
                .collect(toList());

        /**
         * skip
         * 300 칼로리 이상의 첫 두 요리를 건너뛴 다음에 나머지 300칼로리 이상인 요리를 반환한다.
         */
        List<Dish> dishes = MenuFactory.getDishesV2()
                .stream()
                .filter(d -> d.getCalories() > 300)
                .skip(2)
                .collect(toList());

        /**
         * map 메서드
         */
        List<Integer> nameLengths = MenuFactory.getDishesV2()
                .stream()
                .map(Dish::getName)
                .map(String::length)
                .collect(toList());

        /**
         * flatMap
         */

        List<String> unique = Arrays.asList("aa", "bb", "cc", "dd")
                .stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(toList());

        for (String s : unique) {
            System.out.println("s = " + s);
        }

        /**
         * anyMatch
         */
        if(MenuFactory.getDishesV2().stream().anyMatch(Dish::isVegetarian)){
            System.out.println("vegetarain");
        }

        /**
         * allMatch
         */
        boolean isHealthy = MenuFactory.getDishesV2().stream()
                .allMatch(dish -> dish.getCalories() < 300);

        /**
         * noneMatch
         */
        boolean isHealthyV2 = MenuFactory.getDishesV2().stream().noneMatch(d -> d.getCalories() >= 1000);

        /**
         * findAny
         */
        Optional<Dish> dish = MenuFactory.getDishesV2()
                .stream()
                .filter(Dish::isVegetarian)
                .findAny();

        /**
         * findFirst
         */

        List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> first = someNumbers.stream()
                .map(n -> n * n)
                .filter(n -> n % 3 == 0)
                .findFirst();


        /**
         * reduce
         * 초기값 0
         * 두 요소를 ㅊ조합해서 새로운 값을 만드는 BinaryOperator<T> 예제에서는 ㄹ마다 표현식 (a,b ) -> a + b를 사용했다.
         */
        Arrays.asList(1, 2, 3, 3, 4)
                .stream()
                .reduce(0, (a, b) -> a + b);


        //reduce를 활용해서 최댓값을 구하는 방법
        Optional<Integer> max = Arrays.asList(1, 3, 4, 5, 7, 9, 8, 2)
                .stream()
                .reduce(Integer::max);


        /**
         * reduce를 사용해 sum 구하기
         */

        Integer reduce = MenuFactory.getDishes()
                .stream()
                .map(Dish::getCalories)
                .reduce(0, Integer::sum);

        System.out.println("sum.get() = " + reduce);

        /**
         * mapToInt를 사용해 IntStream 사용
         */

        int sum = MenuFactory.getDishes()
                .stream()
                .mapToInt(Dish::getCalories) //Stream<Integer>가 아닌  IntStream을 반환한다.
                .sum();
        System.out.println("sum = " + sum);

        /**
         * 객체 스트림으로 복원하기
         */
        IntStream intStream = MenuFactory.getDishes()
                .stream()
                .mapToInt(Dish::getCalories);
        Stream<Integer> boxed = intStream.boxed();

        /**
         * range
         * rangeClosed
         */
        IntStream evenNumbers = IntStream.rangeClosed(1, 100) // [1, 100] 범위를 나타낸다
                .filter(n -> n % 2 == 0);
        System.out.println(evenNumbers.count()); //1부터 100까지에는 50개의 짝수가 있다. 출력 값 50. range 메서드는 1과 100를 포함하지 않으므로 49가 출력된다.

        /**
         * Stream.of
         */
        Stream<String> stream = Stream.of("Modern", "Java", "in", "Action");
        stream.map(String::toUpperCase).forEach(System.out::println);

        /**
         * Stream.ofNullable
         */
        Stream<String> home = Stream.ofNullable(System.getProperty("home"));

        /**
         * Arrays.stream
         */
        int[] numbers2 = {2, 3, 4, 5, 6, 7};
        int sum1 = Arrays.stream(numbers2).sum();

        /**
         * 파일로 스트림 만들기
         */
        //Stream<String> lines = Files.lines(Paths.get("data.txt"));

        /**
         * 함수로 무한 스트림 만들기
         */

        Stream.iterate(0, n -> n + 2)
                .limit(10)
                .forEach(System.out::println);


        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);
    }
}
