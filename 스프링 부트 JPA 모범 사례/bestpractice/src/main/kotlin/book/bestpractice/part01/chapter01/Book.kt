package book.bestpractice.part01.chapter01

import book.bestpractice.part01.chapter08.Publisher
import jakarta.persistence.*

@Entity
class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    private val title: String,
    private val isbn: String,

    @JoinColumn(name = "author_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private var author: Author? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private var publisher: Publisher? = null,

    private var price: Int = 0

) {
    fun setAuthor(author: Author?) {
        this.author = author
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Book) return false

        if (id != other.id) return false
        if (title != other.title) return false
        if (isbn != other.isbn) return false
        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + title.hashCode()
        result = 31 * result + isbn.hashCode()
        return result
    }

    override fun toString(): String {
        return "Book(id=$id, title='$title', isbn='$isbn')"
    }
}