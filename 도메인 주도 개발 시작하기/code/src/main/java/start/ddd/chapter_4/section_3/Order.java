package start.ddd.chapter_4.section_3;

import javax.persistence.*;
import java.util.List;

@Entity
@Access(AccessType.FIELD)
@Table(name = "purchase_order")
public class Order {

    @EmbeddedId
    private OrderNo number;

    @Embedded
    private Orderer orderer;

    @Embedded
    private ShippingInfo shippingInfo;

    @Enumerated(EnumType.STRING)
    private OrderState state;

    @Convert(converter = MoneyConverter.class) //autoApply이가 false면 지정해줘야 함.
    private Money totalAmount;

   @ElementCollection(fetch = FetchType.EAGER)
   @CollectionTable(name = "order_line", joinColumns = @JoinColumn(name = "order_number"))
   @OrderColumn(name = "line_idx")
   private List<OrderLine> orderLines;


//    public void setState(OrderState state) {
//        this.state = state;
//    }
//  이런 세터가 아니라 의미 있는 메서드를 사용해야 한다.
    void cancel(){
        // 주문 취소 로직
    }
}
