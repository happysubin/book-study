package book.bestpractice.part02.chapter20.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
class BookV20(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val title: String,
    val isbn: String,
    val author: String,

    @OneToMany(mappedBy = "book", cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    val reviews: MutableList<BookReview> = mutableListOf(),

    ) {
}