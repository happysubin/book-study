package com.kotlin.code.section_02_01.reservation.persistence.memory

import com.kotlin.code.section_02_01.reservation.domain.Reservation
import com.kotlin.code.section_02_01.reservation.persistence.ReservationDAO


class ReservationMemoryDAO : InMemoryDAO<Reservation>(), ReservationDAO {
}