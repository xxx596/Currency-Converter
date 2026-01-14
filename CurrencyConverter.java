package app;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class CurrencyConverter extends JFrame {

    private final JComboBox<String> fromBox;
    private final JComboBox<String> toBox;
    private final JTextField amountField;
    private final JLabel resultLabel;

    
    private final Map<String, Double> toTND = new HashMap<>();

    public CurrencyConverter() {
        super("Currency Converter (Java Swing)");

        // TAUX (exemples)
        toTND.put("TND", 1.0);
        toTND.put("EUR", 3.35);
        toTND.put("USD", 3.10);

        String[] currencies = {"TND", "EUR", "USD"};

        fromBox = new JComboBox<>(currencies);
        toBox = new JComboBox<>(currencies);
        amountField = new JTextField();
        resultLabel = new JLabel("Result: —");

        JButton convertBtn = new JButton("Convert");
        JButton swapBtn = new JButton("Swap");

        convertBtn.addActionListener(e -> convert());
        swapBtn.addActionListener(e -> swapCurrencies());

        setLayout(new BorderLayout(12, 12));

        JPanel main = new JPanel(new GridLayout(4, 2, 10, 10));
        main.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        main.add(new JLabel("From:"));
        main.add(fromBox);

        main.add(new JLabel("To:"));
        main.add(toBox);

        main.add(new JLabel("Amount:"));
        main.add(amountField);

        main.add(swapBtn);
        main.add(convertBtn);

        add(main, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        resultLabel.setFont(new Font("Arial", Font.BOLD, 14));
        bottom.add(resultLabel);
        add(bottom, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 230);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void swapCurrencies() {
        int fromIndex = fromBox.getSelectedIndex();
        int toIndex = toBox.getSelectedIndex();
        fromBox.setSelectedIndex(toIndex);
        toBox.setSelectedIndex(fromIndex);
        convert();
    }

    private void convert() {
        String from = (String) fromBox.getSelectedItem();
        String to = (String) toBox.getSelectedItem();

        String input = amountField.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter an amount.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        double amount;
        try {
            input = input.replace(",", ".");
            amount = Double.parseDouble(input);
            if (amount < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Invalid amount.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        double inTND = amount * toTND.get(from);
        double result = inTND / toTND.get(to);

        DecimalFormat df = new DecimalFormat("#,##0.00");
        resultLabel.setText("Result: " + df.format(result) + " " + to);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CurrencyConverter::new);
    }
}
