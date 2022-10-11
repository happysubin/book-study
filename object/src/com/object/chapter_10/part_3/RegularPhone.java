package com.object.chapter_10.part_3;

import com.object.chapter_10.part_1.section_1.Call;
import com.object.chapter_10.part_1.section_1.Money;

import java.time.Duration;


public class RegularPhone extends AbstractPhone{

    private Money amount;
    private Duration seconds;

    public RegularPhone(double taxRate, Money amount, Duration seconds) {
        super(taxRate);
        this.amount = amount;
        this.seconds = seconds;
    }

    @Override
    protected Money calculateCallFee(Call call) {
        return amount.times(call.getDuration().getSeconds() / seconds.getSeconds());
    }
}
