package book.bestpractice.part02.chapter14

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Chapter14Author(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ?= null,
    val age: Int,
    val genre: String,
    val name: String
) {
}