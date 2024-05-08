package book.bestpractice.part02.chapter20.domain.repository

import book.bestpractice.part02.chapter20.domain.BookV20
import org.springframework.data.jpa.repository.JpaRepository

interface BookV20Repository: JpaRepository<BookV20, Long> {
}