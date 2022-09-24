package com.object.chapter_2.part_total.discountcondition;

import com.object.chapter_2.part_total.Screening;

public interface DiscountCondition {
    boolean isSatisfiedBy(Screening screening);
}
