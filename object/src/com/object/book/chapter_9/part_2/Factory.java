package com.object.book.chapter_9.part_2;

import com.object.book.chapter_2.part_total.Money;
import com.object.book.chapter_2.part_total.Movie;
import com.object.book.chapter_2.part_total.discountcondition.PeriodCondition;
import com.object.book.chapter_2.part_total.discountcondition.SequenceCondition;
import com.object.book.chapter_2.part_total.discountpolicy.AmountDiscountPolicy;
import com.object.book.chapter_2.part_total.discountpolicy.PercentDiscountPolicy;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

public class Factory {
    public Movie createAvatarMovie() {
        return  new Movie("아바타",
                Duration.ofMinutes(120),
                Money.wons(10000),
                new AmountDiscountPolicy(
                        Money.wons(800),
                        new SequenceCondition(1),
                        new SequenceCondition(10),
                        new PeriodCondition(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(11, 59)),
                        new PeriodCondition(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(20, 59))
                ));
    }
}
