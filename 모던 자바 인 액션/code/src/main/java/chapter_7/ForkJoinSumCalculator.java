package chapter_7;

import java.util.concurrent.RecursiveTask;

public class ForkJoinSumCalculator extends RecursiveTask<Long> {

    private final long[] numbers;
    private final int start;
    private final int end;
    private static final long THRESHOLD = 10_000;


    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers, 0 , numbers.length);
    }

    public ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        int length = end - start;
        if(length <= THRESHOLD){
            return computeSequentially(); //기준 값보다 작으면 순차적으로 계산
        }

        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length / 2); //배열의 첫번째 절반을 더하도록 서브태스크를 생성

        leftTask.fork();// ForkJoinPool의 다른 스레드로 새로 생성한 태스크를 비동기로 실행

        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length / 2, end); //배열의 나머지 절반을 더하도록 서브태스크를 생성
        Long rightResult = rightTask.compute();  // 두 번째 서브태스크를 동기 실행. 추가로 분할이 발생할 수 있다.
        Long leftResult = leftTask.join(); //첫 번째 서브태스크의 결과를 읽거나 아직 결과가 없으면 기다린다.

        return rightResult + leftResult; //두 서브태스크의 결과를 조합한 값이 이 태스크의 결과다.
    }

    private Long computeSequentially() {
        long sum = 0;

        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }
}
