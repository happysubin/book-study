package start.ddd.chapter_1.section_5;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private List<OrderLine> orderLines = new ArrayList<>();
    private Money totalAmounts;
    private ShippingInfo shippingInfo;
    private OrderState state;
    private String orderNumber;

    public Order(List<OrderLine> orderLines, ShippingInfo shippingInfo) {
        setOrderLines(orderLines);
        setShippingInfo(shippingInfo);
    }

    private void setOrderLines(List<OrderLine> orderLines) {
        verifyAtLeastOneOrMoreOrderLines(orderLines);
        this.orderLines = orderLines;
        calculateTotalAmounts();
    }

    private void verifyAtLeastOneOrMoreOrderLines(List<OrderLine> orderLines) {
        if(orderLines == null || orderLines.isEmpty()){
            throw new IllegalArgumentException("no orderLine");
        }
    }

    private void calculateTotalAmounts() {
        int sum = orderLines.stream()
                .mapToInt(x -> x.getAmounts())
                .sum();
        this.totalAmounts = new Money(sum);
    }

    private void setShippingInfo(ShippingInfo shippingInfo) {
        if(shippingInfo == null){
            throw new IllegalArgumentException("no shippingInfo");
        }
        this.shippingInfo = shippingInfo;
    }

    public void changeShippingInfo(ShippingInfo newShipping){
        verifyNotYetShipped();
    }

    private void verifyNotYetShipped() { //도메인에 대한 이해가 깊어지면서 isShippingChangeable 라는 이름의 메서드가 해당 메서드로 변경됨.
        if(state != OrderState.PAYMENT_WAITING && state != OrderState.PREPARING){
            throw new IllegalStateException("already shipped");
        }
    }

    public void cancel(){
        verifyNotYetShipped();
        this.state = OrderState.CANCELED;
    }


    public void completePayment(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        return orderNumber != null ? orderNumber.equals(order.orderNumber) : order.orderNumber == null;
    }

    @Override
    public int hashCode() {
        return orderNumber != null ? orderNumber.hashCode() : 0;
    }
}
