package com.object.book.chapter_5.step_4;

import com.object.book.chapter_5.step_1.Money;
import com.object.book.chapter_5.step_1.MovieType;
import com.object.book.chapter_5.step_1.Screening;
import com.object.book.chapter_5.step_3.DiscountCondition;

import java.time.Duration;
import java.util.List;

public abstract class Movie {

    private String title;
    private Duration runningTime;
    private Money fee;
    private List<DiscountCondition> discountConditions;

    public Movie(String title, Duration runningTime, Money fee, List<DiscountCondition> discountConditions) {
        this.title = title;
        this.runningTime = runningTime;
        this.fee = fee;
        this.discountConditions = discountConditions;
    }

    public Money calculateMovieFee(Screening screening) {
        if (isDiscountable(screening)) {
            return fee.minus(calculateDiscountAmount());
        }
        return fee;
    }

    private boolean isDiscountable(Screening screening) {
        return discountConditions.stream()
                .anyMatch(condition -> condition.isSatisfiedBy(screening));
    }

    public Money getFee() {
        return fee;
    }

    abstract protected Money calculateDiscountAmount();
}
