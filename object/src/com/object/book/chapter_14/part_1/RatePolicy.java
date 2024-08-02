package com.object.book.chapter_14.part_1;


import com.object.book.chapter_11.part_3.Money;


public interface RatePolicy {
    Money calculateFee(Phone phone);
}
