package com.reactive.part03

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@RequestMapping("/v1/books")
@RestController
class SpringReactiveHeadOfficeController {

    val baseUrl = UriComponentsBuilder.newInstance().scheme("http")
        .host("localhost")
        .port(5050)
        .path("/v1/book")
        .build()
        .encode()
        .toUri()

    @GetMapping("/{book-id}")
    fun test(@PathVariable("book-id") bookId: Long) {
        for (i in 1..5) {
            getBook(i.toLong())
                .subscribe { book ->
                    println("bookId = ${book.id} ${LocalDateTime.now()}}")
                }
        }
    }

    fun getBook(bookId: Long): Mono<Book> {
        val getBookUrl = UriComponentsBuilder.fromUri(baseUrl)
            .path("/{book-id}")
            .build()
            .expand(bookId)
            .encode()
            .toUri()
        return WebClient
            .create()
            .get()
            .uri(getBookUrl)
            .retrieve()
            .bodyToMono(Book::class.java)
    }
}