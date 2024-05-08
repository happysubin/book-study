package book.bestpractice.part02.chapter20.service

import book.bestpractice.part02.chapter20.domain.BookReview
import book.bestpractice.part02.chapter20.domain.repository.BookReviewRepository
import book.bestpractice.part02.chapter20.domain.repository.BookV20Repository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class BookStoreService(
    private val bookV20Repository: BookV20Repository,
    private val bookReviewRepository: BookReviewRepository
) {

    @Transactional
    fun postReview(bookReview: BookReview, bookId: Long) {
        val book = bookV20Repository.findById(bookId).orElseThrow()

        bookReview.book = book
        bookReview.registerReviewEvent()

        bookReviewRepository.save(bookReview)
    }
}