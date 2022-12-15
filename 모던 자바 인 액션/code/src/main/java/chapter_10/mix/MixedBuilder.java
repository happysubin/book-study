package chapter_10.mix;

import chapter_10.Order;
import chapter_10.Trade;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class MixedBuilder {

    public static Order forCustomer(String customer, TradeBuilder... builders){
        Order order = new Order();
        order.setCustomer(customer);
        Stream.of(builders).forEach(b -> order.addTrade(b.getTrade()));
        return order;
    }

    public static TradeBuilder buy(Consumer<TradeBuilder> consumer){
        return buildTrade(consumer, Trade.Type.BUY);
    }

    public static TradeBuilder sell(Consumer<TradeBuilder> consumer){
        return buildTrade(consumer, Trade.Type.SELL);
    }

    private static TradeBuilder buildTrade(Consumer<TradeBuilder> consumer, Trade.Type type) {
        TradeBuilder tradeBuilder = new TradeBuilder();
        tradeBuilder.getTrade().setType(type);
        consumer.accept(tradeBuilder);
        return tradeBuilder;
    }

    public static void main(String[] args) {
        forCustomer("BigBank",
                buy(
                        t -> t
                                .quantity(80)
                                .stock("IBM")
                                .on("NYSE")
                                .at(125.00)
                ),
                sell(
                        t -> t
                                .quantity(50)
                                .stock("GOOGLE")
                                .on("NASDAQ")
                                .at(125.00)
                )
        );

    }
}
