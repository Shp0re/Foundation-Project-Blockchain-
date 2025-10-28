package main.java.taxreturns.frontEnd;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShowTransactionsPage extends JPanel {
    private JPanel Header;
    private JPanel MainContent;
    private JScrollPane scrollPane1;
    private final Object[][] data;
    private JTable transactionTable;
    private JPanel mainPage;
    private JButton acceptTransactionsButton;
    private final Object[] columnNames = {"Transaction ID", "Account Number", "Date", "Amount", "Income or Expense"};
    private transactionAcceptanceListener listener;

    public ShowTransactionsPage(Object[][] data) {
        this.data = data;
        this.createUIComponents();
        this.acceptTransactionsButton.addActionListener(e -> {
            if (listener != null) {
                listener.onTransactionsAccepted(data);
            }
        });

    }


        public JPanel getMainPage () {
            return mainPage;
        }


        private void createUIComponents () {
            // TODO: place custom component creation code here
            this.transactionTable = new JTable(data, columnNames);
            this.transactionTable.setDefaultEditor(Object.class, null);


            this.transactionTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int row = transactionTable.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        String transactionId = transactionTable.getValueAt(row, 0).toString();
                        String accountNumber = transactionTable.getValueAt(row, 1).toString();
                        String date = transactionTable.getValueAt(row, 2).toString();
                        String amount = transactionTable.getValueAt(row, 3).toString();
                        String type = transactionTable.getValueAt(row, 4).toString();

                        QueryWindow queryWindow = new QueryWindow(transactionId, accountNumber, date, amount, type);
                        queryWindow.setVisible(true);
                    }
                }
            });

        }

        public void setTransactionAcceptanceListener (transactionAcceptanceListener listener){
            this.listener = listener;
        }
}

