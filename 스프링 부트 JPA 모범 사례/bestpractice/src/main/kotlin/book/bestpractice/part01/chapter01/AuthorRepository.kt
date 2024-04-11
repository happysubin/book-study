package book.bestpractice.part01.onetomanybi

import org.springframework.data.jpa.repository.JpaRepository

interface AuthorRepository: JpaRepository<Author, Long> {
}