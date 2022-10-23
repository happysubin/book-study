package start.ddd.chapter_2;

public class Order {

    public void changeShippingInfo(ShippingInfo shippingInfo){
        checkShippingInfoChangeable(); //배송지 변경 가능 여부 확인
    }

    private void checkShippingInfoChangeable() {
        // 무조건 애그리거트 루트인 Order를 거쳐야 도메인 로직을 수행할 수 있다.
        //배송지 정보를 변경할 수 있는지 여부를 확인하는 도메인 규칙 구현
        //주문 애그리거트는 애그리거트 루트인 Order를 거치지 않고는 ShippingInfo를 변경할 수 있는 방법을 제공하지 않는다.
        //항상 Order가 제공하는 도메인 규칙을 따라야 한다.
    }
}


class ShippingInfo{

}
