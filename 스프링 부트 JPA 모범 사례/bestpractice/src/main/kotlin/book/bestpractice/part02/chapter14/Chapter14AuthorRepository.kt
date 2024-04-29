package book.bestpractice.part02.chapter14

import org.springframework.data.jpa.repository.JpaRepository


interface Chapter14AuthorRepository: JpaRepository<Chapter14Author, Long> {
}