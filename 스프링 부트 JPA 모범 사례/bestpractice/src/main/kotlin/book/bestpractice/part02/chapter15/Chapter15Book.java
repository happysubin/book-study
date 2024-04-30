package book.bestpractice.part02.chapter15;

import book.bestpractice.part01.chapter01.Author;
import jakarta.persistence.*;

import java.util.Optional;

@Entity
public class Chapter15Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String isbn;

    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

    public Long getId() {
        return id;
    }

    public Optional<String> getTitle() {
        return Optional.ofNullable(title);
    }

    public Optional<String> getIsbn() {
        return Optional.ofNullable(isbn);
    }

    public Optional<Author> getAuthor() {
        return Optional.ofNullable(author);
    }
}
