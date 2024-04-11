package book.bestpractice.part01.chapter01

import book.bestpractice.part01.chapter01.AuthorRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class AppService(
    val authorRepository: AuthorRepository,
) {

    @Transactional
    fun deleteViaCascadeRemove(name: String) {
        val author = authorRepository.findAuthorByName(name)
        author
    }
}