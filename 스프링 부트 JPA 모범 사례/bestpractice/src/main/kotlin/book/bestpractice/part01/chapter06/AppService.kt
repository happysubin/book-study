package book.bestpractice.part01.chapter06

import book.bestpractice.part01.chapter01.AuthorRepository
import book.bestpractice.part01.chapter01.BookRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class AppService(
    val authorRepository: AuthorRepository,
    val bookRepository: BookRepository
) {

    @Transactional
    fun deleteViaCascadeRemove(name: String) {
        val author = authorRepository.findAuthorByName(name)
        authorRepository.delete(author)
    }

    @Transactional
    fun deleteViaOrphanRemoval(name: String) {
        val author = authorRepository.findAuthorByName(name)
        author.removeBooks()
        authorRepository.delete(author)
    }

    /**
     * 하나의 저자만 영속성  컨텍스트에 로드된 경우
     */
    @Transactional
    fun deleteViaIdentifiers(name: String) {
        val author = authorRepository.findAuthorByName(name)
        bookRepository.deleteByAuthorIdentifier(author.id!!)
        authorRepository.deleteByIdentifier(author.id)
    }

    /**
     * 여러 저자가 영속성 콘텍스트에 로드된 경우
     */
    @Transactional
    fun deleteViaBulkIn() {
        val authors = authorRepository.findAuthorByAge(34)
        bookRepository.deleteBulkByAuthors(authors)
        authorRepository.deleteAllInBatch(authors)
    }

    /**
     * 한 저자와 관련 도서들이 영속성 컨텍스트에 로드된 경우
     */

    @Transactional
    fun deleteViaDeleteInBatch(name: String) {
        val author = authorRepository.findAuthorByName(name)
        bookRepository.deleteAllInBatch(author.books)
        authorRepository.deleteAllInBatch(listOf(author))

        //이제 author.name을 수정하면 오류 발생
    }

    /**
     * 삭제해야할 저자와 도서가 영속성 컨텍스트에 로드되지 않은 경우 삭제
     */
    @Transactional
    fun deleteViaHardCodedIdentifiers(id: Long) {
        bookRepository.deleteByAuthorIdentifier(id)
        authorRepository.deleteByIdentifier(id)
    }

    /**
     * 삭제해야할 저자와 도서가 용속성 컨텍스트에 로드되지 않은 경우 삭제
     */
    @Transactional
    fun deleteViaBulk(id: List<Long>) {
        bookRepository.deleteBulkByAuthorIdentifier(id)
        authorRepository.deleteBulkByIdentifier(id)
    }
}