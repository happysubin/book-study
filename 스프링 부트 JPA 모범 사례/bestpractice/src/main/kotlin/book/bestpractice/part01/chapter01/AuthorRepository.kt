package book.bestpractice.part01.chapter01

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface AuthorRepository: JpaRepository<Author, Long> {

    fun findAuthorByName(name: String): Author

    fun findAuthorByAge(age: Int): List<Author>


    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from Author a where a.id = :id")
    fun deleteByIdentifier(id: Long): Int

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from Author a where a.id in :id")
    fun deleteBulkByIdentifier(id: List<Long>)

    @Transactional
    //@EntityGraph(value = "author-books-graph", type = EntityGraph.EntityGraphType.FETCH)
    @EntityGraph(attributePaths = ["books"], type = EntityGraph.EntityGraphType.FETCH)

    override fun findAll(): MutableList<Author>

    @Transactional
    @EntityGraph(value = "author-books-graph", type = EntityGraph.EntityGraphType.FETCH)
    fun findByAgeLessThanOrderByNameDesc(age: Int): List<Author>

    @Transactional
    @EntityGraph(value = "author-books-graph", type = EntityGraph.EntityGraphType.FETCH)
    @Query("select a from Author a where a.age > 20 and a.age < 40")
    fun fetchAllAgeBetween20And40(): List<Author>


}