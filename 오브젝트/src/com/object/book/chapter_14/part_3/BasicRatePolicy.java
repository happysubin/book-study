package com.object.book.chapter_14.part_3;

import com.object.book.chapter_11.part_3.Money;
import com.object.book.chapter_14.part_1.Call;
import com.object.book.chapter_14.part_1.Phone;
import com.object.book.chapter_14.part_1.RatePolicy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasicRatePolicy implements RatePolicy {

    private List<FeeRule> feeRules = new ArrayList<>();


    public BasicRatePolicy(FeeRule... feeRules) {
        this.feeRules = Arrays.asList(feeRules);
    }

    @Override
    public Money calculateFee(Phone phone) {
        return phone.getCalls()
                .stream()
                .map(call -> calculate(call))
                .reduce(Money.ZERO, (first, second) -> first.plus(second));
    }

    private Money calculate(Call call){
        return feeRules
                .stream()
                .map(rule -> rule.calculateFee(call))
                .reduce(Money.ZERO, (first, second) -> first.plus(second));
    }
}
