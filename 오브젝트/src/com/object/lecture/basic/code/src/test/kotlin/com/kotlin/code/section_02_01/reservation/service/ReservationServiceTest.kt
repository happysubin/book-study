package com.kotlin.code.section_02_01.reservation.service

import com.kotlin.code.section_02_01.reservation.domain.DiscountCondition
import com.kotlin.code.section_02_01.reservation.domain.DiscountCondition.ConditionType.*
import com.kotlin.code.section_02_01.reservation.domain.DiscountPolicy
import com.kotlin.code.section_02_01.reservation.domain.DiscountPolicy.*
import com.kotlin.code.section_02_01.reservation.domain.DiscountPolicy.PolicyType.*
import com.kotlin.code.section_02_01.reservation.domain.Movie
import com.kotlin.code.section_02_01.reservation.domain.Screening
import com.kotlin.code.section_02_01.reservation.persistence.*
import com.`object`.lecture.basic.section_02_01.generic.Money
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.time.DayOfWeek.MONDAY
import java.time.DayOfWeek.WEDNESDAY
import java.time.LocalDateTime
import java.time.LocalTime


@ExtendWith(MockitoExtension::class)
class ReservationServiceTest {

    @InjectMocks
    private val reservationService: ReservationService? = null

    @Mock
    private val screeningDAO: ScreeningDAO? = null

    @Mock
    private val movieDAO: MovieDAO? = null

    @Mock
    private val discountPolicyDAO: DiscountPolicyDAO? = null

    @Mock
    private val discountConditionDAO: DiscountConditionDAO? = null

    @Mock
    private val reservationDAO: ReservationDAO? = null

    @Test
    fun 금액할인정책_계산() {
        // given
        val customerId = 1L
        val screeningId = 1L
        val movieId = 1L
        val policyId = 1L

        Mockito.`when`(screeningDAO!!.selectScreening(screeningId))
            .thenReturn(Screening(screeningId, movieId, 1, LocalDateTime.of(2024, 12, 11, 18, 0)))

        Mockito.`when`(movieDAO!!.selectMovie(movieId))
            .thenReturn(Movie(movieId, "한신", 120, Money.wons(10000)))

        Mockito.`when`<DiscountPolicy?>(discountPolicyDAO!!.selectDiscountPolicy(movieId))
            .thenReturn(DiscountPolicy(policyId, movieId,
                AMOUNT_POLICY, Money.wons(1000), null))

        Mockito.`when`<List<DiscountCondition>?>(discountConditionDAO!!.selectDiscountConditions(policyId))
            .thenReturn(
                listOf(
                    DiscountCondition(1L, policyId,
                        SEQUENCE_CONDITION, null, null, null, 1),
                    DiscountCondition(2L, policyId,
                        SEQUENCE_CONDITION, null, null, null, 10),
                    DiscountCondition(
                        3L,
                        policyId,
                        PERIOD_CONDITION,
                        MONDAY,
                        LocalTime.of(10, 12),
                        LocalTime.of(12, 0),
                        null
                    ),
                    DiscountCondition(
                        4L,
                        policyId,
                        PERIOD_CONDITION,
                        WEDNESDAY,
                        LocalTime.of(18, 0),
                        LocalTime.of(21, 0),
                        null
                    )
                )
            )

        // when
        val reservation = reservationService!!.reserveScreening(customerId, screeningId, 2)

        // then
        Assertions.assertEquals(reservation.fee, Money.wons(18000.0))
    }

    @Test
    fun 비율할인정책_계산() {
        // given
        val customerId = 1L
        val screeningId = 1L
        val movieId = 1L
        val policyId = 1L

        Mockito.`when`(screeningDAO!!.selectScreening(screeningId))
            .thenReturn(Screening(screeningId, movieId, 1, LocalDateTime.of(2024, 12, 11, 18, 0)))

        Mockito.`when`(movieDAO!!.selectMovie(movieId))
            .thenReturn(Movie(movieId, "한신", 120, Money.wons(10000)))

        Mockito.`when`<DiscountPolicy?>(discountPolicyDAO!!.selectDiscountPolicy(movieId))
            .thenReturn(DiscountPolicy(policyId, movieId, PERCENT_POLICY, Money.wons(0), 0.1))

        Mockito.`when`(discountConditionDAO!!.selectDiscountConditions(policyId))
            .thenReturn(
                listOf(
                    DiscountCondition(1L, policyId, SEQUENCE_CONDITION, null, null, null, 1),
                    DiscountCondition(2L, policyId, SEQUENCE_CONDITION, null, null, null, 10),
                    DiscountCondition(
                        3L,
                        policyId,
                        PERIOD_CONDITION,
                        MONDAY,
                        LocalTime.of(10, 12),
                        LocalTime.of(12, 0),
                        null
                    ),
                    DiscountCondition(
                        4L,
                        policyId,
                        PERIOD_CONDITION,
                        WEDNESDAY,
                        LocalTime.of(18, 0),
                        LocalTime.of(21, 0),
                        null
                    )
                )
            )

        // when
        val reservation = reservationService!!.reserveScreening(customerId, screeningId, 2)

        // then
        Assertions.assertSame(reservation.fee, Money.wons(18000.0))
    }
}
