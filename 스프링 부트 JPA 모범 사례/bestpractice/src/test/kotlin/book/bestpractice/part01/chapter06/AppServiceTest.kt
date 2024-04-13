package book.bestpractice.part01.chapter06

import book.bestpractice.part01.chapter01.Author
import book.bestpractice.part01.chapter01.AuthorRepository
import book.bestpractice.part01.chapter01.Book
import book.bestpractice.part01.chapter01.BookRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AppServiceTest @Autowired constructor(
    private val appService: AppService,
    private val authorRepository: AuthorRepository,
    private val bookRepository: BookRepository
){



    @Test
    fun deleteViaCascadeRemove() {
        val author = Author(name = "author", genre = "love", age = 23)
        author.addBook(Book(title = "title1", isbn = "isbn1"))
        author.addBook(Book(title = "title2", isbn = "isbn2"))
        author.addBook(Book(title = "title3", isbn = "isbn3"))


        authorRepository.save(author)

        println("=====")

        //delete문이 4번 출력된다. book이 증가할수록 delete문이 많아진다.
        appService.deleteViaCascadeRemove("author")
    }



    @Test
    fun deleteViaOrphanRemoval() {
        val author = Author(name = "author", genre = "love", age = 23)
        author.addBook(Book(title = "title1", isbn = "isbn1"))
        author.addBook(Book(title = "title2", isbn = "isbn2"))
        author.addBook(Book(title = "title3", isbn = "isbn3"))


        authorRepository.save(author)

        println("=====")

        //delete문이 4번 출력된다. book이 증가할수록 delete문이 많아진다. 바로 위 테스트와 동일
        appService.deleteViaOrphanRemoval("author")
    }

    @Test
    fun deleteViaIdentifiers() {
        val author = Author(name = "author", genre = "love", age = 23)
        author.addBook(Book(title = "title1", isbn = "isbn1"))
        author.addBook(Book(title = "title2", isbn = "isbn2"))
        author.addBook(Book(title = "title3", isbn = "isbn3"))


        authorRepository.save(author)

        println("=====")

        //쿼리 2번 발생
        appService.deleteViaIdentifiers("author")
    }

    @Test
    fun deleteViaBulkIn() {
        val author1 = Author(name = "author1", genre = "love", age = 34)
        author1.addBook(Book(title = "title1", isbn = "isbn1"))
        author1.addBook(Book(title = "title2", isbn = "isbn2"))
        author1.addBook(Book(title = "title3", isbn = "isbn3"))

        val author2 = Author(name = "author2", genre = "love", age = 34)
        author2.addBook(Book(title = "title4", isbn = "isbn4"))
        author2.addBook(Book(title = "title5", isbn = "isbn5"))
        author2.addBook(Book(title = "title6", isbn = "isbn6"))


        authorRepository.save(author1)
        authorRepository.save(author2)


        println("=====")

        //쿼리 2번 발생
        appService.deleteViaBulkIn()
    }

    @Test
    fun deleteViaDeleteInBatch() {
        val author1 = Author(name = "author1", genre = "love", age = 34)
        author1.addBook(Book(title = "title1", isbn = "isbn1"))
        author1.addBook(Book(title = "title2", isbn = "isbn2"))
        author1.addBook(Book(title = "title3", isbn = "isbn3"))

        authorRepository.save(author1)

        println("=====")

        //쿼리 2번 발생
        appService.deleteViaDeleteInBatch(author1.name)
    }

    @Test
    fun deleteViaHardCodedIdentifiers() {
        val author1 = Author(name = "author1", genre = "love", age = 34)
        author1.addBook(Book(title = "title1", isbn = "isbn1"))
        author1.addBook(Book(title = "title2", isbn = "isbn2"))
        author1.addBook(Book(title = "title3", isbn = "isbn3"))

        authorRepository.save(author1)

        println("=====")

        //쿼리 2번 발생
        appService.deleteViaHardCodedIdentifiers(author1.id!!)
    }


    @Test
    fun deleteViaBulk() {
        val author1 = Author(name = "author1", genre = "love", age = 34)
        author1.addBook(Book(title = "title1", isbn = "isbn1"))
        author1.addBook(Book(title = "title2", isbn = "isbn2"))
        author1.addBook(Book(title = "title3", isbn = "isbn3"))

        val author2 = Author(name = "author2", genre = "love", age = 34)
        author2.addBook(Book(title = "title4", isbn = "isbn4"))
        author2.addBook(Book(title = "title5", isbn = "isbn5"))
        author2.addBook(Book(title = "title6", isbn = "isbn6"))

        authorRepository.save(author1)
        authorRepository.save(author2)


        println("=====")

        //쿼리 2번 발생
        appService.deleteViaBulk(listOf(author1.id!!, author2.id!!))
    }
}