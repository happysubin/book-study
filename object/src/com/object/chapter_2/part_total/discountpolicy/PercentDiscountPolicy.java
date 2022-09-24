package com.object.chapter_2.part_total.discountpolicy;

import com.object.chapter_2.part_total.discountcondition.DiscountCondition;
import com.object.chapter_2.part_total.Money;
import com.object.chapter_2.part_total.Screening;

public class PercentDiscountPolicy extends DiscountPolicy {

    private double percent;

    public PercentDiscountPolicy(double percent, DiscountCondition... conditions) {
        super(conditions);
        this.percent = percent;
    }

    @Override
    protected Money getDiscountAmount(Screening screening) {
        return screening.getMovieFee().times(percent);
    }

}
