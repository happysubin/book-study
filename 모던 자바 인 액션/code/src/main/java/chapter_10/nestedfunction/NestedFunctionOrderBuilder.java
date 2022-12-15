package chapter_10.nestedfunction;

import chapter_10.Order;
import chapter_10.Stock;
import chapter_10.Trade;

import java.util.stream.Stream;

public class NestedFunctionOrderBuilder {

    public static Order order(String customer, Trade... trades){
        Order order = new Order();
        order.setCustomer(customer);
        Stream.of(trades).forEach(order::addTrade);
        return order;
    }

    public static Trade buy(int quantity, Stock stock, double price){
        return buildTrade(quantity, stock, price, Trade.Type.BUY);
    }

    public static Trade sell(int quantity, Stock stock, double price){
        return buildTrade(quantity, stock, price, Trade.Type.SELL);
    }

    private static Trade buildTrade(int quantity, Stock stock, double price, Trade.Type type) {
        Trade trade = new Trade();
        trade.setQuantity(quantity);
        trade.setStock(stock);
        trade.setPrice(price);
        return trade;
    }

    public static double at(double price){
        return price;
    }

    public static Stock stock(String symbol, String market){
        Stock stock = new Stock();
        stock.setMarket(market);
        stock.setSymbol(symbol);;
        return stock;
    }

    public static String on(String market){
        return market;
    }

    public static void main(String[] args) {
        Order order = order("BigBank",
                buy(80, stock("IBM", on("NYSE")), at(125.00)),
                sell(80, stock("IBM", on("NYSE")), at(375.00))
        );
    }

}
