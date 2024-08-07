package com.kotlin.code.section_02_01

import com.kotlin.code.section_02_01.reservation.domain.DiscountCondition
import com.kotlin.code.section_02_01.reservation.domain.DiscountPolicy
import com.kotlin.code.section_02_01.reservation.domain.Movie
import com.kotlin.code.section_02_01.reservation.domain.Screening
import com.kotlin.code.section_02_01.reservation.persistence.*
import com.kotlin.code.section_02_01.reservation.persistence.memory.*
import com.kotlin.code.section_02_01.reservation.service.ReservationService
import com.`object`.lecture.basic.section_02_01.generic.Money
import java.time.LocalDateTime
import java.time.LocalTime.of

import java.time.DayOfWeek.MONDAY
import java.time.DayOfWeek.WEDNESDAY


class Main {


    private val screeningDAO: ScreeningDAO = ScreeningMemoryDAO()
    private val movieDAO: MovieDAO = MovieMemoryDAO()
    private val discountPolicyDAO: DiscountPolicyDAO = DiscountPolicyMemoryDAO()
    private val discountConditionDAO: DiscountConditionDAO = DiscountConditionMemoryDAO()
    private val reservationDAO: ReservationDAO = ReservationMemoryDAO()

    val reservationService: ReservationService = ReservationService(
        screeningDAO,
        movieDAO,
        discountPolicyDAO,
        discountConditionDAO,
        reservationDAO
    )

    private fun initializeData(): Screening {
        val movie = Movie(title = "한산", runningTime = 150, fee = Money.wons(10000))
        movieDAO.insert(movie)

        val discountPolicy = DiscountPolicy(
            movieId = movie.id!!,
            policyType = DiscountPolicy.PolicyType.AMOUNT_POLICY,
            amount = Money.wons(1000),
            percent = null
        )
        discountPolicyDAO.insert(discountPolicy)

        discountConditionDAO.insert(DiscountCondition(
            id = null,
            policyId = discountPolicy.id!!,
            conditionType = DiscountCondition.ConditionType.SEQUENCE_CONDITION,
            null,
            null,
            null,
            1)
        )
        discountConditionDAO.insert(DiscountCondition(
            id = null,
            policyId = discountPolicy.id,
            conditionType = DiscountCondition.ConditionType.SEQUENCE_CONDITION,
            null,
            null,
            null,
            10)
        )
        discountConditionDAO.insert(
            DiscountCondition(
                id = null,
                policyId = discountPolicy.id,
                conditionType = DiscountCondition.ConditionType.PERIOD_CONDITION,
                dayOfWeek = MONDAY,
                startTime = of(10, 0),
                endTime = of(12, 0),
                sequence = null
            )
        )
        discountConditionDAO.insert(
            DiscountCondition(
                id = null,
                policyId = discountPolicy.id,
                conditionType = DiscountCondition.ConditionType.PERIOD_CONDITION,
                dayOfWeek = WEDNESDAY,
                startTime = of(18, 0),
                endTime = of(21, 0),
                sequence = null
            )
        )

        val screening = Screening(
            movieId = movie.id,
            sequence = 7,
            screeningTime = LocalDateTime.of(2024, 12, 11, 18, 0)
        )
        screeningDAO.insert(screening)

        return screening
    }

    fun run() {
        val screening = initializeData()

        val reservation = reservationService.reserveScreening(1L, screening.id!!, 2)

        System.out.printf("관객수 : %d, 요금: %s%n", reservation.audienceCount, reservation.fee)
    }
}

fun main() {
    Main().run()
}