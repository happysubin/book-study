package com.kotlin.code.section_02_01.reservation.service

import com.kotlin.code.section_02_01.reservation.domain.*
import com.kotlin.code.section_02_01.reservation.persistence.*
import com.`object`.lecture.basic.section_02_01.generic.Money


class ReservationService (
    private val screeningDAO: ScreeningDAO,
    private val movieDAO: MovieDAO,
    private val discountPolicyDAO: DiscountPolicyDAO,
    private val discountConditionDAO: DiscountConditionDAO,
    private val reservationDAO: ReservationDAO,
) {


    fun reserveScreening(customerId: Long, screeningId: Long, audienceCount: Int): Reservation {
        val screening = screeningDAO.selectScreening(screeningId)
        val movie = movieDAO.selectMovie(screening!!.movieId)
        val policy = discountPolicyDAO.selectDiscountPolicy(movie!!.id!!)
        val conditions = discountConditionDAO.selectDiscountConditions(policy!!.id!!)

        val condition = findDiscountCondition(screening, conditions)
        val fee = if (condition != null) {
            movie.fee.minus(calculateDiscount(policy, movie))
        } else {
            movie.fee
        }

        val reservation = makeReservation(customerId, screeningId, audienceCount, fee)
        reservationDAO.insert(reservation)

        return reservation
    }

    private fun findDiscountCondition(screening: Screening, conditions: List<DiscountCondition>?): DiscountCondition? {
        for (condition in conditions!!) {
            if (condition.isPeriodCondition()) {
                if (screening.isPlayedIn(
                        condition.dayOfWeek!!,
                        condition.startTime!!,
                        condition.endTime!!
                    )
                ) {
                    return condition
                }
            } else {
                if (condition.sequence?.equals(screening.sequence)!!) {
                    return condition
                }
            }
        }

        return null
    }

    private fun calculateDiscount(policy: DiscountPolicy, movie: Movie): Money {
        if (policy.isAmountPolicy()) {
            return policy.amount
        } else if (policy.isPercentPolicy()) {
            return movie.fee.times(policy.percent!!)
        }

        return Money.ZERO
    }

    private fun makeReservation(customerId: Long, screeningId: Long, audienceCount: Int, fee: Money): Reservation {
        return Reservation(null, customerId, screeningId, audienceCount, fee.times(audienceCount.toDouble()))
    }
}
