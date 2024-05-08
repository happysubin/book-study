package book.bestpractice.part02.chapter20.domain.event

import book.bestpractice.part02.chapter20.domain.ReviewStatus
import book.bestpractice.part02.chapter20.domain.repository.BookReviewRepository
import jdk.jfr.TransitionTo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener
import java.util.*

@Service
class CheckReviewEventHandler(
    private val bookReviewRepository: BookReviewRepository
) {

    //@Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    fun handleCheckReviewEvent(event: CheckReviewEvent) {
        val bookReview = event.bookReview
        println("start")
        try {
            //리뷰어 이메일 등의 검토에 대한 처리를 시뮬레이션한다.
            val content = bookReview.content
            val email = bookReview.email

            Thread.sleep(10000)
        } catch (ex: InterruptedException) {
            Thread.currentThread().interrupt()
        }

        val random = Random()
        if(random.nextBoolean()) {
            bookReview.status = ReviewStatus.ACCEPT
            println("accepted!")
        }
        else {
            bookReview.status = ReviewStatus.REJECT
            println("rejected!")
        }

        bookReviewRepository.save(bookReview)
        println("done!")
    }
}