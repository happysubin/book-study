package com.kotlin.code.section_02_01.reservation.domain

import com.`object`.lecture.basic.section_02_01.generic.Money


class DiscountPolicy (
    val id: Long? =  null,
    val movieId: Long,
    val policyType: PolicyType,
    val amount: Money,
    val percent: Double? = null
) {

    enum class PolicyType {
        PERCENT_POLICY, AMOUNT_POLICY
    }

    fun isAmountPolicy(): Boolean {
        return PolicyType.AMOUNT_POLICY == policyType
    }

    fun isPercentPolicy(): Boolean {
        return PolicyType.PERCENT_POLICY == policyType
    }

}