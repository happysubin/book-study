package com.object.chapter_2.part_total.discountpolicy;

import com.object.chapter_2.part_total.discountcondition.DiscountCondition;
import com.object.chapter_2.part_total.Money;
import com.object.chapter_2.part_total.Screening;

public class AmountDiscountPolicy extends DiscountPolicy {

    private Money discountAmount;

    public AmountDiscountPolicy(Money discountAmount, DiscountCondition... conditions) {
        super(conditions);
        this.discountAmount = discountAmount;
    }

    @Override
    protected Money getDiscountAmount(Screening screening) {
        return discountAmount;
    }
}
