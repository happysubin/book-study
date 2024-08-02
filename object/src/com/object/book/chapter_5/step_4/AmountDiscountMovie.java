package com.object.book.chapter_5.step_4;

import com.object.book.chapter_5.step_1.Money;
import com.object.book.chapter_5.step_1.Screening;
import com.object.book.chapter_5.step_3.DiscountCondition;

import java.time.Duration;
import java.util.List;

public class AmountDiscountMovie extends Movie{

    private Money discountAmount;

    public AmountDiscountMovie(String title, Duration runningTime, Money fee, List<DiscountCondition> discountConditions, Money discountAmount) {
        super(title, runningTime, fee, discountConditions);
        this.discountAmount = discountAmount;
    }

    @Override
    protected Money calculateDiscountAmount() {
        return discountAmount;
    }
}
