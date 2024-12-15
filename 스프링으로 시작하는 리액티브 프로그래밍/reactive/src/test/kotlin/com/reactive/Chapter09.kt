package com.reactive

import org.junit.jupiter.api.Test
import org.junit.platform.commons.logging.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Sinks
import reactor.core.scheduler.Schedulers
import java.util.stream.IntStream

class Chapter09 {

    val log = LoggerFactory.getLogger(Chapter09::class.java)

    /**
     * create Operator를 사용해서 프로그래밍 방식으로 signal을 전송할 수 있으며, Reactor Sequence를 단계적으로 나누어서 여러 개의 스레드로 처리할 수 있다.
     *
     * 아래코드에서 작업을 처리한 후,
     * 그 결과 값을 반환하는 doTask() 메서드가 싱글 스레드가 아닌 여러 개의 스레드에서 각각의 전혀 다른 작업들을 처리한 후, 처리 결과를 반환하는 상황이 발생할 수 있다.
     * 이 같은 상황에서 적절하게 사용할 수 있는 방식이 바로 sinks이다.
     */
    @Test
    fun Exmaple9_1() {

        //총 3개의 스레드로 실행된다.
        val tasks = 6

        Flux
            // 아래 코드가 Operator가 처리해야 할 작업의 개수만큼 doTask() 메서드를 호출해서 작업ㅇ르 처리한 후, 결과를 리턴 받는다.
            .create { sink ->
                IntStream
                    .range(1, tasks)
                    .forEach { sink.next(doTasks(it)) } //elsatic 스레드에서 doTask 작업이 실행된다.
            }
            .subscribeOn(Schedulers.boundedElastic())
            .doOnNext { log.info {"create = $it ${Thread.currentThread().name}"} }
            .publishOn(Schedulers.parallel())
            //ㅊ가적인 가공 처리
            .map { "$it success!" } //paralle-l 스레드에서 실행
            .doOnNext { log.info{ "map = ${it} ${Thread.currentThread().name}"}}
            .publishOn(Schedulers.parallel())
            .subscribe { log.info{ "onNext = $it ${Thread.currentThread().name}"}} //parallel-1이 실행

        Thread.sleep(1000L)
    }

    private fun doTasks(taskNumber: Int): String {
        return "task $taskNumber result"
    }

    /**
     * 코드 실행 결과를 보면 doTask 메서드나 루프를 돌 때마다 새로운 스레드에서 실행되기 때문에 총 5개의 스레드에서 실행되고
     * map() Operator에서의 가공 처리는 parallel-2 스레드, Subscriber에서 전달받은 데이터의 처리는 parallel-1 스레드에서 실행되어 코드 9-2는 총 7개의 스레드가 실행되었다.
     *
     * 이처럼 Sinks는 프로그래밍 방식으로 Signal을 전송할 수 있으며, 멀티스레드 환경에서 스레드 안전성을 보장받을 수 있다는 장점이 있다.
     */
    @Test
    fun example9_2() {
        val tasks = 6

        val unicastSink: Sinks.Many<String> = Sinks
            .many()
            .unicast()
            .onBackpressureBuffer()

        val fluxView = unicastSink.asFlux()

        IntStream
            .range(1, tasks)
            .forEach { n ->
                try {
                    Thread {
                        //doTask() 메서드가 루프를 돌 때마다 새로운 스레드에서 실행된다.
                        unicastSink.emitNext(doTasks(n), Sinks.EmitFailureHandler.FAIL_FAST)
                    }.start()
                    Thread.sleep(100L)
                } catch (e: InterruptedException) { }
            }

        fluxView
            .publishOn(Schedulers.parallel())
            .map { "$it success!" }
            .doOnNext { log.info{ "map(): $it ${Thread.currentThread().name}"} }
            .publishOn(Schedulers.parallel())
            .subscribe {
                log.info { "onNext = ${it} ${Thread.currentThread().name}\""}
            }

        Thread.sleep(3000L)
    }

    @Test
    fun example9_4() {
        val sinkOne: Sinks.One<String> = Sinks.one()

        val mono: Mono<String> = sinkOne.asMono()

        /**
         *  FAIL_FAST는 람다 표현식으로 표현된 EmitFailureHandler 인터페이스의 구현 객체.
         *  EmitFailureHandler 객체를 통해서 emit 도중에 발생한 에러에 대해 빠르게 실패 처리한다.
         *  빠르게 실패 처리한다는 의미는 에러가 발생했을 때 재시도를 하지 않고 즉시 실패 처리를 한다는 의미.
         *  이렇게 빠른 실패 처리를 함으로써 스레드 간의 경합 등으로 발생하는 교착 상태 등을 미연에 방지할 수 있는데, 이는 결과적으로 스레드 안전성을 보장하기 위함
         */
        sinkOne.emitValue("Hello Reactor", Sinks.EmitFailureHandler.FAIL_FAST)

        /**
         * onNextDropped: Hi Reactor 로그 확인이 가능하다.
         * Sinks.One으로 아무리 많은 수의 데이터를 emit 해도 처음 emit한 데이터는 정상적으로 emit되지만 나머지 데이터들은 Drop된다는 사실을 알 수 있다.
         */
        sinkOne.emitValue("Hi Reactor", Sinks.EmitFailureHandler.FAIL_FAST)

        mono.subscribe { println("it = ${it}")}
        mono.subscribe { println("it2 = ${it}")}
    }

