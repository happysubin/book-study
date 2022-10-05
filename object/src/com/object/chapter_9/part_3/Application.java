package com.object.chapter_9.part_3;

import com.object.chapter_2.part_total.Money;
import com.object.chapter_2.part_total.discountcondition.PeriodCondition;
import com.object.chapter_2.part_total.discountcondition.SequenceCondition;
import com.object.chapter_2.part_total.discountpolicy.AmountDiscountPolicy;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

public class Application {

    public static void main(String[] args) {
        ServiceLocator.provide(new AmountDiscountPolicy(
                Money.wons(800),
                new SequenceCondition(1),
                new SequenceCondition(10),
                new PeriodCondition(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(11, 59)),
                new PeriodCondition(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(20, 59))
        ));

        Movie avatar = new Movie("아바타",
                Duration.ofMinutes(120),
                Money.wons(10000)
        );
    }

}
