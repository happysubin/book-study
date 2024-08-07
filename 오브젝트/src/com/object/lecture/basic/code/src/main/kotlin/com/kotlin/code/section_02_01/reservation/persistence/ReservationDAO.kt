package com.kotlin.code.section_02_01.reservation.persistence

import com.kotlin.code.section_02_01.reservation.domain.Reservation

interface ReservationDAO {
    fun insert(reservation: Reservation)
}