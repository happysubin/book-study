package com.reactive

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReactiveApplication

fun main(args: Array<String>) {
	System.setProperty("reactor.netty.ioWorkerCount", "1")
	runApplication<ReactiveApplication>(*args)
}
