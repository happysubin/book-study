package com.object.부록_A;

import com.object.chapter_11.part_3.Call;
import com.object.chapter_11.part_3.Money;

import java.util.List;

public interface RatePolicy {
    Money calculateFee(List<Call> calls);
}
