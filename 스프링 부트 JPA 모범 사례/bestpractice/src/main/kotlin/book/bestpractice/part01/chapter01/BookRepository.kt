package book.bestpractice.part01.chapter01

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface BookRepository: JpaRepository<Book, Long> {


    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from Book b where b.author.id = :id")
    fun deleteByAuthorIdentifier(id: Long): Int

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from Book b where b.author in :authors")
    fun deleteBulkByAuthors(authors: List<Author>)

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from Book b where b.author.id in :id")
    fun deleteBulkByAuthorIdentifier(id: List<Long>)

}