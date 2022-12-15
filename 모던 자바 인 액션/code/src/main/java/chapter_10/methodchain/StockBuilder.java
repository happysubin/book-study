package chapter_10.methodchain;

import chapter_10.Stock;
import chapter_10.Trade;

public class StockBuilder {

    private final MethodChainingOrderBuilder builder;
    private final Trade trade;
    private final Stock stock = new Stock();

    public StockBuilder(MethodChainingOrderBuilder builder, Trade trade, String symbol) {
        this.builder = builder;
        this.trade = trade;
        this.stock.setSymbol(symbol);
    }

    public TradeBuilderWithStock on (String market){
        stock.setMarket(market);
        trade.setStock(stock);
        return new TradeBuilderWithStock(builder, trade);
    }
}
