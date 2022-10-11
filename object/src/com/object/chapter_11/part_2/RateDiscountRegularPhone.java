package com.object.chapter_11.part_2;

import com.object.chapter_10.part_1.section_1.Call;
import com.object.chapter_10.part_1.section_1.Money;

import java.time.Duration;


public class RateDiscountRegularPhone extends RegularPhone {

    private Money discountAmount;

    public RateDiscountRegularPhone(Money amount, Duration seconds, Money discountAmount) {
        super(amount, seconds);
        this.discountAmount = discountAmount;
    }


    @Override
    protected Money afterCalculated(Money fee) {
        return fee.minus(discountAmount);
    }
}
