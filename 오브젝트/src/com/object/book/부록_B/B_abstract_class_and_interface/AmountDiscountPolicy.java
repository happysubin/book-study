package com.object.book.부록_B.B_abstract_class_and_interface;

import com.object.book.chapter_2.part_total.Money;
import com.object.book.chapter_2.part_total.Screening;

public class AmountDiscountPolicy extends DefaultDiscountPolicy{
    @Override
    protected Money getDiscountAmount(Screening screening) {
        return null;
    }
}
