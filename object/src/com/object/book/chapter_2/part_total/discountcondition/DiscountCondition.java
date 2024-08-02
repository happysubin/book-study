package com.object.book.chapter_2.part_total.discountcondition;

import com.object.book.chapter_2.part_total.Screening;

public interface DiscountCondition {
    boolean isSatisfiedBy(Screening screening);
}
