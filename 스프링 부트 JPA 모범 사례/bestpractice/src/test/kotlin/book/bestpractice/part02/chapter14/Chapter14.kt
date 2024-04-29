package book.bestpractice.part02.chapter14

import book.bestpractice.part01.AbstractServiceTest
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

class Chapter14  @Autowired constructor(
    private val bookRepository: Chapter14BookRepository,
    private val authorRepository: Chapter14AuthorRepository,
    private val entityManager: EntityManager

): AbstractServiceTest() {

    /***
     * select 문이 호출되고 INSERT문으로 author_id를 저장
     */
    @Test
    fun useFindById() {
        val author = authorRepository.save(Chapter14Author(age = 34, genre = "adventure", name = "author"))

        println("===========")

        val result = authorRepository.findById(author.id).orElseThrow()
        bookRepository.save(Chapter14Book(isbn = "111", title = "title", author = result))
    }


    //insert문만하나 실행된다고 하는데 계속 select랑 insert만 보인다.
    @Test
    fun useGetOne() {
        val author = authorRepository.save(Chapter14Author(age = 34, genre = "adventure", name = "author"))

        println("===========")

        //getOne이 deprecated 되었다. getReference()를 사용한 프록시 엔티티 조회
        val proxy = authorRepository.getReferenceById(author.id)
        bookRepository.save(Chapter14Book(isbn = "111", title = "title", author = proxy))
    }
}

// deprecated된 걸 보면 findById를 쓰는게 낫지 않을까...