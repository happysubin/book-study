package book.bestpractice.part02.chapter17

import book.bestpractice.part01.AbstractServiceTest
import book.bestpractice.part02.chapter14.Chapter14AuthorRepository
import book.bestpractice.part02.chapter14.Chapter14BookRepository
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired


class Chapter17  @Autowired constructor(
    private val authorRepository: Chapter17AuthorRepository
): AbstractServiceTest() {

}