    @Test
    fun example9_8() {
        val unicastSink = Sinks.many().unicast().onBackpressureBuffer<Int>()

        val fluxView = unicastSink.asFlux()

        unicastSink.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST)
        unicastSink.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST)

        fluxView.subscribe { println("it = ${it}")}

        unicastSink.emitNext(3, Sinks.EmitFailureHandler.FAIL_FAST)

        /**
         * 아래 주석을 해제하면 에러가 발생
         * reactor.core.Exceptions$ErrorCallbackNotImplemented: java.lang.IllegalStateException: Sinks.many().unicast() sinks only allow a single Subscriber
         * Caused by: java.lang.IllegalStateException: Sinks.many().unicast() sinks only allow a single Subscriber
         *
         * UnicastSpec의 기능이 단 하나의 Subscriber에게만 데이터를 emit하는 것이기 때문에 에러가 발생
         */

        //fluxView.subscribe { println("it2 = ${it}")}

    }

    @Test
    fun example9_9() {
        /**
         * MulticastSpec을 활용
         *
         * 결과 코드
         * it = 1
         * it = 2
         * it = 3
         * it2 = 3
         *
         * Subscriber는 마지막 세번째 데이터만 수신함
         *
         * Sinks가 Publisher의 역할을 할 경우 기본적으로 HotPublisher의 역할로 동작하며, 특히 onBackpressureBuffer() 메서드는
         * Warm up의 특징을 가지는 Hot Sequence로 동작하기 때문에 첫 번째 구독이 발생한 시점에 Downstream 쪽으로 데이터가 전달되는 것이다.
         */
        val multicastSink = Sinks.many().multicast().onBackpressureBuffer<Int>()

        val fluxView = multicastSink.asFlux()

        multicastSink.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST)
        multicastSink.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST)

        fluxView.subscribe {
            println("it = ${it}")
        }

        fluxView.subscribe {
            println("it2 = ${it}")
        }

        multicastSink.emitNext(3, Sinks.EmitFailureHandler.FAIL_FAST)
    }

    @Test
    fun example9_10() {
        /**
         * replay() 메서드를 호출하면 리턴 값으로 MulticastReplaySpec을 리턴한다.
         * 위 클래스는 emit된 데이터라도 Subscriber가 전달받을 수 있게 하는 다양한 메서드들이 정의되어 있다.
         *
         * limit() 메서드는 emit된 데이터 중에서 파라미터로 입력한 개수만큼 가장 나중에 emit된 데이터부터 Subscriber에게 전달하는 기능을 한다.
         * 즉 emit된 데이터 중에서 2개만 뒤로 돌려서 전달하겠다는 의미
         *
         * 실행 결과
         *
         * it = 2
         * it = 3
         * it = 4
         * it2 = 3
         * it2 = 4
         *
         * 실행 결과를 보면 첫 번째 Subscriber의 입장에서는 구독 시점에 이미 세 개의 데이터가 emit되었기 때문에
         * 마지막 2개를 뒤로 되돌린 숫자가 2이므로 2부터 전달된다.
         *
         * 두 번째 Subscriber의 경우, 구독 전에 숫자 4의 데이터가 한 번 더 emit 되었기 때문에 두 번째 Subscriber의 구독 시점에 마지막 2개를 뒤로 돌린 숫자가 3이므로 3부터 전달된다.
         */
        val replaySink = Sinks.many().replay().limit<Int>(2)
        val fluxView = replaySink.asFlux()

        replaySink.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST)
        replaySink.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST)
        replaySink.emitNext(3, Sinks.EmitFailureHandler.FAIL_FAST)
        //replaySink.emitNext(9, Sinks.EmitFailureHandler.FAIL_FAST)


        fluxView.subscribe { println("it = ${it}")}

        replaySink.emitNext(4, Sinks.EmitFailureHandler.FAIL_FAST)
        //replaySink.emitNext(5, Sinks.EmitFailureHandler.FAIL_FAST)
        //replaySink.emitNext(6, Sinks.EmitFailureHandler.FAIL_FAST)

        fluxView.subscribe { println("it2 = ${it}")}
    }
}