package book.bestpractice.part01.chapter07

import book.bestpractice.part01.chapter01.AuthorRepository
import book.bestpractice.part01.chapter01.BookRepository
import book.bestpractice.part01.chapter06.AppService
import book.bestpractice.part01.fixture.AuthorFactory
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class Chapter07Test @Autowired constructor(
    private val appService: AppService,
    private val authorRepository: AuthorRepository,
    private val bookRepository: BookRepository
){


    @Test
    fun queryMethodOverride() {
        val author = AuthorFactory.create()
        authorRepository.save(author)

        //left 조인 쿼리 확인
        authorRepository.findAll()
    }

    @Test
    fun queryBuilder() {
        val author1 = AuthorFactory.create()
        val author2 = AuthorFactory.create()

        authorRepository.saveAll(listOf(author1, author2))

        authorRepository.findByAgeLessThanOrderByNameDesc(23)
    }

    @Test
    fun queryAndJpql() {
        val author1 = AuthorFactory.create()
        val author2 = AuthorFactory.create()

        authorRepository.saveAll(listOf(author1, author2))

        authorRepository.fetchAllAgeBetween20And40()
    }
}