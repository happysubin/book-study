package book.bestpractice.part02.chapter20.domain

import book.bestpractice.part02.chapter20.domain.event.CheckReviewEvent
import jakarta.persistence.*
import org.springframework.data.domain.AbstractAggregateRoot

@Entity
class BookReview(
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val content: String,
    var email: String,

    @Enumerated(EnumType.STRING)
    var status: ReviewStatus,

    @JoinColumn(name = "book_id")
    @ManyToOne(fetch = FetchType.LAZY)
    var book: BookV20? = null,

    ): AbstractAggregateRoot<BookReview>()  {


    fun registerReviewEvent() {
        registerEvent(CheckReviewEvent(this))
    }

    override fun toString(): String {
        return "BookReview(id=$id, content='$content', email='$email', status=$status, book=$book)"
    }


}