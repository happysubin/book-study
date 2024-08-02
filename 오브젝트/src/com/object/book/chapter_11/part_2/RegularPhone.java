package com.object.book.chapter_11.part_2;

import com.object.book.chapter_10.part_1.section_1.Call;
import com.object.book.chapter_10.part_1.section_1.Money;

import java.time.Duration;


public class RegularPhone extends AbstractPhone {

    private Money amount;
    private Duration seconds;

    public RegularPhone(Money amount, Duration seconds) {
        this.amount = amount;
        this.seconds = seconds;
    }

    @Override
    protected Money calculateCallFee(Call call) {
        return amount.times(call.getDuration().getSeconds() / seconds.getSeconds());
    }

}
