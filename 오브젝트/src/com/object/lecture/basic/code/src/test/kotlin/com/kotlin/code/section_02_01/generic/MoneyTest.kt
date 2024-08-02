package com.kotlin.code.section_02_01.generic

import com.`object`.lecture.basic.section_02_01.generic.Money
import org.junit.jupiter.api.Assertions.*
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
}