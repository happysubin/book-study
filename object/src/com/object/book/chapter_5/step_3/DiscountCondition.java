package com.object.book.chapter_5.step_3;

import com.object.book.chapter_5.step_1.Screening;

public interface DiscountCondition {
    boolean isSatisfiedBy(Screening screening);
}
