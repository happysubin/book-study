package com.object.chapter_11.part_2;

import com.object.chapter_10.part_1.section_1.Call;
import com.object.chapter_10.part_1.section_1.Money;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPhone {

    private List<Call> calls = new ArrayList<>();


    public Money calculateFee(){
        Money result = Money.ZERO;

        for (Call call : calls) {
            result = result.plus(calculateCallFee(call));
        }

        return result; //요구사항 추가도 훨씬 수월해짐.
    }

    abstract protected Money calculateCallFee(Call call);
    protected Money afterCalculated(Money fee){
        return fee;
    }

}
