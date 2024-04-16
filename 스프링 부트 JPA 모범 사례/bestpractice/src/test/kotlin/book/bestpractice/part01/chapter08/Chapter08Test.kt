package book.bestpractice.part01.chapter08

import book.bestpractice.part01.chapter01.AuthorRepository
import book.bestpractice.part01.chapter01.BookRepository
import book.bestpractice.part01.chapter06.AppService
import book.bestpractice.part01.fixture.AuthorFactory
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class Chapter08Test @Autowired constructor(
    private val appService: AppService,
    private val authorRepository: AuthorRepository,
    private val bookRepository: BookRepository
) {


    @Test
    fun subGraph() {
        val author = AuthorFactory.create()

        authorRepository.save(author)
        authorRepository.findAll()
    }
}