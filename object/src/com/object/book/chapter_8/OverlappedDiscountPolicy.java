package com.object.book.chapter_8;

import com.object.book.chapter_2.part_total.Money;
import com.object.book.chapter_2.part_total.Screening;
import com.object.book.chapter_2.part_total.discountcondition.DiscountCondition;
import com.object.book.chapter_2.part_total.discountpolicy.DiscountPolicy;

import java.util.ArrayList;
import java.util.List;

/**
 * 두 번째 해결법. 하중복 적용이 가능한 할인정책 구현.
 * 가장 간단한 구현 방법은 Movie가 DiscountPolicy의 인스턴스로 구성된 List를 인스턴스 변수로 갖게함.
 * 하지만 이 방법은 중복 할인 정책을 구현하기 위해 기존의 할인 정책의 협력 방식과는 다른 예외 케이스를 추가하게 만든다.
 *
 * 이 문제 역시 NoneDiscountPolicy와 같은 방법으로 해결한다.
 * 중복 할인 정책을 할인 정책의 한 가지로 간주하는 것이다.
 * 그러면 기존의 Movie와 DiscountPolicy 사이의 협력 방식을 수정하지 않고도 여러개의 할인 정책을 적용할 수 있다.
 * 뭔가 일급 컬렉션의 냄새가 난다.
 *
 */

public class OverlappedDiscountPolicy extends DiscountPolicy {

    private List<DiscountPolicy> discountPolicies = new ArrayList<>();

    public OverlappedDiscountPolicy(List<DiscountPolicy> discountPolicies, DiscountCondition... conditions) {
        super(conditions);
        this.discountPolicies = discountPolicies;
    }

    @Override
    protected Money getDiscountAmount(Screening screening) {
        Money result = Money.ZERO;

        for (DiscountPolicy discountPolicy : discountPolicies) {
            result = result.plus(discountPolicy.calculateDiscountAmount(screening));
        }

        return result;
    }
}
