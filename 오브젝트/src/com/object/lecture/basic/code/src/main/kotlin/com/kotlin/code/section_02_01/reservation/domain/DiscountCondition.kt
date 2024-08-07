package com.kotlin.code.section_02_01.reservation.domain

import java.time.DayOfWeek
import java.time.LocalTime


class DiscountCondition(
    val id: Long? = null,
    val policyId: Long,
    val conditionType: ConditionType,
    val dayOfWeek: DayOfWeek? = null,
    val startTime: LocalTime? = null,
    val endTime: LocalTime? =  null,
    val sequence: Int? = null
) {

    enum class ConditionType {
        PERIOD_CONDITION, SEQUENCE_CONDITION
    }

    fun isPeriodCondition(): Boolean {
        return ConditionType.PERIOD_CONDITION == conditionType
    }

    fun isSequenceCondition(): Boolean {
        return ConditionType.SEQUENCE_CONDITION == conditionType
    }
}