package StockTradingPlatform;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

public class Portfolio {
    private Map<String, Integer> holdings;
    private double cash;

    public Portfolio(double initialCash) {
        holdings = new HashMap<>();
        cash = initialCash;
    }

    public double getCash() {
        return cash;
    }

    public void buyStock(String symbol, int quantity, double price) {
        double cost = quantity * price;
        if (cost <= cash) {
            cash -= cost;
            holdings.put(symbol, holdings.getOrDefault(symbol, 0) + quantity);
            JOptionPane.showMessageDialog(null, "Bought " + quantity + " shares of " + symbol + " at $" + price + " each. Total cost: $" + String.format("%.2f", cost));
        } else {
            JOptionPane.showMessageDialog(null, "Not enough cash to buy " + quantity + " shares of " + symbol, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void sellStock(String symbol, int quantity, double price) {
        if (holdings.getOrDefault(symbol, 0) >= quantity) {
            double revenue = quantity * price;
            cash += revenue;
            holdings.put(symbol, holdings.get(symbol) - quantity);
            JOptionPane.showMessageDialog(null, "Sold " + quantity + " shares of " + symbol + " at $" + price + " each. Total revenue: $" + String.format("%.2f", revenue));
        } else {
            JOptionPane.showMessageDialog(null, "Not enough shares to sell " + quantity + " of " + symbol, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Map<String, Integer> getHoldings() {
        return holdings;
    }
}
