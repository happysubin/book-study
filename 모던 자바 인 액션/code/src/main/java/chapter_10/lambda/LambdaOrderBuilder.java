package chapter_10.lambda;

import chapter_10.Order;
import chapter_10.Trade;

import java.util.function.Consumer;

public class LambdaOrderBuilder {

    private Order order = new Order();

    public static Order order(Consumer<LambdaOrderBuilder> consumer){
        LambdaOrderBuilder builder = new LambdaOrderBuilder();
        consumer.accept(builder);
        return builder.order;
    }

    public void forCustomer(String customer){
        order.setCustomer(customer);
    }

    public void buy(Consumer<TradeBuilder> consumer){
        trade(consumer, Trade.Type.BUY);
    }


    public void sell(Consumer<TradeBuilder> consumer){
        trade(consumer, Trade.Type.SELL);
    }

    private void trade(Consumer<TradeBuilder> consumer, Trade.Type type) {
        TradeBuilder builder = new TradeBuilder();
        builder.getTrade().setType(type);
        consumer.accept(builder);
        order.addTrade(builder.getTrade());
    }

    public static void main(String[] args) {
        order(o -> {
            o.forCustomer("BigBank");
            o.buy(t -> {
                t.quantity(80);
                t.price(125.00);
                t.stock( s -> {
                    s.symbol("IBM");
                    s.market("NYSE");
                });
            });
            o.sell( t -> {
                t.quantity(50);
                t.price(375.00);
                t.stock( s -> {
                    s.symbol("GOOGLE");
                    s.market("NASDAQ");
                });
            });
        });
    }
}
