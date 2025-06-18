// TASK 4
// Currency Converter 

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.*;

public class CurrencyConverter {
    private static final String API_URL = "https://api.frankfurter.app/latest?from=";
    private static final String[] CURRENCIES = {
        "USD", "EUR", "GBP", "JPY", "CAD", "AUD", "CHF", "INR", 
        "CNY", "BRL", "SGD", "NZD", "MXN", "ZAR", "KRW"
    };
    private static final String[][] COUNTRY_CURRENCY = {
        {"United States", "USD"}, {"Eurozone", "EUR"}, {"United Kingdom", "GBP"},
        {"Japan", "JPY"}, {"Canada", "CAD"}, {"Australia", "AUD"}, 
        {"Switzerland", "CHF"}, {"India", "INR"}, {"China", "CNY"},
        {"Brazil", "BRL"}, {"Singapore", "SGD"}, {"New Zealand", "NZD"},
        {"Mexico", "MXN"}, {"South Africa", "ZAR"}, {"South Korea", "KRW"}
    };

    public static void main(String[] args) {
        // Main frame
        JFrame frame = new JFrame("Currency Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setBackground(Color.decode("#F5F5F5"));
 
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.decode("#F5F5F5"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel baseLabel = new JLabel("Base Currency:");
        baseLabel.setForeground(Color.decode("#01579B")); 
        baseLabel.setFont(new Font("Arial", Font.BOLD, 16)); 
        
        JComboBox<String> baseCurrencyCombo = new JComboBox<>(CURRENCIES);
        baseCurrencyCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JLabel targetLabel = new JLabel("Target Currency:");
        targetLabel.setForeground(Color.decode("#01579B"));
        targetLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        JComboBox<String> targetCurrencyCombo = new JComboBox<>(CURRENCIES);
        targetCurrencyCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setForeground(Color.decode("#01579B"));
        amountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        JTextField amountField = new JTextField(12); 
        amountField.setFont(new Font("Arial", Font.PLAIN, 14));
        amountField.setBackground(Color.WHITE);
        amountField.setBorder(BorderFactory.createLineBorder(Color.decode("#B0BEC5")));
        
        JButton convertButton = new JButton("Convert");
        convertButton.setPreferredSize(new Dimension(150, 40));
        convertButton.setBackground(Color.decode("#26A69A")); 
        convertButton.setForeground(Color.WHITE); 
        convertButton.setFont(new Font("Arial", Font.BOLD, 16));
        convertButton.setFocusPainted(false);
        
        JLabel resultLabel = new JLabel("Converted Amount: ");
        resultLabel.setForeground(Color.decode("#2E7D32")); 
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        JTextArea countryList = new JTextArea(12, 40);
        countryList.setEditable(false);
        countryList.setText(getCountryCurrencyList());
        countryList.setFont(new Font("Arial", Font.PLAIN, 14)); 
        countryList.setBackground(Color.decode("#E8ECEF")); 
        countryList.setBorder(BorderFactory.createLineBorder(Color.decode("#B0BEC5")));
        JScrollPane countryScrollPane = new JScrollPane(countryList);
        countryScrollPane.setPreferredSize(new Dimension(500, 150));

        gbc.gridx = 0; gbc.gridy = 0; panel.add(baseLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; panel.add(baseCurrencyCombo, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(targetLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(targetCurrencyCombo, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(amountLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; panel.add(amountField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; panel.add(convertButton, gbc);
        gbc.gridx = 0; gbc.gridy = 4; panel.add(resultLabel, gbc);
        gbc.gridx = 0; gbc.gridy = 5; panel.add(countryScrollPane, gbc);

        frame.add(panel);

        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String baseCurrency = (String) baseCurrencyCombo.getSelectedItem();
                String targetCurrency = (String) targetCurrencyCombo.getSelectedItem();
                String amountText = amountField.getText().trim();
                double amount;
                
                // Validate amount input
                try {
                    amount = Double.parseDouble(amountText);
                    if (amount < 0) {
                        resultLabel.setText("Error: Amount must be non-negative!");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    resultLabel.setText("Error: Please enter a valid number!");
                    return;
                }
                
                // Validate currency codes
                if (!baseCurrency.matches("[A-Z]{3}") || !targetCurrency.matches("[A-Z]{3}")) {
                    resultLabel.setText("Error: Invalid currency code!");
                    return;
                }
                
                try {
                    // Fetch exchange rate
                    double exchangeRate = fetchExchangeRate(baseCurrency, targetCurrency);
                    
                    // Perform conversion
                    double convertedAmount = amount * exchangeRate;
                    
                    // Currency symbol
                    String targetSymbol = getCurrencySymbol(targetCurrency);
                    
                    // Display result
                    resultLabel.setText(String.format("%.2f %s = %.2f %s (%s%.2f)", 
                        amount, baseCurrency, convertedAmount, targetCurrency, targetSymbol, convertedAmount));
                } catch (Exception ex) {
                    resultLabel.setText("Error: " + ex.getMessage());
                }
            }
        });
        
        // Center the frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private static double fetchExchangeRate(String base, String target) throws Exception {
        String urlString = API_URL + base + "&to=" + target;
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        
        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new Exception("Failed to fetch exchange rates. HTTP code: " + responseCode);
        }
        
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String jsonResponse = response.toString();
        String targetKey = "\"" + target + "\":";
        int index = jsonResponse.indexOf(targetKey);
        if (index == -1) {
            throw new Exception("Target currency " + target + " not supported by API.");
        }
        
        int startIndex = index + targetKey.length();
        int endIndex = jsonResponse.indexOf(",", startIndex);
        if (endIndex == -1) {
            endIndex = jsonResponse.indexOf("}", startIndex);
        }
        if (endIndex == -1) {
            throw new Exception("Failed to parse exchange rate for " + target);
        }
        
        String rateString = jsonResponse.substring(startIndex, endIndex).trim();
        try {
            return Double.parseDouble(rateString);
        } catch (NumberFormatException e) {
            throw new Exception("Failed to parse exchange rate value.");
        }
    }
    
    private static String getCurrencySymbol(String currency) {
        switch (currency) {
            case "USD": return "$";
            case "EUR": return "€";
            case "GBP": return "£";
            case "JPY": return "¥";
            case "CAD": return "C$";
            case "AUD": return "A$";
            case "CHF": return "CHF";
            case "INR": return "₹";
            case "CNY": return "¥";
            case "BRL": return "R$";
            case "SGD": return "S$";
            case "NZD": return "NZ$";
            case "MXN": return "MX$";
            case "ZAR": return "R";
            case "KRW": return "₩";
            default: return currency;
        }
    }
    
    private static String getCountryCurrencyList() {
        StringBuilder list = new StringBuilder("Country - Currency:\n");
        for (String[] pair : COUNTRY_CURRENCY) {
            list.append(pair[0]).append(" - ").append(pair[1]).append("\n");
        }
        return list.toString();
    }
}