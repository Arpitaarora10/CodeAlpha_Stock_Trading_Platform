package StockTradingPlatform;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class StockTradingPlatformGUI extends JFrame {
    private Market market;
    private Portfolio portfolio;
    private JTextArea marketDisplay;
    private JTextArea portfolioDisplay;

    public StockTradingPlatformGUI() {
        market = new Market();
        portfolio = new Portfolio(10000.0);

        setTitle("Stock Trading Platform");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        // Create and style market display area
        marketDisplay = new JTextArea();
        marketDisplay.setEditable(false);
        marketDisplay.setBackground(new Color(40, 40, 40));
        marketDisplay.setForeground(Color.LIGHT_GRAY);
        marketDisplay.setFont(new Font("Monospaced", Font.PLAIN, 14));

        // Create and style portfolio display area
        portfolioDisplay = new JTextArea();
        portfolioDisplay.setEditable(false);
        portfolioDisplay.setBackground(new Color(40, 40, 40));
        portfolioDisplay.setForeground(Color.LIGHT_GRAY);
        portfolioDisplay.setFont(new Font("Monospaced", Font.PLAIN, 14));

        // Create and style display panel
        JPanel displayPanel = new JPanel(new GridLayout(2, 1));
        displayPanel.add(new JScrollPane(marketDisplay));
        displayPanel.add(new JScrollPane(portfolioDisplay));
        displayPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        displayPanel.setBackground(new Color(60, 60, 60));

        // Create and style control panel
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.setBackground(new Color(60, 60, 60));

        // Create and style input fields and buttons
        JTextField symbolField = new JTextField(6);
        JTextField quantityField = new JTextField(6);
        JButton buyButton = new JButton("Buy");
        JButton sellButton = new JButton("Sell");

        styleButton(buyButton, new Color(76, 175, 80)); // Green
        styleButton(sellButton, new Color(244, 67, 54)); // Red

        // Add components to control panel
        controlPanel.add(createLabeledComponent("Symbol:", symbolField));
        controlPanel.add(createLabeledComponent("Quantity:", quantityField));
        controlPanel.add(buyButton);
        controlPanel.add(sellButton);

        // Add panels to main frame
        getContentPane().add(displayPanel, BorderLayout.CENTER);
        getContentPane().add(controlPanel, BorderLayout.SOUTH);

        // Define button actions
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleTransaction(symbolField, quantityField, true);
            }
        });

        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleTransaction(symbolField, quantityField, false);
            }
        });

        // Update displays initially and periodically
        updateDisplays();
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                market.updateMarket();
                updateDisplays();
            }
        });
        timer.start();
    }

    // Helper method to style buttons
    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(100, 30)); // Custom size for buttons
        button.setBorder(BorderFactory.createLineBorder(color.darker(), 2)); // Rounded border
    }

    // Helper method to create labeled components
    private JPanel createLabeledComponent(String label, JComponent component) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label_1 = new JLabel(label);
        label_1.setForeground(new Color(200, 200, 200));
        panel.add(label_1);
        panel.add(component);
        panel.setBackground(new Color(60, 60, 60));
        component.setFont(new Font("Arial", Font.PLAIN, 14));
        return panel;
    }

    // Helper method to handle buy/sell transactions
    private void handleTransaction(JTextField symbolField, JTextField quantityField, boolean isBuy) {
        try {
            String symbol = symbolField.getText().toUpperCase();
            int quantity = Integer.parseInt(quantityField.getText());
            Stock stock = market.getStock(symbol);
            if (stock != null) {
                double price = stock.getPrice();
                if (isBuy) {
                    portfolio.buyStock(symbol, quantity, price);
                } else {
                    portfolio.sellStock(symbol, quantity, price);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Stock symbol not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            updateDisplays();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter a valid quantity!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to update the display areas
    private void updateDisplays() {
        StringBuilder marketText = new StringBuilder("Market:\n");
        for (Stock stock : market.getStocks().values()) {
            marketText.append(stock.getSymbol()).append(": $").append(String.format("%.2f", stock.getPrice())).append("\n");
        }
        marketDisplay.setText(marketText.toString());

        StringBuilder portfolioText = new StringBuilder("Portfolio:\n");
        portfolioText.append("Cash: $").append(String.format("%.2f", portfolio.getCash())).append("\n");
        for (Map.Entry<String, Integer> entry : portfolio.getHoldings().entrySet()) {
            String symbol = entry.getKey();
            int quantity = entry.getValue();
            double price = market.getStock(symbol).getPrice();
            portfolioText.append(symbol).append(": ").append(quantity).append(" shares, Current Value: $").append(String.format("%.2f", quantity * price)).append("\n");
        }
        portfolioDisplay.setText(portfolioText.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StockTradingPlatformGUI().setVisible(true);
            }
        });
    }
}
