package com.object.chapter_5.step_3;

import com.object.chapter_5.step_1.Screening;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class SequenceDiscountCondition implements DiscountCondition {

    private int sequence;

    public SequenceDiscountCondition(int sequence) {
        this.sequence = sequence;
    }

    @Override
    public boolean isSatisfiedBy(Screening screening) {
        return sequence == screening.getSequence();
    }
}

