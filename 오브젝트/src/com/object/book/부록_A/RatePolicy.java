package com.object.book.부록_A;

import com.object.book.chapter_11.part_3.Call;
import com.object.book.chapter_11.part_3.Money;

import java.util.List;

public interface RatePolicy {
    Money calculateFee(List<Call> calls);
}
