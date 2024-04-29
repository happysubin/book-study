package book.bestpractice.part02.chapter14

import org.springframework.data.jpa.repository.JpaRepository


interface Chapter14BookRepository: JpaRepository<Chapter14Book, Long> {
}