package com.reactive

import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.util.context.Context
import reactor.util.context.ContextView
import java.util.function.Function

class Example11 {

    companion object {
        const val HEADER_AUTH_TOKEN: String = "authToken"
    }

    @Test
    fun example11_1() {
        Mono
            /**
             * 아래 deferContextual()이라는 Operator를 사용해 Context에서 데이터를 읽는다.
             * defer() Operator와 같은 원리로 동작하는데, Context에 저장된 데이터와 원본 데이터 소스의 처리를 지연시키는 역할을한다.
             *
             * Context에 데이터를 쓸 때는 Context를 사용하지만, Context에 저장된 데이터를 읽을 때는 ContextView를 사용한다.
             */
            .deferContextual {ctx ->
                Mono
                    .just("Hello " + ctx.get("firstName"))
                    .doOnNext { println("just doOnNext = ${it} ${Thread.currentThread().name}") }
            }
            .subscribeOn(Schedulers.boundedElastic())
            .publishOn(Schedulers.parallel())
            .transformDeferredContextual { mono, ctx -> mono.map {
                println("map Thread.currentThread().name = ${Thread.currentThread().name}") //parallel
                it + " " + ctx.get("lastName")
            } }
            /**
             * 아래 Operator가 Context에 데이터를 쓰는 작업을 처리. 실제로 데이터를 쓰는 동작은 put()을 사용해 구현
             */
            .contextWrite {
                println("last Thread.currentThread().name = ${Thread.currentThread().name}") //main
                it.put("lastName", "Jobs")
            }
            .contextWrite {
                println("first Thread.currentThread().name = ${Thread.currentThread().name}") //main
                it.put("firstName", "Steve")
            }
            .subscribe {
                println("it = ${it}")
            }

        Thread.sleep(2000L)
    }

    @Test
    fun example11_3() {
        val key1 = "company"
        val key2 = "firstName"
        val key3 = "lastName"

        Mono
            .deferContextual { ctx ->
                Mono
                    .just(""+ ctx.get(key1) + ", " + ctx.get(key2) + " "+ ctx.get(key3))
            }

        Mono
            .deferContextual {ctx ->
                println("ctx Thread.currentThread().name = ${Thread.currentThread().name}")
                Mono
                    .just("Hello " + ctx.get("firstName"))
            }
            .publishOn(Schedulers.parallel())
            .contextWrite {
                println("ctx write 1 Thread.currentThread().name = ${Thread.currentThread().name}")
                it.putAll(Context.of(key2, "Steve", key3, "Jobs").readOnly())
                //readOnly가 Context를 ContextView로 변환해준다.
            }
            .contextWrite {
                println("ctx write 2 Thread.currentThread().name = ${Thread.currentThread().name}")
                it.put(key1, "Apple")
            }
            .subscribe {
                println("onNext = ${it} ${Thread.currentThread().name}")
            }

        Thread.sleep(2000)
    }

    @Test
    fun example11_5() {
        val key1 = "company"

        val mono: Mono<String> = Mono
            .deferContextual {
                Mono.just("Company: " + it.get(key1))
            }
            .publishOn(Schedulers.parallel())


        mono.contextWrite {
            it.put(key1, "Apple")
        }.subscribe {
            println("it = ${it} ${Thread.currentThread().name}") //it = Company: Apple parallel-1
        }

        mono.contextWrite {
            it.put(key1, "MS")
        }.subscribe {
            println("it2 = ${it} ${Thread.currentThread().name}") //it2 = Company: MS parallel-2
        }

        Thread.sleep(2000)
    }

