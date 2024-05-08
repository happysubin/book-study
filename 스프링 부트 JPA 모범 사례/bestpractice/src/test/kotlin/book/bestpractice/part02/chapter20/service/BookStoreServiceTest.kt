package book.bestpractice.part02.chapter20.service

import book.bestpractice.part01.AbstractServiceTest
import book.bestpractice.part02.chapter20.domain.BookReview
import book.bestpractice.part02.chapter20.domain.BookV20
import book.bestpractice.part02.chapter20.domain.ReviewStatus
import book.bestpractice.part02.chapter20.domain.repository.BookReviewRepository
import book.bestpractice.part02.chapter20.domain.repository.BookV20Repository
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BookStoreServiceTest @Autowired constructor(
    private val bookStoreService: BookStoreService,
    private val bookV20Repository: BookV20Repository,
    private val bookReviewRepository: BookReviewRepository,
    private val em: EntityManager
) {

    @Test
    fun checkReview() {
        val book = bookV20Repository.save(BookV20(title = "title", isbn = "isbn", author = "author"))
        val bookReview = bookReviewRepository.save(
            BookReview(
                content = "content",
                status = ReviewStatus.CHECK,
                email = "test@gmail.com"
            )
        )

        bookStoreService.postReview(BookReview(
            content = "content",
            status = ReviewStatus.CHECK,
            email = "test@gmail.com"
        ), book.id!!)
    }
}