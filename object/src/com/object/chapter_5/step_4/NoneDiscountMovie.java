package com.object.chapter_5.step_4;

import com.object.chapter_5.step_1.Money;
import com.object.chapter_5.step_3.DiscountCondition;

import java.time.Duration;
import java.util.List;

public class NoneDiscountMovie extends Movie{

    public NoneDiscountMovie(String title, Duration runningTime, Money fee, List<DiscountCondition> discountConditions) {
        super(title, runningTime, fee, discountConditions);
    }

    @Override
    protected Money calculateDiscountAmount() {
        return Money.ZERO;
    }
}
