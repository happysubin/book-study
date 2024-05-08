package book.bestpractice.part02.chapter20.domain.repository

import book.bestpractice.part02.chapter20.domain.BookReview
import org.springframework.data.jpa.repository.JpaRepository

interface BookReviewRepository: JpaRepository<BookReview, Long> {
}