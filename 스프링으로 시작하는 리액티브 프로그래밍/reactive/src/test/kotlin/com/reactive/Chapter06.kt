package com.reactive

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


class Chapter06 {


	@Test
	fun example_6_2() {
		Flux.just("1", "2")
			.subscribe (
				{ value -> println("Received value: $value") }, // onNext: 각 항목 처리
				{ error -> println("Error: ${error.message}") }, // onError: 에러 처리
				{ println("Completed!",) }
			)
	}

	@Test
	fun example_6_5() {
		Flux
			.fromArray(arrayOf(1, 2, 3))
			.filter { num -> num > 2 }
			.map { num -> num * 2 }
			.subscribe {
				println(it)
			}
	}

	@Test
	fun example6_6() {
		val flux = Mono
			.justOrEmpty("Steve")
			.concatWith(Mono.justOrEmpty("Jobs"))
		flux.subscribe {
			println("it = ${it}")
		}
	}

	@Test
	fun example6_7() {
		Flux.concat(
			Flux.just("1", "2", "3"),
			Flux.just("4", "5", "6"),
			Flux.just("7", "8", "9")
		)
			.collectList()
			.subscribe {
				println("it = ${it}")
			}

	}

}
