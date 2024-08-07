package com.kotlin.code.section_02_01.reservation.domain

import com.`object`.lecture.basic.section_02_01.generic.Money

class Movie (
    val id: Long? = null,
    val title: String,
    val runningTime: Int,
    val fee: Money
)