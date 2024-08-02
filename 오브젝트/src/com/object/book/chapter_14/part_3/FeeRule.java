package com.object.book.chapter_14.part_3;

import com.object.book.chapter_11.part_3.Money;
import com.object.book.chapter_14.part_1.Call;

/**
 * FeeRule은 규칙을 구현하는 클래스이며, 단위요금은 FeeRule의 인스턴스 변수인 feePerDuration에 저장돼 있다.
 * FeeCondition은 적용 조건을 구현하는 인터페이스이며 변하는 부분을 캡슐화한 추상화다.
 * FeeRule이 FeeConditiond을 합성 관계로 연결하고 있다.
 *
 *
 * FeeRule은 하나의 Call에 대해 요금을 계산하는 책임을 수행한다.
 * 현재 FeeRule은 단위 시간당 요금인 feePerDuration과 요금을 적용할 조건을 판단하는 적용 조건인 FeeCondtion의 인스턴스를 알고 있다.
 *
 * 하나의 Call 요금을 계산하기 위해서는 하나의 전체 통화 시간을 각 규칙의 적용조건을 만족하는 구간들로 나누는 것이다.
 * 다른 하나는 이렇게 분리된 통화 구간에 단위요금을 적용해서 요금을 계산하는 것이다.
 *
 * 전체 통화 시간을 각 규칙의 적용조건을 만족하는 구간들로 나누는 첫 번째 작업은 적용 조건을 가장 잘 아는 FeeCondition에게 할당하는 것이 적절한다.
 * 분리된 통화 구간에 단위 요금을 적용해서 요금을 계산하는 두 번째 작업은 요금기준의 정보 전문가인 FeeRule이 담당하는 것이 적절하다.
 *
 *
 */

public class FeeRule {

    private FeeCondtion feeCondtion;
    private FeePerDuration feePerDuration;


    public FeeRule(FeeCondtion feeCondtion, FeePerDuration feePerDuration) {
        this.feeCondtion = feeCondtion;
        this.feePerDuration = feePerDuration;
    }

    public Money calculateFee(Call call){
        return feeCondtion.findTimeIntervals(call)
                .stream()
                .map(each -> feePerDuration.calculate(each))
                .reduce(Money.ZERO, (first, second) -> first.plus(second));
    }
}
