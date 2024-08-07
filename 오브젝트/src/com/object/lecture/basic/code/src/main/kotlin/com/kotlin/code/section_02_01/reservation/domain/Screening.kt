package com.kotlin.code.section_02_01.reservation.domain

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime


class Screening(
    val id: Long? = null,
    val movieId: Long,
    val sequence: Int,
    val screeningTime: LocalDateTime
) {


    fun isPlayedIn(dayOfWeek: DayOfWeek, startTime: LocalTime, endTime: LocalTime): Boolean {
        return screeningTime.dayOfWeek == dayOfWeek &&
                (screeningTime.toLocalTime() == startTime || screeningTime.toLocalTime().isAfter(startTime)) &&
                (screeningTime.toLocalTime() == endTime || screeningTime.toLocalTime().isBefore(endTime))
    }
}