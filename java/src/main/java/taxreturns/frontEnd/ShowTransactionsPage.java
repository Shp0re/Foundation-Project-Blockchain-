package main.java.taxreturns.frontEnd;

import javax.swing.*;

public class ShowTransactionsPage extends JPanel {
    private JPanel Header;
    private JPanel MainContent;
    private JScrollPane scrollPane1;
    private final Object[][] data;
    private JTable transactionTable;
    private JPanel mainPage;
    private final Object[] columnNames = {"Transaction ID","Account Number","Date","Amount", "Income or Expense"};

    public ShowTransactionsPage(Object[][] data){
        this.data = data;
        this.createUIComponents();
    }

    public JPanel getMainPage(){
        return mainPage;
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.transactionTable = new JTable(data, columnNames);
        this.transactionTable.setDefaultEditor(Object.class, null);
    }
}
