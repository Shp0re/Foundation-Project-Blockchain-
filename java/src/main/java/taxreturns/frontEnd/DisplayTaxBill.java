package main.java.taxreturns.frontEnd;

import javax.swing.*;

public class DisplayTaxBill {
    private Float taxBill;
    private JTextPane thisTaxBillHasTextPane;
    private JLabel taxBillLabel;
    private JButton continueButton;
    private JButton backButton;
    private JPanel mainPage;

    public DisplayTaxBill(Float taxBill){
        this.taxBill = taxBill;
        this.taxBillLabel.setText("TAX BILL: "+taxBill.toString());
    }

    public void updateBill(Float taxBill){
        this.taxBillLabel.setText("TAX BILL: "+taxBill.toString());
    }

    public JPanel getMainPage(){
        return this.mainPage;
    }
}
