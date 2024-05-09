package book.bestpractice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
class BestpracticeApplication

fun main(args: Array<String>) {
	runApplication<BestpracticeApplication>(*args)
}
