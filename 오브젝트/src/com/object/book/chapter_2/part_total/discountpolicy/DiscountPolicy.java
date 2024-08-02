package com.object.book.chapter_2.part_total.discountpolicy;

import com.object.book.chapter_2.part_total.discountcondition.DiscountCondition;
import com.object.book.chapter_2.part_total.Money;
import com.object.book.chapter_2.part_total.Screening;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DiscountPolicy {

    private List<DiscountCondition> conditions = new ArrayList<>();

    public DiscountPolicy(DiscountCondition ... conditions) {
        this.conditions = Arrays.asList(conditions);
    }

    public Money calculateDiscountAmount(Screening screening) {
        for (DiscountCondition each : conditions) {
            if(each.isSatisfiedBy(screening)){
                return getDiscountAmount(screening);
            }
        }
        return Money.ZERO;
    }

    abstract protected Money getDiscountAmount(Screening screening);
}

/**
 * 템플릿 메서드 패턴
 */