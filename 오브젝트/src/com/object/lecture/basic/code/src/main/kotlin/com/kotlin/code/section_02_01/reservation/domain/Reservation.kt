package com.kotlin.code.section_02_01.reservation.domain

import com.`object`.lecture.basic.section_02_01.generic.Money


class Reservation (
    val id: Long?,
    val customerId: Long,
    val screeningId: Long,
    val audienceCount: Int,
    val fee: Money
)