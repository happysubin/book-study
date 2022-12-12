package chapter_7;

import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Application {

    private static final String SENTENCE = "HELLO MY NAME IS       JAVA" +
            "AWESOME!!!      WOW !!!!!!!!        OMG!!!! ";

    public static void main(String[] args) {

        int i = Runtime.getRuntime().availableProcessors();// 기기의 프로세서와 동일함/

        /**
         * 여러 스레드에서 접근하면 공유 변수에 문제가 발생함.
         */
        Accumulator accumulator = new Accumulator();
        long n = 10000000L;
        LongStream.rangeClosed(1, n).forEach(accumulator::add);
        System.out.println("accumulator.total = " + accumulator.total);

        /**
         * 포크/조인 프레임워크를 살펴봄.
         * n까지의 자연수 덧셈 작업을 병렬로 수행.
         */

        long result = forkJoinSum();

        /**
         * Spliterator 구현.
         * 문자열의 단어 수를 계산.
         */

        int i1 = countWordsIteratively(SENTENCE);
        System.out.println("i1 = " + i1);


        Stream<Character> stream = IntStream.rangeClosed(0, SENTENCE.length() -1 )
                .mapToObj(SENTENCE::charAt);
        //System.out.println(countWords(stream));

        
        //System.out.println(countWords(stream.parallel()));

        WordCounterSpliterator spliterator = new WordCounterSpliterator(SENTENCE);
        Stream<Character> finalStream = StreamSupport.stream(spliterator, true);
        System.out.println("countWords(finalStream) = " + countWords(finalStream));

    }

    private static long forkJoinSum() {
        long[] numbers = LongStream.rangeClosed(1, 10_000_000).toArray();
        ForkJoinSumCalculator task = new ForkJoinSumCalculator(numbers);
        return new ForkJoinPool().invoke(task);
    }

    public static int countWordsIteratively(String s){
        int counter = 0;
        boolean lastSpace = true;

        for (char c : s.toCharArray()) {
            if (Character.isWhitespace(c)) {
                lastSpace = true;
            }
            else{
                if(lastSpace) counter++;
                lastSpace = false;
            }
        }
        return counter;
    }

    private static int countWords(Stream<Character> stream){
        WordCounter wordCounter = stream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
        return wordCounter.getCounter();
    }
}