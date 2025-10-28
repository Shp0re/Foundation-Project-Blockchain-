package main.java.taxreturns.frontEnd;

import javax.swing.*;
import java.awt.*;

public class QueryWindow extends JFrame {
    public QueryWindow(String transactionId, String accountNumber, String date, String amount, String type) {
        setTitle("Query");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 1));
        panel.add(new JLabel("Transaction ID: " + transactionId));
        panel.add(new JLabel("Account Number: " + accountNumber));
        panel.add(new JLabel("Date: " + date));
        panel.add(new JLabel("Amount: " + amount));
        panel.add(new JLabel("Type: " + type));
        panel.add(new JButton("Query This Transaction"));

        add(panel);
    }
}