    @Test
    fun example11_6() {
        val key1 = "company"
        val key2 = "name"

        Mono.deferContextual {
            Mono
                .just (
                    it.get<String>(key1)
                )
        }
            .publishOn(Schedulers.parallel())
            .contextWrite {
                //println("write1 Thread.currentThread().name = ${Thread.currentThread().name}")
                it.put(key2, "Bill")
            }
            .transformDeferredContextual { mono, ctx ->
                //println("transform Thread.currentThread().name = ${Thread.currentThread().name}")
                mono.map {"" + it + ", "+ ctx.getOrDefault(key2, "Steve")  }
            }
            .contextWrite {
                //println("write2 Thread.currentThread().name = ${Thread.currentThread().name}")
                it.put(key1, "Apple")
            }
            .subscribe {
                println("onNext = ${it} ${Thread.currentThread().name}")
            }
        /**
         * 결과
         * onNext = Apple, Steve parallel-1
         * Bill대신에 Steve가 출력됨.
         *
         * Context의 경우, Operator 체인상의 아래에서 위로 전파되는 특징이 있다.
         *
         *
         */
        Thread.sleep(2000)
    }


    /** 결과
     * it = Apple, Steve, | CEO
     *
     * 실행 결과를 보면 바깥쪽 Sequence에 연결된 Context에 쓴 값인 Apple을 Inner Sequence에서 읽을 수 있다는 사실을 알 수 있다.
     */
    @Test
    fun example11_7() {

        val key1 = "company"
        Mono
            .just<String>("Steve")

            //다음 주석을 해제하면 role이라는 키가 없으므로 에러가 발생.
            //따라서 Inner Sequence 외부에서는 InnerSequence 내부 Context에 저장된 데이터를 읽을수 없음을 알 수 있다.
//            .transformDeferredContextual<Any> { stringMono, ctx ->
//                val role = ctx.get<String>("role")
//                Mono.just(role ?: "No Role")
//            }
            .flatMap { name: Any ->
                //아래가 Inner Sequence
                Mono.deferContextual { ctx: ContextView ->
                    Mono
                        .just<String>("" + ctx.get<Any>(key1) + ", " + name)
                        .transformDeferredContextual { mono, innerCtx ->
                            mono.map { "$it, | " + innerCtx.get("role") }
                        }
                        .contextWrite { it.put("role", "CEO") }
                }
            }
            .publishOn(Schedulers.parallel())
            .contextWrite { it.put(key1, "Apple") }
            .subscribe { println("it = ${it}") }

        Thread.sleep(100L);

    }

    /**
     * 설명은 다음과 같다.
     *
     * 인증된 도서 관리자가 신규 도서를 등록하기 윟 ㅐ도서 정보와 인증 토큰을 서버로 전송한다고 가정
     *
     * 1. 도서 정보인 Book을 전송하기 위해 Mono<Book> 객체를 postBook() 메서드의 파라미터로 전달
     * 2. zip() Operator를 사용해 Mono<Book> 객체와 인증 토큰 정보를 의미하는 Mono<String> 객체를 하나의 Mono로 합친다. 이때 합쳐진 Mono는 Mono<Tuple2>의 객체가 된다
     * 3. flatMap() Operator 내부에서 도서 정보를 전송한다.
     *
     * Mono가 어떤 과정을 거치든 상관없이 가장 마지막에 리턴된 Mono를 구독하기 직전에 contextWrite을 저장하기 때문에 Operator 체인의 위쪽으로 전파되고,
     * Operator 체인 어느 위치에서든 Context에 접근할 수 있다.
     *
     * it = POST the book(Reactor's Bible, Kevin) with token: eyJhbGciOi
     */
    @Test
    fun example11_8() {
        val mono =
            postBook(
                Mono.just(
                    Book(
                        "abcd-1111-3533-2809",
                        "Reactor's Bible",
                        "Kevin"
                    )
                )
            ).contextWrite(Context.of(HEADER_AUTH_TOKEN, "eyJhbGciOi"))

        mono.subscribe { println("it = ${it}")}
    }

    private fun postBook(book: Mono<Book>): Mono<String> {
        return Mono.zip(
            book,
            Mono.deferContextual { ctx ->
                Mono.just(ctx.get<String>(HEADER_AUTH_TOKEN))
            }
        )
            .flatMap { tuple ->
                val response = "POST the book(${tuple.t1.bookName}, ${tuple.t1.author}) with token: ${tuple.t2}"
                Mono.just(response) // HTTP POST 전송을 했다고 가정
            }
    }


    data class Book(
        val isbn: String,
        val bookName: String,
        val author: String,
    )
}