package com.reactive

import org.junit.jupiter.api.Test
import org.reactivestreams.Subscription
import reactor.core.publisher.BaseSubscriber
import reactor.core.publisher.BufferOverflowStrategy
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.time.Duration

class Chapter08 {

    @Test
    fun Example8_1() {
        Flux.range(1, 5)
            .doOnRequest {
                println("it = ${it}") // Subscriber가 요청한 데이터의 요청 개수를 로그로 출력하도록 함
            }
            .subscribe(object : BaseSubscriber<Int>() {

                /**
                 * hookOnSubscribe() 메서드는 Subscriber 인터페이스에 정의된 onSubscribe() 메서드를 대신해 구독 시점에
                 * request() 메서드를 호출해서 최초 데이터 요청 개수를 제어하는 역할을 한다.
                 *
                 * hookOnNext() 메서드는 Subscriber 인터페이스에 저으이된 onNext() 메서드를 대신해 Publisher가 emit한 데이터를 전달받아 처리한 후에
                 * Publisher에게 또 다시 데이터를 요청하는 역할을 한다. 이때 역시 request() 메서드를 호출해서 데이터 요청 개수를 제어한다.
                 */
                override fun hookOnSubscribe(subscription: Subscription) {
                    request(1)
                }

                override fun hookOnNext(value: Int) {
                    Thread.sleep(2000)
                    println("hootOnNext, value = ${value}")
                    request(1)
                }
            })
    }

    /**
     * Exceptions$OverflowException가 발생하면서 종료된다.
     * Exceptions$OverflowException은 IlegalStateException을 상속했다.
     */
    @Test
    fun Example8_2() {
        println("Thread.currentThread().name = ${Thread.currentThread().name}")
        Flux
            .interval(Duration.ofMillis(1L)) //0부터 1씩 증가한 숫자를 0.001초에 한 번씩 아주 빠른 속도로 emit
            .onBackpressureError() // ERROR 전략을 적용하기위해 다음과 같은 Operator를 사용
            .doOnNext {
                println("it = ${it}, ${Thread.currentThread().name}") //디버깅용
            }
            /**
             * 아래 Operator는 Reactor Sequence 중 일부를 별도의 스레드에서 실행할 수 있게 해주는 Operator
             * 지금은 publishOn을 사용하면 별도의 스레드가 하나 더 실행된다고 생각하면 된다.
             */
            .publishOn(Schedulers.parallel())
            .subscribe (
                {
                    try {
                        /**
                         * Subscriber가 전달받은 데이터를 처리하는 데 0.005초 시간이 걸리도록 시뮬레이션했다.
                         * 이렇게 코드를 구성하면 Publisher에서 데이터를 emit하는 속도와 Subscriber가 전달받은 데이터를 처리하는 속도에 차이가 나기 때문에
                         * Backpressure 전략의 테스트가 가능해진다.
                         */
                        Thread.sleep(5L )
                    } catch (e: InterruptedException) {
                        println("onNext = ${it}")
                    }
                },
                { println("error = ${it}")}
            )

        Thread.sleep(2000L)
    }

    /**
     * 첫번째 DROP 구간에서 DROP이 시작되는 데이터는 숫자 256이고, Drop이 끝나는 데이터는 숫자 1025이다.
     * 이 구간 동안에는 버퍼가 가득 차 있는 상태임을 알 수 있다.
     * 숫자 1025까지 Drop되기 때문에 Subscriber 쪽에서는 숫자 1026부터 전달받아 처리하는 것을 볼 수 있다.
     * 그리고 두 번째 DROP 구간에서 DROP이 시작되는 데이터는 숫자 1218인 것으로 보아 Subscriber 쪽에서는 숫자 1217까지 데이터를 처리한다고 예상할 수 있다.
     *
     * Backpressure DROP 전략을 적용하면 버퍼가 가득 찬 상태에서는 버퍼가 비워질 때까지 데이터를 DROP 한다
     */
    @Test
    fun Example8_3() {
        Flux
            .interval(Duration.ofMillis(1L))
            /**
             * 아래 Operator는 Drop된 데이터를 파라미터로 전달받을 수 있기 때문에 Drop된 데이터가 되기전에 추가 작업을 진행할 수 있다.
             */
            .onBackpressureDrop {
                println("dropped = ${it}")
            }
            .publishOn(Schedulers.parallel())
            .subscribe (
                {
                    try {
                        Thread.sleep(5L)
                    } catch (e: InterruptedException) { }
                    println("onNext = ${it}")
                },
                {
                    println("error = ${it}")
                }
            )

        Thread.sleep(2000L)
    }

