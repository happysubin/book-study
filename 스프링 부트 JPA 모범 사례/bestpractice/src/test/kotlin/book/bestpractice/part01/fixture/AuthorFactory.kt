package book.bestpractice.part01.fixture

import book.bestpractice.part01.chapter01.Author
import book.bestpractice.part01.chapter01.Book

class AuthorFactory {

    companion object {
        fun create(): Author {
            val author = Author(name = "author1", genre = "love", age = 34)
            author.addBook(Book(title = "title1", isbn = "isbn1"))
            author.addBook(Book(title = "title2", isbn = "isbn2"))
            author.addBook(Book(title = "title3", isbn = "isbn3"))
            return author
        }
    }

}