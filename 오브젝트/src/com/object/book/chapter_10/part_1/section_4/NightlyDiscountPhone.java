package com.object.book.chapter_10.part_1.section_4;

import com.object.book.chapter_10.part_1.section_1.Call;
import com.object.book.chapter_10.part_1.section_1.Money;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class NightlyDiscountPhone extends Phone{

    private static final int LATE_NIGHT_HOUR = 22;

    private Money nightlyAmount;


    public NightlyDiscountPhone(Money regularAmount, Duration seconds, Money nightlyAmount) {
        super(regularAmount, seconds);
        this.nightlyAmount = nightlyAmount;
    }

    @Override
    public Money calculateFee(){
        Money result = super.calculateFee(); // 이 부분은 뭐지? 라는 질문이 생길 수 있음.
        Money nightlyFee = Money.ZERO;

        for (Call call : getCalls()) {
            if(call.getFrom().getHour() >= LATE_NIGHT_HOUR){
                nightlyFee = nightlyFee.plus(getAmount().minus(nightlyAmount).times(call.getDuration().getSeconds() / getSeconds().getSeconds()));
            }
        }
        return result.minus(nightlyFee); // 이 부분은 뭐지?? 라는 질문이 생길 수 있음.
    }
}
