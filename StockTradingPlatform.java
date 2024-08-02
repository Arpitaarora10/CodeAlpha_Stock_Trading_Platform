import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class StockTradingPlatform extends JFrame {

    private JTextField stockField;
    private JTextField quantityField;
    private JTextArea marketDataArea;
    private JTextArea portfolioArea;

    private Map<String, Integer> portfolio = new HashMap<>();
    private Map<String, Double> marketData = new HashMap<>();

    public StockTradingPlatform() {
        setTitle("Stock Trading Platform");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Initialize mock market data
        marketData.put("AAPL", 150.00);
        marketData.put("GOOGL", 2800.00);
        marketData.put("MSFT", 300.00);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Market Data Panel
        JPanel marketDataPanel = new JPanel(new BorderLayout());
        marketDataArea = new JTextArea();
        marketDataArea.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font size
        marketDataArea.setEditable(false);
        updateMarketData();
        marketDataPanel.add(new JScrollPane(marketDataArea), BorderLayout.CENTER);
        tabbedPane.addTab("Market Data", marketDataPanel);

        // Buy/Sell Panel
        JPanel buySellPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        stockField = new JTextField();
        quantityField = new JTextField();
        JButton buyButton = new JButton("Buy");
        JButton sellButton = new JButton("Sell");

        stockField.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font size
        quantityField.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font size
        buyButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font size
        sellButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font size
        buyButton.setPreferredSize(new Dimension(120, 40)); // Set button size
        sellButton.setPreferredSize(new Dimension(120, 40)); // Set button size

        buySellPanel.add(new JLabel("Stock Symbol:"));
        buySellPanel.add(stockField);
        buySellPanel.add(new JLabel("Quantity:"));
        buySellPanel.add(quantityField);
        buySellPanel.add(buyButton);
        buySellPanel.add(sellButton);

        buyButton.addActionListener(new BuyButtonListener());
        sellButton.addActionListener(new SellButtonListener());

        tabbedPane.addTab("Buy/Sell", buySellPanel);

        // Portfolio Panel
        JPanel portfolioPanel = new JPanel(new BorderLayout());
        portfolioArea = new JTextArea();
        portfolioArea.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font size
        portfolioArea.setEditable(false);
        updatePortfolio();
        portfolioPanel.add(new JScrollPane(portfolioArea), BorderLayout.CENTER);
        tabbedPane.addTab("Portfolio", portfolioPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

    private void updateMarketData() {
        StringBuilder sb = new StringBuilder("Market Data:\n");
        for (Map.Entry<String, Double> entry : marketData.entrySet()) {
            sb.append(entry.getKey()).append(": $").append(entry.getValue()).append("\n");
        }
        marketDataArea.setText(sb.toString());
    }

    private void updatePortfolio() {
        StringBuilder sb = new StringBuilder("Your Portfolio:\n");
        if (portfolio.isEmpty()) {
            sb.append("No stocks owned.");
        } else {
            for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
                sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(" shares\n");
            }
        }
        portfolioArea.setText(sb.toString());
    }

    private class BuyButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String stock = stockField.getText().toUpperCase();
            int quantity = Integer.parseInt(quantityField.getText());

            if (!marketData.containsKey(stock)) {
                JOptionPane.showMessageDialog(StockTradingPlatform.this, "Invalid stock symbol.");
                return;
            }

            portfolio.put(stock, portfolio.getOrDefault(stock, 0) + quantity);
            updatePortfolio();
            JOptionPane.showMessageDialog(StockTradingPlatform.this, "Bought " + quantity + " of " + stock);
        }
    }

    private class SellButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String stock = stockField.getText().toUpperCase();
            int quantity = Integer.parseInt(quantityField.getText());

            if (!portfolio.containsKey(stock)) {
                JOptionPane.showMessageDialog(StockTradingPlatform.this, "You don't own this stock.");
                return;
            }

            int currentQuantity = portfolio.get(stock);
            if (quantity > currentQuantity) {
                JOptionPane.showMessageDialog(StockTradingPlatform.this, "Insufficient shares to sell.");
                return;
            }

            if (quantity == currentQuantity) {
                portfolio.remove(stock);
            } else {
                portfolio.put(stock, currentQuantity - quantity);
            }
            updatePortfolio();
            JOptionPane.showMessageDialog(StockTradingPlatform.this, "Sold " + quantity + " of " + stock);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StockTradingPlatform platform = new StockTradingPlatform();
            platform.setVisible(true);
        });
    }
}
