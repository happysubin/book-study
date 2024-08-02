package com.object.book.부록_B.B_abstract_class_and_interface;

import com.object.book.chapter_2.part_total.Money;
import com.object.book.chapter_2.part_total.Screening;
import com.object.book.chapter_2.part_total.discountcondition.DiscountCondition;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DefaultDiscountPolicy implements DiscountPolicy{

    private List<DiscountCondition> conditions = new ArrayList<>();

    public DefaultDiscountPolicy(DiscountCondition... discountConditions) {
        this.conditions = Arrays.asList(discountConditions);
    }

    @Override
    public Money calculateDiscountAmount(Screening screening) {
        for (DiscountCondition each : conditions) {
            if(each.isSatisfiedBy(screening)){
                return getDiscountAmount(screening);
            }
        }

        return screening.getMovieFee();

    }

    abstract protected Money getDiscountAmount(Screening screening);
}
