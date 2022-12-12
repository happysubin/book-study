package parallel;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;
import java.util.stream.Stream;

//반드시 src.jmg.java 경로에서 진행해야 한다.

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 2, jvmArgs = {"-Xms4G", "-Xms4G"})
@State(Scope.Benchmark)
public class ParallelStreamBenchmark {

    private static final long N = 10_000_000L;

//    @Benchmark
//    public long sequentialSum(){
//        return Stream.iterate(1L, i -> i + 1)
//                .limit(N)
//                .reduce(0L, Long::sum);
//    }

//    @TearDown(Level.Invocation)
//    public void tearDown(){
//        System.gc();
//    }

//    @Benchmark
//    public long iterativeSum(){
//        long result = 0;
//        for (long i = 1L; i <= N; i++) {
//            result += i;
//        }
//        return result;
//    }

//    @TearDown(Level.Invocation)
//    public void tearDownV2(){
//        System.gc();
//    }

//    @Benchmark
//    public long parallelSum(){
//        return Stream.iterate(1L, i -> i + 1)
//                .limit(N)
//                .parallel()
//                .reduce(0L, Long::sum);
//    }

    @Benchmark
    public long parallelSum(){
        return LongStream.rangeClosed(1, N)  //훨씬 특화된 메서드 사용. 청크도 가능하고 언박싱 박싱도 필요 없다.
                .parallel()
                .reduce(0L, Long::sum);
    }

}