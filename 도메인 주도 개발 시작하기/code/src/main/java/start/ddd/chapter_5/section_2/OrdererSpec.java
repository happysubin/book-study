package start.ddd.chapter_5.section_2;


import start.ddd.chapter_4.section_3.Order;

public class OrdererSpec implements Specification<Order>{

    private String ordererId;

    public OrdererSpec(String ordererId) {
        this.ordererId = ordererId;
    }

    @Override
    public boolean isSatisfiedBy(Order agg) {
        return agg.getOrderer().getMemberId().getId().equals(ordererId);
    }
}
