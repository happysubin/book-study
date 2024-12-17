package com.reactive

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers

class Example10 {

    @Test
    fun example10_1() {
        Flux
            .fromArray(arrayOf(1, 2, 3, 4, 5, 6, 7))
            .subscribeOn(Schedulers.boundedElastic())
            /**
             * 아래 모든 Operator은 boundedElastic-1에서 실행됨
             */
            .doOnNext {
                println("it = ${it} ${Thread.currentThread().name}")
            }
            .doOnSubscribe {
                println("it2 = ${it} ${Thread.currentThread().name}")
            }
            .subscribe {
                println("onNext = ${it} ${Thread.currentThread().name}")
            }

        Thread.sleep(1000L)
    }

    /**
     * 결과물
     *
     * it = 1 Test worker
     * it = 2 Test worker
     * it = 3 Test worker
     * it = 4 Test worker
     * it = 5 Test worker
     * it = 6 Test worker
     * it = 7 Test worker
     * onNext = 1 parallel-1
     * onNext = 2 parallel-1
     * onNext = 3 parallel-1
     * onNext = 4 parallel-1
     * onNext = 5 parallel-1
     * onNext = 6 parallel-1
     * onNext = 7 parallel-1
     */
    @Test
    fun example10_2() {
        Flux
            .fromArray(arrayOf(1, 2, 3, 4, 5, 6, 7))
            .doOnNext {
                println("it = ${it} ${Thread.currentThread().name}")
            }
            .doOnSubscribe {
                println("it2 = ${it} ${Thread.currentThread().name}")
            }
            .publishOn(Schedulers.parallel())
            .subscribe {
                println("onNext = ${it} ${Thread.currentThread().name}")
            }

        Thread.sleep(1000L)
    }

    /**
     * 결과
     * onNext = 4 parallel-4
     * onNext = 5 parallel-5
     * onNext = 6 parallel-6
     * onNext = 2 parallel-2
     * onNext = 1 parallel-1
     * onNext = 7 parallel-7
     * onNext = 3 parallel-3
     *
     * parallel() Operator는 emit된 데이터를 CPU의 논리적인 코어 수에 맞게 사전에 골고루 분배하는 역할
     * 실제로 병렬 작업을 수행할 스레드의 할당은 runOn() Operator가 담당한다.
     */
    @Test
    fun example10_3() {
        Flux
            .fromArray(arrayOf(1, 2, 3, 4, 5, 6, 7))
            .parallel()
            //.parallel(4) 스레드의 개수 지정 가능
            .runOn(Schedulers.parallel()) //이 부분을 주석하면 병렬 처리가 안됨
            .subscribe {
                println("onNext = ${it} ${Thread.currentThread().name}")
            }

        Thread.sleep(1000L)
    }

    /**
     * 실제로 psvm으로 줘서 돌려보면 모두 main에서 실행된다
     */
    @Test
    fun example10_5() {
        Flux
            .fromArray(arrayOf(1, 2, 3, 4, 5, 6, 7))
            .doOnNext { println("doOnNext array = ${it} ${Thread.currentThread().name}") }
            .filter { it > 3}
            .doOnNext { println("doOnNext filter = ${it} ${Thread.currentThread().name}") }
            .map { it * 10 }
            .doOnNext { println("doOnNext map = ${it} ${Thread.currentThread().name}") }
            .subscribe {
                println("onNext = ${it} ${Thread.currentThread().name}")
            }

        Thread.sleep(1000L)
    }

    /**
     * publishOn 이후에 수행된 스레드는 모두 parallel-1
     */
    @Test
    fun example10_6() {
        Flux
            .fromArray(arrayOf(1, 2, 3, 4, 5, 6, 7))
            .doOnNext { println("doOnNext array = ${it} ${Thread.currentThread().name}") }
            .publishOn(Schedulers.parallel())
            .filter { it > 3}
            .doOnNext { println("doOnNext filter = ${it} ${Thread.currentThread().name}") }
            .map { it * 10 }
            .doOnNext { println("doOnNext map = ${it} ${Thread.currentThread().name}") }
            .subscribe {
                println("onNext = ${it} ${Thread.currentThread().name}")
            }

        Thread.sleep(1000L)
    }


    /**
     * array는 main 스레드
     * doOnNext fitler는  parallel-2 스레드가 실행
     * 이후에는 모두 parallel-1 스레드가 실행
     */
    @Test
    fun example10_7() {
        Flux
            .fromArray(arrayOf(1, 2, 3, 4, 5, 6, 7))
            .doOnNext { println("doOnNext array = ${it} ${Thread.currentThread().name}") }
            .publishOn(Schedulers.parallel())
            .filter { it > 3}
            .doOnNext { println("doOnNext filter = ${it} ${Thread.currentThread().name}") }
            .publishOn(Schedulers.parallel())
            .map { it * 10 }
            .doOnNext { println("doOnNext map = ${it} ${Thread.currentThread().name}") }
            .subscribe {
                println("onNext = ${it} ${Thread.currentThread().name}")
            }

        Thread.sleep(1000L)
    }

    /**
     * publishOn() 이전까지의 Operator 체인은 subscribeOn()에서 지정한 boundedElastic-1 스레드에서 실행되고,
     * publishOn() 이후의 Operator 체인은 parallel-1 스레드에서 실행된다.
     *
     * 이처럼 subscribe() Operator의 publishOn() Operator를 함께 사용하면 원본 Publisher에서 데이터를 emit 하는 스레드와 emit된 데이터를 가공 처리하는
     * 스레드를 적절하게 분리할 수 있다.
     */
    @Test
    fun example10_8() {
        Flux
            .fromArray(arrayOf(1, 2, 3, 4, 5, 6, 7))
            .doOnNext { println("doOnNext array = ${it} ${Thread.currentThread().name}") }
            .subscribeOn(Schedulers.boundedElastic())
            .filter { it > 3}
            .doOnNext { println("doOnNext filter = ${it} ${Thread.currentThread().name}") }
            .publishOn(Schedulers.parallel())
            .map { it * 10 }
            .doOnNext { println("doOnNext map = ${it} ${Thread.currentThread().name}") }
            .subscribe {
                println("onNext = ${it} ${Thread.currentThread().name}")
            }

        Thread.sleep(1000L)
    }
}