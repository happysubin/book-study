package book.bestpractice.part03.chapter21

import book.bestpractice.part02.chapter20.domain.BookReview
import book.bestpractice.part02.chapter20.domain.repository.BookReviewRepository
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.awt.print.Book

@Service
class FetchService(
    private val em: EntityManager,
    private val bookReviewRepository: BookReviewRepository
) {


    @Transactional
    fun fetchTest(id: Long) {
        val bookReview = em.find(BookReview::class.java, id)
        bookReview.email = "changeeeee@email.com"
        println("bookReview = ${bookReview}")
        val bookReview2 = bookReviewRepository.fetchById(id) ?: RuntimeException() //JPQL을 호출하므로 이게 플러쉬가 된다.
        println("bookReview = ${bookReview2}")

        //플러쉬가 행해지므로 세션 수준 반복 읽기가 가능한것이다.
    }

    @Transactional(readOnly = true)
    fun inTest(ids: List<Long>) {
        val bookReviews = bookReviewRepository.findAllById(ids)

        for (bookReview in bookReviews) {
            println("bookReview = ${bookReview}")
        }
    }
}