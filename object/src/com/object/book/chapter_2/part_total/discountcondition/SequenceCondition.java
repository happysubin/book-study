package com.object.book.chapter_2.part_total.discountcondition;

import com.object.book.chapter_2.part_total.Screening;

public class SequenceCondition  implements DiscountCondition {

    private int sequence;

    public SequenceCondition(int sequence) {
        this.sequence = sequence;
    }

    @Override
    public boolean isSatisfiedBy(Screening screening) {
        return screening.isSequence(sequence);
    }
}
