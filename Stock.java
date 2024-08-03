package StockTradingPlatform;

import java.util.Random;

public class Stock {
    private String symbol;
    private double price;

    public Stock(String symbol, double initialPrice) {
        this.symbol = symbol;
        this.price = initialPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public void updatePrice() {
        Random random = new Random();
        double changePercent = (random.nextDouble() * 10) - 5; // Simulate price change: ±5%
        price += price * changePercent / 100;
    }
}
