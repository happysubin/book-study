package book.bestpractice.part01.chapter10

import book.bestpractice.part01.AbstractServiceTest
import book.bestpractice.part01.chapter01.AuthorRepository
import book.bestpractice.part01.chapter01.BookRepository
import book.bestpractice.part01.chapter06.AppService
import book.bestpractice.part01.fixture.AuthorFactory
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

class Chapter10Test @Autowired constructor(
    private val appService: AppService,
    private val authorRepository: AuthorRepository,
    private val bookRepository: BookRepository,
    private val entityManager: EntityManager
): AbstractServiceTest() {


    @Transactional
    @Test
    fun test1() {
        val author = AuthorFactory.create()
        authorRepository.save(author)

        entityManager.clear()
        val result = authorRepository.findById(author.id).get()
        println(result.cheapBooks)
        println(result.restOfBooks)
    }
}