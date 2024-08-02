package com.object.book.chapter_10.part_3;

import com.object.book.chapter_10.part_1.section_1.Call;
import com.object.book.chapter_10.part_1.section_1.Money;

import java.time.Duration;


public class NightlyDiscountPhone extends AbstractPhone{

    private static final int LATE_NIGHT_HOUR = 22;


    private Money nightlyAmount;
    private Money regularAmount;
    private Duration seconds;


    public NightlyDiscountPhone(double taxRate, Money nightlyAmount, Money regularAmount, Duration seconds) {
        super(taxRate);
        this.nightlyAmount = nightlyAmount;
        this.regularAmount = regularAmount;
        this.seconds = seconds;
    }

    @Override
    protected Money calculateCallFee(Call call) {
        if(call.getFrom().getHour() >= LATE_NIGHT_HOUR){
            return nightlyAmount.times(call.getDuration().getSeconds() / seconds.getSeconds());
        }
        return regularAmount.times(call.getDuration().getSeconds() / seconds.getSeconds());
    }
}
