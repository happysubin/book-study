package book.bestpractice.part02.chapter16

import org.springframework.stereotype.Service

@Service
class Chapter16AuthorService(
    private val authorRepository: Chapter16AuthorRepository
) {

    //새로운 Author를 만들고 데이터베이스에 저장하고, 이를 Write-Through 전략을 통해 2차 캐시에 보관한다.
    fun newAuthor() {
        val author = Chapter16Author(name = "test", genre = "awesome", age = 33)
        authorRepository.save(author)
    }

    //데이터베이스에 접근하지 않고 2차 캐시에서 생성된 Author를 가져온다.
    fun fetchAuthor() {
        val author = authorRepository.findById(1L).orElseThrow()
        println(author)
    }

    //이 작업은 무시된다.
    fun updateAuthor() {
        val author = authorRepository.findById(1L).orElseThrow()
        author.age = 25
    }

    //2차 캐시에서 엔티티를 가져와 두 위치(캐시 및 데이터베이스)에서 삭제한다.
    fun deleteAuthor() {
        authorRepository.deleteById(1L)
    }
}