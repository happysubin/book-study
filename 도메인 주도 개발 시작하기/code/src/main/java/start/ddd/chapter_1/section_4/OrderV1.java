package start.ddd.chapter_1.section_4;

public class OrderV1 {
    private OrderStateV1 state;
    private ShippingInfo shippingInfo;


    public void changeShippingInfo(ShippingInfo shippingInfo){
        if(!state.isShippingChangeable()){
            throw new IllegalStateException("cant change shipping in " + state);
        }
        this.shippingInfo = shippingInfo;
    }
}


enum OrderStateV1 {
    PAYMENT_WAITING{
        @Override
        public boolean isShippingChangeable() {
            return true;
        }
    },
    PREPARING{
        @Override
        public boolean isShippingChangeable() {
            return true;
        }

    },
    SHIPPED, DELIVERING, DELIVERY_COMPLETED;

    public boolean isShippingChangeable(){
        return false;
    }
}

/**
 * OrderState만 이용해서 판단한다면 해당 코드가 좋음.
 *
 */