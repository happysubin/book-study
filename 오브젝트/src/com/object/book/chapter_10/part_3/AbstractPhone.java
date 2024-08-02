package com.object.book.chapter_10.part_3;

import com.object.book.chapter_10.part_1.section_1.Call;
import com.object.book.chapter_10.part_1.section_1.Money;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPhone {

    private double taxRate;
    private List<Call> calls = new ArrayList<>();

    public AbstractPhone(double taxRate) {
        this.taxRate = taxRate;
    }

    public Money calculateFee(){
        Money result = Money.ZERO;

        for (Call call : calls) {
            result = result.plus(calculateCallFee(call));
        }

        return result.plus(result.times(taxRate)); //요구사항 추가도 훨씬 수월해짐.
    }

    abstract protected Money calculateCallFee(Call call);

}
