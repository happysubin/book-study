package com.object.book.부록_A;

import com.object.book.chapter_11.part_3.Money;

public class Bill {

    private Phone phone;
    private Money fee;

    public Bill(Phone phone, Money fee) {

        if(phone == null){
            throw new IllegalArgumentException();
        }

        if( fee.isLessThan(Money.ZERO)){
            throw new IllegalArgumentException();
        }

        this.phone = phone;
        this.fee = fee;
    }
}
