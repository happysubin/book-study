package com.reactive.part03

import jakarta.annotation.PostConstruct
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.util.concurrent.ConcurrentHashMap

@RequestMapping("/v1/book")
@RestController
class SpringReactiveBranchOfficeController {

    val map = ConcurrentHashMap<Long, Book>()


    @PostConstruct
    fun init() {
        map.put(1L, Book(1L, "Book1"))
        map.put(2L, Book(2L, "Book2"))
        map.put(3L, Book(3L, "Book3"))
        map.put(4L, Book(4L, "Book4"))
        map.put(5L, Book(5L, "Book5"))

    }

    @GetMapping("/{bookId}")
    fun getBook(@PathVariable bookId: Long): Mono<Book> {
        Thread.sleep(5000)

        val book = map[bookId]!!
        return Mono.just(book)
    }
}