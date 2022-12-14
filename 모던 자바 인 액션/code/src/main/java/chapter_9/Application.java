package chapter_9;

import chapter_4.Dish;
import chapter_4.MenuFactory;

import java.util.Collections;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Application {

    public static void main(String[] args) {

        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello");
            }
        };

        Runnable r2 = () -> System.out.println("Hello");


        int a = 10;

        Runnable r3 = () -> {
            //int a = 3; 컴파일 에러
            System.out.println("Hello");
        };

        Runnable r4 = new Runnable() {
            @Override
            public void run() {
                int a = 10;
                System.out.println("Hello");
            }
        };

        //doSomething(() -> System.out.println("HelloWorld")); 모호하다.
        //아래와 같이 명시적으로 남겨야 한다.
        doSomething((Runnable) () -> System.out.println("HelloWorld"));


        Integer reduce1 = MenuFactory.getDishes()
                .stream()
                .map(Dish::getCalories)
                .reduce(0, Integer::sum);
                //.reduce(0, (c1, c2) -> c1 + c2);

        MenuFactory
                .getDishes()
                .stream()
                .collect(Collectors.summingInt(Dish::getCalories));


        /**
         * 조건부 연기 실행
         */
        Logger logger = Logger.getLogger("logger");
        if(logger.isLoggable(Level.FINER)){
            logger.finer("logging" + generateDiagnostic());
        }

        logger.log(Level.FINER, "Problem: " + generateDiagnostic());
        logger.log(Level.FINER, () -> "Problem: " + generateDiagnostic());


    }

    private static String generateDiagnostic() {
        //어떤 진단 값을 리턴해야함. 원래는
        return null;
    }

    public static void doSomething(Runnable r){
        r.run();
    }

    public static void doSomething(Task a){
        a.execute();
    }

    interface Task{
        void execute();
    }
}
