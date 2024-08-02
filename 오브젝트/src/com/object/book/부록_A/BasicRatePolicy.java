package com.object.book.부록_A;

import com.object.book.chapter_11.part_3.Call;
import com.object.book.chapter_11.part_3.Money;

import java.util.List;

public abstract class BasicRatePolicy implements  RatePolicy{
    @Override
    public Money calculateFee(List<Call> calls) {
        // 사전 조건
        assert calls != null;

        Money result = Money.ZERO;

        for (Call call : calls) {
            result.plus(calculateCallFee(call));
        }

        //사후 조건
        assert result.isGreaterThanOrEqual(Money.ZERO);

        return result;
    }

    protected abstract Money calculateCallFee(Call call);
}
