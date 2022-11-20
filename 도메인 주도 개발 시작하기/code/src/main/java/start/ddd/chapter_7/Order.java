package start.ddd.chapter_7;

import start.ddd.chapter_4.section_3.Money;
import start.ddd.chapter_4.section_3.OrderLine;

import java.util.List;

public class Order {

    private List<OrderLine> orderLines;
    private List<Coupon> coupons;
    private Money payments;

    /**
     * 부가적인 자세한 내용은 생략
     */

    public void calculateAmounts(DiscountCalculationService service, MemberGrade grade){
        Money totalAmounts = getTotalAmounts();
        Money money = service.calculateDiscountAmounts(this.orderLines, this.coupons, grade);
        this.payments = money;
    }

    private Money getTotalAmounts() {
        // 로직을 실행
        return null;
    }
}
