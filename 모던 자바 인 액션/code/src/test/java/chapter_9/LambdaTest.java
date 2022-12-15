package chapter_9;

import chapter_2.section_3.ApplicationV3;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static chapter_2.section_3.ApplicationV3.filter;

public class LambdaTest {

    @Test
    void testMoveRightBy() {

        //given
        Point p1 = new Point(5, 5);

        //when
        Point p2 = p1.moveRightBy(10);

        //then
        Assertions.assertEquals(15, p2.getX());
        Assertions.assertEquals(5, p2.getY());
    }

    @Test
    void testComparingTwoPoints(){

        //given
        Point p1 = new Point(5, 5);

        //when
        Point p2 = p1.moveRightBy(10);

        //then
        int result = Point.compareByXAndThenY.compare(p1, p2);
        Assertions.assertTrue(result < 0);
    }

    @Test
    void textMoveAllPointsRightBy(){

        List<Point> points = Arrays.asList(new Point(5, 5), new Point(10, 5));
        List<Point> expectedPoints = Arrays.asList(new Point(15, 5), new Point(20, 5));

        List<Point> newPoints = Point.moveAllPointsRightBy(points, 10);
        Assertions.assertEquals(expectedPoints, newPoints);
    }


    @Test
    void testFilter() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
        List<Integer> even = filter(numbers, i -> i % 2 == 0);
        List<Integer> smallerThanThree = filter(numbers, i -> i < 3);

        Assertions.assertEquals(Arrays.asList(2,4), even);
        Assertions.assertEquals(Arrays.asList(1,2), smallerThanThree);

    }


    public static <T> List<T> filter(List<T> inventory, Predicate<T> predicate){

        List<T> result = new ArrayList<>();
        for (T e : inventory) {
            if(predicate.test(e)){
                result.add(e);
            }
        }
        return result;
    }


    @Test
    void list(){
        List<Point> points = Arrays.asList(new Point(1, 2), null);
        points.stream()
                .map(Point::getX)
                .forEach(System.out::println);
    }

    @Test
    void logging(){

        List<Boolean> collect = List.of(1, 2, 3, 4, 5, 6, 7)
                .stream()
                .peek(System.out::println)
                .map(x -> x + 9)
                .peek(System.out::println)
                .map(x -> x % 2 == 0)
                .peek(System.out::println)
                .limit(3)
                .peek(System.out::println)
                .collect(Collectors.toList());

        System.out.println("collect = " + collect);


    }
}
