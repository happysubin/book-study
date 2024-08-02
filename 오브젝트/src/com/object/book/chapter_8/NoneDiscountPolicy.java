package com.object.book.chapter_8;

import com.object.book.chapter_2.part_total.Money;
import com.object.book.chapter_2.part_total.Screening;
import com.object.book.chapter_2.part_total.discountpolicy.DiscountPolicy;

/**
 * 첫 번째 요구 사항의 해결법. 할인 정책 적용 X 요구사항 (예외 처리 코드를 추가했는데 이를 해결하기 위한 방법)
 * 해당 구체 클래스를 사용해 할인 적용이 존재하지 않을 때를 대체함. 이렇게 하면 내부 코드를 수정할 필요가 없음.
 * 간단하게 NoneDiscountPolicy의 인스턴스를 Movie 생성자에 전달하면 문제가 해결됨.
 *
 *
 */

public class NoneDiscountPolicy extends DiscountPolicy {
    @Override
    protected Money getDiscountAmount(Screening screening) {
        return Money.ZERO;
    }
}
