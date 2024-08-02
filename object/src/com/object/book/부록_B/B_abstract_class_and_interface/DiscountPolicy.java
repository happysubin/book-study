package com.object.book.부록_B.B_abstract_class_and_interface;


import com.object.book.chapter_2.part_total.Money;
import com.object.book.chapter_2.part_total.Screening;

public interface DiscountPolicy {
    Money calculateDiscountAmount(Screening screening);
}
