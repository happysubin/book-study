package com.reactive

import com.jayway.jsonpath.JsonPath
import org.junit.jupiter.api.Test
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.net.URI
import java.time.Duration
import java.util.*

class Chapter07 {

    /**
     * 코드 실행 결과를 보면 구독이 발생할 대마다 emit된 데이터를 처음부터 다시 전달받고 있음을 확인할 수 있다.
     */
    @Test
    fun example7_1() {
        val coldFlux = Flux
            .fromIterable(arrayListOf("Korea", "Japan", "Chinese"))
            .map { e -> e.lowercase(Locale.getDefault()) }

        coldFlux.subscribe {
            println("country = $it")
        }

        println("===================")
        Thread.sleep(2000L)

        coldFlux.subscribe {
            println("country = $it")
        }
    }

    /**
     * 출력 결과
     * it = A
     * it = B
     * it = C
     * it2 = C
     * it = D
     * it2 = D
     * it = E
     * it2 = E
     *
     * 이미 지나간 엘리먼트가 존재하기 때문에 2가 처리하지 못함
     */
    @Test
    fun example7_2() {
        val arr = arrayOf("A", "B", "C", "D", "E")

        val c1Flux = Flux
            .fromArray(arr)
            .delayElements(Duration.ofSeconds(1)) //데이터의 emit을 일정시간 지연시키는 Operator
            .share() //Colde Sequence를 Hot Sequence로 동작하게 해줌

        /**
         * share() Operator는 원본 Flux를 멀티캐스트하는 새로운 Flux로 리턴
         * Flux를 멀티캐스트한다는 뜻은 여러 Subscriber가 한개의 원본 Flux를 공유한다는 의미.
         */

        c1Flux.subscribe {
            println("it = ${it}")
        }

        Thread.sleep(2500)

        c1Flux.subscribe {
            println("it2 = ${it}")
        }
        Thread.sleep(3000)
    }


    @Test
    fun example7_3() {
        val worldTimeUri = UriComponentsBuilder.newInstance().scheme("http")
            .host("worldtimeapi.org")
            .port(80)
            .path("/api/timezone/Asia/Seoul")
            .build()
            .encode()
            .toUri()

        val mono = getWorldTime(worldTimeUri)

        mono.subscribe { println("it = ${it}") }
        Thread.sleep(2000)
        mono.subscribe { println("it2 = ${it}") }

        Thread.sleep(3000)
    }

    @Test
    fun example7_4() {
        val worldTimeUri = UriComponentsBuilder.newInstance().scheme("http")
            .host("worldtimeapi.org")
            .port(80)
            .path("/api/timezone/Asia/Seoul")
            .build()
            .encode()
            .toUri()

        val mono = getWorldTime(worldTimeUri).cache()

        mono.subscribe { println("it = ${it}") }
        Thread.sleep(2000)
        mono.subscribe { println("it2 = ${it}") }

        Thread.sleep(3000)

    }


    fun getWorldTime(worldTimeUri: URI): Mono<String> {

        return WebClient.create()
            .get()
            .uri(worldTimeUri)
            .retrieve()
            .bodyToMono(String::class.java)
            .map {
                val jsonContext = JsonPath.parse(it)
                val dateTime = jsonContext.read<String>("$.datetime")
                dateTime
            }
    }
}