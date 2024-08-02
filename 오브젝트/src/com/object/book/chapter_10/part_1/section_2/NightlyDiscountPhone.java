package com.object.book.chapter_10.part_1.section_2;

import com.object.book.chapter_10.part_1.section_1.Call;
import com.object.book.chapter_10.part_1.section_1.Money;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class NightlyDiscountPhone {

    private static final int LATE_NIGHT_HOUR = 22;

    private Money nightlyAmount;
    private Money regularAmount;
    private Duration seconds;
    private List<Call> calls = new ArrayList<>();
    private double taxRate;

    public NightlyDiscountPhone(Money nightlyAmount, Money regularAmount, Duration seconds, double taxRate) {
        this.nightlyAmount = nightlyAmount;
        this.regularAmount = regularAmount;
        this.seconds = seconds;
        this.taxRate = taxRate;
    }

    public void call(Call call){
        calls.add(call);
    }

    public Money calculateFee(){
        Money result = Money.ZERO;

        for (Call call : calls) {
            if(call.getFrom().getHour() >= LATE_NIGHT_HOUR){
                result = result.plus(nightlyAmount.times(call.getDuration().getSeconds() / seconds.getSeconds()));
            }
            else{
                result = result.plus(regularAmount.times(call.getDuration().getSeconds() / seconds.getSeconds()));
            }
        }
        return result.minus(result.times(taxRate));  //만약 여기 minus가 나타난다면 오류 발생!!! 중복 코드는 이런 오류를 놓치게 만든다. 너무 많은 코드를 똑같이 수정하므스
    }
}
