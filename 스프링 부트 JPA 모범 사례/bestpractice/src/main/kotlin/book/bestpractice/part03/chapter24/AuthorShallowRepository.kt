package book.bestpractice.part03.chapter24

import org.springframework.data.jpa.repository.JpaRepository

interface AuthorShallowRepository: JpaRepository<AuthorShallow, Long> {
}