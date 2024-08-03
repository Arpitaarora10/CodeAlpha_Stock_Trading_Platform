package StockTradingPlatform;

import java.util.HashMap;
import java.util.Map;

public class Market {
    private Map<String, Stock> stocks;

    public Market() {
        stocks = new HashMap<>();
        stocks.put("APPLE", new Stock("APPLE", 150.0));
        stocks.put("GOOGLE", new Stock("GOOGLE", 2800.0));
        stocks.put("AMAZON", new Stock("AMAZON", 3400.0));
    }

    public Stock getStock(String symbol) {
        return stocks.get(symbol);
    }

    public void updateMarket() {
        for (Stock stock : stocks.values()) {
            stock.updatePrice();
        }
    }

    public Map<String, Stock> getStocks() {
        return stocks;
    }
}
