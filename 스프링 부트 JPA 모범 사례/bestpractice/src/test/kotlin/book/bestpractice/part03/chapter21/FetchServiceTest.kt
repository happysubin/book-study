package book.bestpractice.part03.chapter21

import book.bestpractice.part01.AbstractServiceTest
import book.bestpractice.part02.chapter20.domain.BookReview
import book.bestpractice.part02.chapter20.domain.BookV20
import book.bestpractice.part02.chapter20.domain.ReviewStatus
import book.bestpractice.part02.chapter20.domain.repository.BookReviewRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

class FetchServiceTest @Autowired constructor(
    private val fetchService: FetchService,
    private val bookReviewRepository: BookReviewRepository
): AbstractServiceTest() {

    @Test
    fun fetchTest() {
        val result = bookReviewRepository.save(BookReview(
            content = "content",
            status = ReviewStatus.CHECK,
            email = "test@gmail.com"
        ))

        fetchService.fetchTest(result.id!!)
    }

    @Test
    @Transactional
    fun inTest() {
        val result1 = bookReviewRepository.save(BookReview(
            id = 1,
            content = "content",
            status = ReviewStatus.CHECK,
            email = "test@gmail.com"
        ))

        val result2 = bookReviewRepository.save(BookReview(
            id = 2,
            content = "content",
            status = ReviewStatus.CHECK,
            email = "test@gmail.com"
        ))

        //fetchService.inTest(listOf(result1.id!!, result2.id!!))
    }
}