package com.object.chapter_8;

import com.object.chapter_2.part_total.Money;
import com.object.chapter_2.part_total.Screening;
import com.object.chapter_2.part_total.discountpolicy.DiscountPolicy;

import java.time.Duration;

public class Movie {

    private String title;
    private Duration runningTime;
    private Money fee;
    private DiscountPolicy discountPolicy;

    public Movie(String title, Duration runningTime, Money fee) {
        this(title, runningTime, fee, null);
    }

    public Movie(String title, Duration runningTime, Money fee, DiscountPolicy discountPolicy) {
        this.title = title;
        this.runningTime = runningTime;
        this.fee = fee;
        this.discountPolicy = discountPolicy;
    }

    public Money calculateMovieFee(Screening screening){
        if(discountPolicy == null){ //null 여부를 체크. 여기에 예외 케이스가 추가된 것임. 어떤 경우든 코드를 수정하면 버그 발생률 상승 -> 다른 클래스를 적용시키자.
            return fee;
        }

        return fee.minus(discountPolicy.calculateDiscountAmount(screening));
    }
}
