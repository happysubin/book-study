package com.kotlin.code.section_02_01.reservation.persistence.memory

import com.kotlin.code.section_02_01.reservation.domain.DiscountCondition
import com.kotlin.code.section_02_01.reservation.persistence.DiscountConditionDAO


class DiscountConditionMemoryDAO : InMemoryDAO<DiscountCondition>(), DiscountConditionDAO {

    override fun selectDiscountConditions(policyId: Long): List<DiscountCondition>? {
        return findMany { it.policyId == policyId }
    }
}