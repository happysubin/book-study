package book.bestpractice.part03.chapter24

import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import java.io.Serializable

@MappedSuperclass
open class BaseAuthor (

    @Id
    val id: Long = 0,

    val age: Int? = null,
    val name: String? = null,
    val genre: String? = null,

): Serializable {
}