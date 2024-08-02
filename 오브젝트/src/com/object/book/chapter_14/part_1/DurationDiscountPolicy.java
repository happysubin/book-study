package com.object.book.chapter_14.part_1;

import com.object.book.chapter_11.part_3.Money;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DurationDiscountPolicy extends BasicRatePolicy{

    private List<DurationDiscountRule> rules = new ArrayList<>();

    public DurationDiscountPolicy(List<DurationDiscountRule> rules) {
        this.rules = rules;
    }

    @Override
    protected Money calculateCallFee(Call call) {
        Money result = Money.ZERO;
        for (DurationDiscountRule rule : rules) {
            result.plus(rule.calculate(call));
        }
        return result;
    }
}
