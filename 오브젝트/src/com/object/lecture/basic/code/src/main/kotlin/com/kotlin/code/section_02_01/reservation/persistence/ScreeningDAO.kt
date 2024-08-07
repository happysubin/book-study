package com.kotlin.code.section_02_01.reservation.persistence

import com.kotlin.code.section_02_01.reservation.domain.Screening

interface ScreeningDAO {
    fun selectScreening(screeningId: Long): Screening?

    fun insert(screening: Screening)
}