    /**
     * 코드를 실행해보면 Subscriber가 숫자 255를 출력하고 곧바로 그다음에 숫자 1037을 출력하는 것을 볼 수 있다.
     * 이는 버퍼가 가득 찼다가 버퍼가 다시 비워지는 시간 동안 emit되는 데이터가 가장 최근에 emit된 데이터가 된 후, 다음 데이터가 emit되면
     * 다시 폐기되는 이 과정을 반복하기 때문이다.
     */
    @Test
    fun Example8_4() {
        Flux
            .interval(Duration.ofMillis(1L))
            .onBackpressureLatest()
            .publishOn(Schedulers.parallel())
            .subscribe(
                {
                    try {
                        Thread.sleep(5L)
                    } catch (e: InterruptedException) { }
                    println("onNext = ${it}")
                },
                {
                    println("error = ${it}")
                }
            )

        Thread.sleep(2000L)
    }

    /**
     * 결과
     * it = 0
     * data = 0
     * it = 1
     * it = 2
     * it = 3
     * Overflow & Dropped = 3
     * onNext = 0
     * data = 1
     * it = 4
     * it = 5
     * Overflow & Dropped = 5
     * it = 6
     * Overflow & Dropped = 6
     * onNext = 1
     * data = 2
     * it = 7
     * it = 8
     * Overflow & Dropped = 8
     * it = 9
     * Overflow & Dropped = 9
     *
     * 자세한 설명은 책 180p
     */
    @Test
    fun Example8_5() {
        Flux
            .interval(Duration.ofMillis(300L))
            .doOnNext {
                println("it = ${it}")
            }
            .onBackpressureBuffer(2, //버퍼 최대 용량
                { println("Overflow & Dropped = ${it}")}, //버퍼 오버 플로가 발생했을 때, Drop 되는 데이터를 전달받아 후처리
                BufferOverflowStrategy.DROP_LATEST //Backpressure 전략 설정
            )
            .doOnNext {
                println("data = ${it}")
            }
            .publishOn(Schedulers.parallel(), false, 1)
            .subscribe ({
                try {
                    Thread.sleep(1000L)
                } catch (e: InterruptedException) { }
                println("onNext = ${it}")
            },
            {
                println("error = ${it}")
            })
        Thread.sleep(3000L)
    }

    /**
     * 코드 수행 결과
     * it = 0
     * it = 1
     * it = 2
     * it = 3
     * Overflow && Dropped = 1
     * onNext = 0
     * it = 4
     * it = 5
     * Overflow && Dropped = 3
     * it = 6
     * Overflow && Dropped = 4
     * onNext = 2
     * it = 7
     *
     * 자세한 내용은 책 184p
     */
    @Test
    fun Example8_6() {
        Flux
            .interval(Duration.ofMillis(300L))
            .doOnNext { println("it = ${it}")}
            .onBackpressureBuffer(2,
                { println("Overflow && Dropped = ${it}")},
                BufferOverflowStrategy.DROP_OLDEST)
            .publishOn(Schedulers.parallel(), false, 1)
            .subscribe({
                       try {
                           Thread.sleep(1000L)
                       } catch(e: InterruptedException) {}
                println("onNext = ${it}")
            }, {
                println("error = ${it}")
            })

        Thread.sleep(2500L)
    }
}