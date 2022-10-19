package start.ddd.chapter_1.section_4;



public class OrderV2 {
    private OrderStateV2 state;
    private ShippingInfo shippingInfo;


    public void changeShippingInfo(ShippingInfo shippingInfo){
        if(!isShippingChangeable()){
            throw new IllegalStateException("cant change shipping in " + state);
        }
        this.shippingInfo = shippingInfo;
    }

    private boolean isShippingChangeable() {
        return state == OrderStateV2.PAYMENT_WAITING || state == OrderStateV2.PREPARING;
    }
}


enum OrderStateV2 {
    PAYMENT_WAITING, PREPARING, SHIPPED, DELIVERING, DELIVERY_COMPLETED;
}

/**
 * OrderState만 이용해서 판단하는 것이 아니라 다른 정보를 함께 사용한다면 Order에서 로직을 구현하는 것이 좋다.
 * 중요한 점은 주문과 관련된 중요 업무 규칙을 주문 도메인 모델인 Order나 OOrderState에서 구현한다는 점이다.
 *
 * 여담이지만 무조건 객체지향적인 것이 좋은 건 아니넴.. 아니면 객체가 판단하고 여러 정보를 합산해서 판단하는 그런 클래스는 어떨까?
 * 또 생각해보면 정보가 너무 많아지면 관리하기 귀찮을 듯. 그냥 Order에서 관리하는게 제일 좋은 것 같다.
 * 역시 Validation은 절차지향적인 방향이 더 좋은 듯.
 *
 */