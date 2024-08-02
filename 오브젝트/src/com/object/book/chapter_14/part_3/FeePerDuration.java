package com.object.book.chapter_14.part_3;

import com.object.book.chapter_11.part_3.Money;
import com.object.book.chapter_14.part_1.DateTimeInterval;

import java.time.Duration;

public class FeePerDuration {

    private Money fee;
    private Duration duration;

    public FeePerDuration(Money fee, Duration duration) {
        this.fee = fee;
        this.duration = duration;
    }

    public Money calculate(DateTimeInterval interval){
        return fee.times(Math.ceil((double) interval.duration().toNanos() / duration.toNanos()));
    }
}
