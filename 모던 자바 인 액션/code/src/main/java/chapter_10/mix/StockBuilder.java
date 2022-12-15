package chapter_10.mix;

import chapter_10.Stock;
import chapter_10.Trade;

public class StockBuilder {

    private final TradeBuilder builder;
    private final Trade trade;
    private final Stock stock = new Stock();

    public StockBuilder(TradeBuilder builder, Trade trade, String symbol) {
        this.builder = builder;
        this.trade = trade;
        stock.setSymbol(symbol);
    }

    public TradeBuilder on(String market){
        stock.setMarket(market);
        trade.setStock(stock);
        return builder;
    }
}
