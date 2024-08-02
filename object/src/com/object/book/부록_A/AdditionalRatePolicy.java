package com.object.book.부록_A;

import com.object.book.chapter_11.part_3.Call;
import com.object.book.chapter_11.part_3.Money;

import java.util.List;

public abstract class AdditionalRatePolicy implements  RatePolicy{

    private RatePolicy next;

    public AdditionalRatePolicy(RatePolicy next) {
        this.next = next;
    }

    @Override
    public Money calculateFee(List<Call> calls) {
        // 사전 조건
        assert calls != null;

        Money fee =next.calculateFee(calls);
        Money result = afterCalculated(fee);


        //사후 조건
        assert result.isGreaterThanOrEqual(Money.ZERO);

        return result;
    }

    abstract protected Money afterCalculated(Money fee);
}
