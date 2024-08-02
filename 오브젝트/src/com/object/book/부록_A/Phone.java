package com.object.book.부록_A;

import com.object.book.chapter_11.part_3.Call;

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

    public Bill publishBill(){
        return new Bill(this, ratePolicy.calculateFee(calls));
    }
}
