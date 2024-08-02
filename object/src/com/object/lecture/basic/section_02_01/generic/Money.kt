package com.`object`.lecture.basic.section_02_01.generic

import java.math.BigDecimal
import java.util.function.Function

data class Money(val amount: BigDecimal) {

    companion object {

        val ZERO = Money.wons(0)

        fun wons(amount: Long) = Money(BigDecimal.valueOf(amount))

        fun wons(amount: Double) = Money(BigDecimal.valueOf(amount))

        fun <T> sum(bags: Collection<T>, monetary: Function<T, Money>): Money {
            return bags.map { monetary.apply(it) }.fold(ZERO) { acc, money -> acc.plus(money) } //fold를 사용해 명시적으로 초깃값 지정
        }
    }

    fun plus(amount: Money): Money {
        return Money(this.amount.add(amount.amount))
    }

    fun minus(amount: Money): Money {
        return Money(this.amount.subtract(amount.amount))
    }

    fun times(percent: Double): Money {
        return Money(amount.multiply(BigDecimal.valueOf(percent)))
    }

    fun divide(divisor: Double): Money {
        return Money(amount.divide(BigDecimal.valueOf(divisor)))
    }

    fun isLessThan(other: Money): Boolean {
        return amount.compareTo(other.amount) < 0
    }

    fun isGreaterThanOrEqual(other: Money): Boolean {
        return amount.compareTo(other.amount) >= 0
    }

//    fun getAmount(): BigDecimal {
//        return amount
//    }

    fun longValue(): Long {
        return amount.toLong()
    }

    fun doubleValue(): Double {
        return amount.toDouble()
    }
}