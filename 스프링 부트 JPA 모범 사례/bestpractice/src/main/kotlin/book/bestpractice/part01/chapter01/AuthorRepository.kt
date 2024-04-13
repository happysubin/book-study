package book.bestpractice.part01.chapter01

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
}