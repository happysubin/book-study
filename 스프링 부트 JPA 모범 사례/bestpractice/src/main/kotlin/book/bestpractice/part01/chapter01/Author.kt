package book.bestpractice.part01.chapter01

import jakarta.persistence.*

@Entity
@NamedEntityGraph(
    name = "author-books-publisher-graph",
    attributeNodes = [
        NamedAttributeNode("books")
    ],
    subgraphs = [
        NamedSubgraph(
            name = "publisher-subgraph",
            attributeNodes = [
                NamedAttributeNode("publisher")
            ]
        )
    ]
)
class Author (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val name: String,
    val genre: String,
    val age: Int,


    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "author", orphanRemoval = true)
    val books: MutableList<Book> = mutableListOf()
) {

    fun addBook(book: Book) {
        this.books.add(book)
        book.setAuthor(this)
    }

    fun removeBook(book: Book) {
        book.setAuthor(null)
        this.books.remove(book)
    }

    fun removeBooks() {
        val iterator = books.iterator()
        while(iterator.hasNext()) {
            val book = iterator.next()
            book.setAuthor(null)
            iterator.remove()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Author) return false

        if (id != other.id) return false
        if (name != other.name) return false
        if (genre != other.genre) return false
        if (age != other.age) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + genre.hashCode()
        result = 31 * result + age
        return result
    }

    override fun toString(): String {
        return "Author(id=$id, name='$name', genre='$genre', age=$age)"
    }

}