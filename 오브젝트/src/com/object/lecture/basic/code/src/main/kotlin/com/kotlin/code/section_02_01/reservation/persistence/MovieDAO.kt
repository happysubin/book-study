package com.kotlin.code.section_02_01.reservation.persistence

import com.kotlin.code.section_02_01.reservation.domain.Movie


interface MovieDAO {
    fun selectMovie(movieId: Long): Movie?
    fun insert(movie: Movie)
}