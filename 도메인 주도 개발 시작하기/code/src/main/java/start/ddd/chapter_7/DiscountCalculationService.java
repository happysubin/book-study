package start.ddd.chapter_7;

import start.ddd.chapter_4.section_3.Money;
import start.ddd.chapter_4.section_3.OrderLine;

import java.util.List;

public class DiscountCalculationService {

    public Money calculateDiscountAmounts(List<OrderLine> orderLineList, List<Coupon> coupons, MemberGrade grade){

        /**
         * 애그리거트 OrderLine, coupons, grade를 주입 받아 도메인 로직을 도메인 서비스에서 진행
         */

        return null;
    }
}
