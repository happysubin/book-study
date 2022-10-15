package com.object.chapter_14.part_1;


import com.object.chapter_11.part_3.Money;

import java.time.Duration;

public class FixedFeePolicy extends BasicRatePolicy {

    private Money amount;
    private Duration seconds;

    public FixedFeePolicy(Money amount, Duration seconds) {
        this.amount = amount;
        this.seconds = seconds;
    }

    @Override
    protected Money calculateCallFee(Call call) {
        return amount.times(call.getDuration().getSeconds() / seconds.getSeconds());
    }
}
