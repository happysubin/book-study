package com.object.book.chapter_14.part_1;

import com.object.book.chapter_11.part_3.Money;


import java.time.Duration;

public class DurationDiscountRule extends FixedFeePolicy {

    private Duration from;
    private Duration to;

    public DurationDiscountRule(Money amount, Duration seconds, Duration from, Duration to) {
        super(amount, seconds);
        this.from = from;
        this.to = to;
    }

    public Money calculate(Call call){
        if(call.getDuration().compareTo(to) > 0){
            return Money.ZERO;
        }

        if(call.getDuration().compareTo(from) < 0){
            return Money.ZERO;
        }


        Phone phone = new Phone(null);

        phone.call(
                new Call(call.getFrom().plus(from),
                call.getDuration().compareTo(to) > 0 ? call.getFrom().plus(to) : call.getTo())
        );

        return super.calculateFee(phone);

    }
}
