package com.object.chapter_5.step_4;

import com.object.chapter_5.step_1.Money;
import com.object.chapter_5.step_3.DiscountCondition;

import java.time.Duration;
import java.util.List;

public class PercentDiscountMovie extends Movie{

    private double percent;

    public PercentDiscountMovie(String title, Duration runningTime, Money fee, List<DiscountCondition> discountConditions, double percent) {
        super(title, runningTime, fee, discountConditions);
        this.percent = percent;
    }

    @Override
    protected Money calculateDiscountAmount() {
        return getFee().times(percent);
    }
}
