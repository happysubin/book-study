package book.bestpractice.part02.chapter16

import org.springframework.data.jpa.repository.JpaRepository

interface Chapter16AuthorRepository: JpaRepository<Chapter16Author, Long> {
}