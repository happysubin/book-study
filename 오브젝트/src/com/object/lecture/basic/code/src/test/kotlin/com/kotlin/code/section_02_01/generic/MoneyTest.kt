package com.kotlin.code.section_02_01.generic

import com.`object`.lecture.basic.section_02_01.generic.Money
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test


class MoneyTest {


    @Test
    fun plus() {
        val won1000 = Money.wons(1000)
        val won2000 = Money.wons(2000)
        val won3000 = won1000.plus(won2000)


        assertEquals(Money.wons(3000), won3000);
        assertEquals(Money.wons(1000), won1000);
        assertEquals(Money.wons(2000), won2000);
    }

    @Test
    fun minus() {
        val won3000 = Money.wons(3000)
        val won2000 = Money.wons(2000)
        val won1000 = won3000.minus(won2000)

        assertEquals(Money.wons(1000), won1000)
        assertEquals(Money.wons(3000), won3000)
        assertEquals(Money.wons(2000), won2000)
    }

    @Test
    fun times() {
        val won1000 = Money.wons(1000)
        val won2000 = won1000.times(2.0)

        assertEquals(Money.wons(2000.0), won2000)
        assertEquals(Money.wons(1000), won1000)
    }

    @Test
    fun divideBy() {
        val won2000 = Money.wons(2000)
        val won1000 = won2000.divide(2.0)

        assertEquals(Money.wons(1000), won1000)
        assertEquals(Money.wons(2000), won2000)
    }

    @Test
    fun isLessThan() {
        val won1000 = Money.wons(1000)
        val won2000 = Money.wons(2000)

        assertTrue(won1000.isLessThan(won2000))
    }

    @Test
    fun isGreaterThanOrEqual() {
        val won1000 = Money.wons(1000)
        val won2000 = Money.wons(2000)

        assertTrue(won2000.isGreaterThanOrEqual(won2000))
    }
}