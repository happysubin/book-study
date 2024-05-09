package book.bestpractice.part02.chapter20.domain.repository

import book.bestpractice.part02.chapter20.domain.BookReview
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BookReviewRepository: JpaRepository<BookReview, Long> {


    @Query("select br from BookReview br where br.id = :id")
    fun fetchById(id: Long): BookReview?

    @Query("select br from BookReview br where br.id in :ids")
    fun findAllById(ids: List<Long>?): List<BookReview>
}