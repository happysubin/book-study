package com.kotlin.code.section_02_01.reservation.persistence.memory

import com.kotlin.code.section_02_01.reservation.domain.DiscountPolicy
import com.kotlin.code.section_02_01.reservation.persistence.DiscountPolicyDAO

class DiscountPolicyMemoryDAO : InMemoryDAO<DiscountPolicy>(), DiscountPolicyDAO {

    override fun selectDiscountPolicy(movieId: Long): DiscountPolicy? {
        return findOne { it.movieId == movieId }
    }
}