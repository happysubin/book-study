package chapter_5;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Pythagoras {
    public static void main(String[] args) {
        Stream<int[]> pytha1 = IntStream.rangeClosed(1, 100)//a 값 생성
                .boxed()
                .flatMap(a ->
                        IntStream.rangeClosed(a, 100) //b값 생성
                                .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0) //제곱근이 정수인지 확인
                                .mapToObj(b ->
                                        new int[]{a, b, (int) Math.sqrt(a * a + b * b)}) //집합 생성
                );

        pytha1.limit(5)
                .forEach( t ->
                        System.out.println(t[0] + " " + t[1] + " " + t[2] + "\n"));



        Stream<double[]> pytha2 = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100) //b값 생성
                                .mapToObj(
                                        b -> new double[]{a, b, (int) Math.sqrt(a * a + b * b)})
                                        .filter(t -> t[2] % 1 ==0));
    }
}
