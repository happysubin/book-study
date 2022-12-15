package chapter_10.methodchain;

import chapter_10.Order;
import chapter_10.Trade;

import java.lang.reflect.Method;

public class MethodChainingOrderBuilder {

    public final Order order = new Order(); //빌더로 감싼 주문

    private MethodChainingOrderBuilder(String customer) {
        order.setCustomer(customer);
    }

    public static MethodChainingOrderBuilder forCustomer(String customer){
        return new MethodChainingOrderBuilder(customer); //고객의 주문을 만드는 정적 팩토리 메서드
    }

    public TradeBuilder buy(int quantity){
        return new TradeBuilder(this, Trade.Type.BUY, quantity);
    }

    public TradeBuilder sell(int quantity){
        return new TradeBuilder(this, Trade.Type.SELL, quantity);
    }

    public MethodChainingOrderBuilder addTrade(Trade trade){
        order.addTrade(trade);
        return this;
    }

    public Order end(){
        return order;
    }

    public static void main(String[] args) {
        forCustomer("BigBank")
                .buy(80)
                .stock("IBM")
                .on("NYSE")
                .at(125.00)
                .sell(50)
                .stock("GOOGLE")
                .on("NASDAQ")
                .at(375.00)
                .end();
    }
}
