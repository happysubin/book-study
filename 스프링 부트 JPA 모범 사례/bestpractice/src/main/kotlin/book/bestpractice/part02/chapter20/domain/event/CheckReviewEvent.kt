package book.bestpractice.part02.chapter20.domain.event

import book.bestpractice.part02.chapter20.domain.BookReview

class CheckReviewEvent(
    val bookReview: BookReview
) {
}