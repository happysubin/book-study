package com.object.book.chapter_9.part_3;

import com.object.book.chapter_2.part_total.Money;
import com.object.book.chapter_2.part_total.Screening;
import com.object.book.chapter_2.part_total.discountpolicy.DiscountPolicy;

import java.time.Duration;

public class Movie {

    private String title;
    private Duration runningTime;
    private Money fee;
    private DiscountPolicy discountPolicy;

    public Movie(String title, Duration runningTime, Money fee) {
        this.title = title;
        this.runningTime = runningTime;
        this.fee = fee;
        this.discountPolicy = ServiceLocator.discountPolicy();
    }

    public Movie(String 아바타, Duration runningTime) {
    }

    public Money getFee() {
        return fee;
    }

    public Money calculateMovieFee(Screening screening) { //1인당 예매 요금
        return fee.minus(discountPolicy.calculateDiscountAmount(screening));
    }
}
