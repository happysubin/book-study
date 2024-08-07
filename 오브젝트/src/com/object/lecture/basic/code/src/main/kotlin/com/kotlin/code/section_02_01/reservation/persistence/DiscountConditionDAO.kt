package com.kotlin.code.section_02_01.reservation.persistence

import com.kotlin.code.section_02_01.reservation.domain.DiscountCondition


interface DiscountConditionDAO {
    fun selectDiscountConditions(policyId: Long): List<DiscountCondition>?
    fun insert(discountCondition: DiscountCondition)
}