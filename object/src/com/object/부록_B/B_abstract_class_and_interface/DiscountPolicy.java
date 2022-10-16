package com.object.부록_B.B_abstract_class_and_interface;


import com.object.chapter_2.part_total.Money;
import com.object.chapter_2.part_total.Screening;

public interface DiscountPolicy {
    Money calculateDiscountAmount(Screening screening);
}
