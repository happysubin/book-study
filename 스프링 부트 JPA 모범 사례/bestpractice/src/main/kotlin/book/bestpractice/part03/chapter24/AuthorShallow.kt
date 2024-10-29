package book.bestpractice.part03.chapter24

import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "author")
class AuthorShallow: BaseAuthor() {
}