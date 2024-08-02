package com.object.book.chapter_10.part_1.section_1;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * 심야 할인 요금제라는 요구사항이 추가됨.
 * 밤 10시 이후의 통화에 대해 요금을 할인해줌. 이제 일반 요금제와 심야 할인 요금제가 존재.
 * Phone 코드를 복사해서 클래스를 작성
 *
 */
public class NightlyDiscountPhone {

    private static final int LATE_NIGHT_HOUR = 22;

    private Money nightlyAmount;
    private Money regularAmount;
    private Duration seconds;
    private List<Call> calls = new ArrayList<>();

    public NightlyDiscountPhone(Money nightlyAmount, Money regularAmount, Duration seconds) {
        this.nightlyAmount = nightlyAmount;
        this.regularAmount = regularAmount;
        this.seconds = seconds;
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
        return result;
    }
}
