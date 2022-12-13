package chapter_8;

import chapter_4.Dish;
import chapter_4.MenuFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Application {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        List<String> strings = Arrays.asList("bin", "joon", "su", "kim");
        //strings.add("ho"); UnsupportedOperationException 발생.

        List<String> list = List.of("a", "b", "c");
        //list.add("s"); UnsupportedOperationException 발생.

        Set<String> set = Set.of("a", "b", "c");

        Map<String, Integer> a = Map.of("a", 1, "b", 2);
        System.out.println("a = " + a);

        ArrayList<Dish> dishes = new ArrayList<>();
        dishes.addAll(MenuFactory.getDishes());

        /**
         * 아래 연속으로 나오는 코드는 사실 동일하다.
         */

        for (Dish dish : dishes) {
            if(Character.isDigit(dish.getName().charAt(0))){
                dishes.remove(dish);
            }
        }

        /**
         * 위 코드에서 보이는 for-each는 내부적으로 Iterator 객체를 사용하므로 아래 코드와 같이 해석된다.
         */

        for (Iterator<Dish> iterator = dishes.iterator(); iterator.hasNext(); ) {
            Dish dish = iterator.next();
            if(Character.isDigit(dish.getName().charAt(0))){
                dishes.remove(dish);
            }
        }

        /**
         * 해당 코드로 문제를 해결할 수 있다.
         */

        for (Iterator<Dish> iterator = dishes.iterator(); iterator.hasNext(); ) {
            Dish dish = iterator.next();
            if(Character.isDigit(dish.getName().charAt(0))){
                iterator.remove();
            }
        }

        /**
         * removeIf를 통해 깔끔하게 해결한 모습
         */
        dishes.removeIf(dish -> Character.isDigit(dish.getName().charAt(0)));


        /**
         * replaceAll을 사용
         */

        List<String> strings1 = Arrays.asList("a", "b", "c");
        strings1.replaceAll(s -> s.toUpperCase(Locale.ROOT));
        System.out.println("strings1 = " + strings1);

        /**
         * Map forEach
         */

        Map<String, Integer> a1 = Map.of("a", 1, "b", 2, "c", 3);
        a1.forEach((k, v) -> System.out.println("k + v = " + k + v)); //편하게 출력 가능

        /**
         *  정렬
         */
        Map<String, String> map = Map.ofEntries(Map.entry("a", "2"), Map.entry("c", "1"), Map.entry("b", "3"));

        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(System.out::println);


        /**
         * 계산 패턴
         */

        Map<String, String> bin = Map.of("bin", "su", "kim", "ho", "hi", "world");
        String friend = "bin";
        //bin.computeIfPresent("bin", value -> new ArrayList()).add("Hello World");


        /**
         * map.remove(key);
         * map.remove(key,value);
         * 위와 같이 사용 가능.
         */


        /**
         * 교체 패턴
         */
        Map<String, Integer> changeMap = new HashMap<>();
        changeMap.put("1",1);
        changeMap.put("2",2);
        changeMap.replaceAll((k, v) -> v + 3);
        System.out.println("changeMap = " + changeMap);
    }
}
