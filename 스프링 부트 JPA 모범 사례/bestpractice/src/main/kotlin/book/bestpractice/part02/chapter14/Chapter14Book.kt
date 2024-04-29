package book.bestpractice.part02.chapter14

import jakarta.persistence.*

@Entity
class Chapter14Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val isbn: String,
    val title: String,

    @JoinColumn(name = "author_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val author: Chapter14Author
) {
}