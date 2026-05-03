package com.prog2.labs;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class LabNineteen extends JFrame {

    public LabNineteen() {
        setTitle("Programming 2 - Lab 19 Solution");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 320);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Retail Price", createRetailPanel());
        tabs.addTab("Sales Tax", createSalesTaxPanel());
        tabs.addTab("Tip Calculator", createTipPanel());

        add(tabs, BorderLayout.CENTER);
    }

    private JPanel createRetailPanel() {
        JTextField wholesaleField = new JTextField();
        JTextField markupField = new JTextField();
        JLabel resultLabel = new JLabel("Retail price: ");

        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(e -> {
            try {
                double wholesale = Double.parseDouble(wholesaleField.getText().trim());
                double markupPercent = Double.parseDouble(markupField.getText().trim());
                double retailPrice = wholesale + wholesale * markupPercent / 100.0;
                resultLabel.setText(String.format(Locale.US, "Retail price: $%.2f", retailPrice));
            } catch (NumberFormatException ex) {
                showInputError();
            }
        });

        JPanel panel = createFormPanel(
                new String[]{"Wholesale cost:", "Markup percentage:"},
                new JTextField[]{wholesaleField, markupField},
                calculateButton,
                resultLabel
        );
        return panel;
    }

    private JPanel createSalesTaxPanel() {
        JTextField salesField = new JTextField();
        JLabel federalLabel = new JLabel("Federal tax: ");
        JLabel provincialLabel = new JLabel("Provincial tax: ");
        JLabel totalLabel = new JLabel("Total sales tax: ");

        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(e -> {
            try {
                double sales = Double.parseDouble(salesField.getText().trim());
                double federalTax = sales * 0.05;
                double provincialTax = sales * 0.10;
                double totalTax = federalTax + provincialTax;

                federalLabel.setText(String.format(Locale.US, "Federal tax: $%.2f", federalTax));
                provincialLabel.setText(String.format(Locale.US, "Provincial tax: $%.2f", provincialTax));
                totalLabel.setText(String.format(Locale.US, "Total sales tax: $%.2f", totalTax));
            } catch (NumberFormatException ex) {
                showInputError();
            }
        });

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.add(new JLabel("Monthly total sales:"));
        form.add(salesField);
        form.add(new JLabel(""));
        form.add(calculateButton);

        JPanel results = new JPanel(new GridLayout(3, 1, 8, 8));
        results.add(federalLabel);
        results.add(provincialLabel);
        results.add(totalLabel);

        panel.add(form, BorderLayout.NORTH);
        panel.add(results, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createTipPanel() {
        JTextField chargeField = new JTextField();
        JLabel resultLabel = new JLabel("20% tip: ");

        JButton calculateButton = new JButton("Calculate Tip");
        calculateButton.addActionListener(e -> {
            try {
                double charge = Double.parseDouble(chargeField.getText().trim());
                double tip = charge * 0.20;
                resultLabel.setText(String.format(Locale.US, "20% tip: $%.2f", tip));
            } catch (NumberFormatException ex) {
                showInputError();
            }
        });

        return createFormPanel(
                new String[]{"Restaurant charge:"},
                new JTextField[]{chargeField},
                calculateButton,
                resultLabel
        );
    }

    private JPanel createFormPanel(String[] labels, JTextField[] fields, JButton button, JLabel resultLabel) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel form = new JPanel(new GridLayout(labels.length + 1, 2, 10, 10));
        for (int i = 0; i < labels.length; i++) {
            form.add(new JLabel(labels[i]));
            form.add(fields[i]);
        }
        form.add(new JLabel(""));
        form.add(button);

        panel.add(form, BorderLayout.NORTH);
        panel.add(resultLabel, BorderLayout.CENTER);
        return panel;
    }

    private void showInputError() {
        JOptionPane.showMessageDialog(this,
                "Please enter valid numeric values.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LabNineteen frame = new LabNineteen();
            frame.setVisible(true);
        });
    }
}
