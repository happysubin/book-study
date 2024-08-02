package com.object.book.chapter_11.part_3;

import java.util.ArrayList;
import java.util.List;

public class Phone {

    private RatePolicy ratePolicy;
    private List<Call> calls = new ArrayList<>();

    public Phone(RatePolicy ratePolicy) {
        this.ratePolicy = ratePolicy;
    }

    public void call(Call call){
        calls.add(call);
    }

    public List<Call> getCalls() {
        return calls;
    }

    public Money calculateFee(){
        return ratePolicy.calculateFee(this);
    }
}
