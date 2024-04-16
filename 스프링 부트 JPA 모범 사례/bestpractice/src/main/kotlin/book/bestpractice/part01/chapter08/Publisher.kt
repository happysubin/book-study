package book.bestpractice.part01.chapter08

import book.bestpractice.part01.chapter01.Book
import jakarta.persistence.*

@Entity
class Publisher(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val company: String,

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "publisher", orphanRemoval = true)
    val books: MutableList<Book> = mutableListOf()



) {
}