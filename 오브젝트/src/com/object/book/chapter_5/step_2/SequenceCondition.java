package com.object.book.chapter_5.step_2;

import com.object.book.chapter_5.step_1.Screening;

public class SequenceCondition {

    private int sequence;

    public SequenceCondition(int sequence) {
        this.sequence = sequence;
    }

    public boolean isSatisfiedBy(Screening screening){
        return sequence == screening.getSequence();
    }
}


