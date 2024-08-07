package com.kotlin.code.section_02_01.reservation.persistence.memory

import com.kotlin.code.section_02_01.reservation.domain.Screening
import com.kotlin.code.section_02_01.reservation.persistence.ScreeningDAO
import java.util.function.Predicate


class ScreeningMemoryDAO : InMemoryDAO<Screening>(), ScreeningDAO {
    override fun selectScreening(id: Long): Screening? {
        return findOne{ it.id == id }
    }
}