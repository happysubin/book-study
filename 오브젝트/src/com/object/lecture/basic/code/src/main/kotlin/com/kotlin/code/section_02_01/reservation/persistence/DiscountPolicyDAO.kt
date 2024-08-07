package com.kotlin.code.section_02_01.reservation.persistence

import com.kotlin.code.section_02_01.reservation.domain.DiscountPolicy


interface DiscountPolicyDAO {
    fun selectDiscountPolicy(movieId: Long): DiscountPolicy?
    fun insert(discountPolicy: DiscountPolicy)
}