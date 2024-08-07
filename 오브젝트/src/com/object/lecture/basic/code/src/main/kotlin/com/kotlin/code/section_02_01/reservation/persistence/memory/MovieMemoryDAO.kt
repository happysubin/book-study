package com.kotlin.code.section_02_01.reservation.persistence.memory

import com.kotlin.code.section_02_01.reservation.domain.Movie
import com.kotlin.code.section_02_01.reservation.persistence.MovieDAO


class MovieMemoryDAO : InMemoryDAO<Movie>(), MovieDAO {

    override fun selectMovie(movieId: Long): Movie? {
        return findOne {  it.id == movieId }
    }
}