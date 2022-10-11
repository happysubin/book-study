package com.object.chapter_11.part_2;

import com.object.chapter_10.part_1.section_1.Call;
import com.object.chapter_10.part_1.section_1.Money;

import java.time.Duration;


public class TaxableRegularPhone extends RegularPhone{

    private double taxRate;

    public TaxableRegularPhone(Money amount, Duration seconds, double taxRate) {
        super(amount, seconds);
        this.taxRate = taxRate;
    }

    @Override
    protected Money afterCalculated(Money fee) {
        return fee.plus(fee.times(taxRate));
    }
}
