package com.object.chapter_14.part_1;


import com.object.chapter_11.part_3.Money;


import java.util.ArrayList;
import java.util.List;

/**
 * 기본 정책
 */

public abstract class BasicRatePolicy implements RatePolicy {

    private List<Call> calls = new ArrayList<>();

    @Override
    public Money calculateFee(Phone phone) {
        Money result = Money.ZERO;

        for (Call call : calls) {
            result.plus(calculateCallFee(call));
        }
        return result;
    }

    protected abstract Money calculateCallFee(Call call);
}
