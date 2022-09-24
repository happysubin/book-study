package com.object.chapter_2.part_total;

import com.object.chapter_2.part_total.discountpolicy.DiscountPolicy;

import java.time.Duration;

public class Movie {

    private String title;
    private Duration runningTime;
    private Money fee;
    private DiscountPolicy discountPolicy;

    public Movie(String title, Duration runningTime, Money fee, DiscountPolicy discountPolicy) {
        this.title = title;
        this.runningTime = runningTime;
        this.fee = fee;
        this.discountPolicy = discountPolicy;
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
