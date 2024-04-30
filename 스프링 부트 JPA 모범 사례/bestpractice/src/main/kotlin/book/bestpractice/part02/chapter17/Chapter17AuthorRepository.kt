package book.bestpractice.part02.chapter17

import org.springframework.data.jpa.repository.JpaRepository

interface Chapter17AuthorRepository: JpaRepository<Chapter17Author, Long> {
}